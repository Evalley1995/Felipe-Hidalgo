CREATE USER PRY2206_PRUEBA3 IDENTIFIED BY "PRY2206.prueba_3"
DEFAULT TABLESPACE DATA
TEMPORARY TABLESPACE TEMP;
ALTER USER PRY2206_PRUEBA3 QUOTA UNLIMITED ON DATA;
GRANT CREATE SESSION TO PRY2206_PRUEBA3;
GRANT RESOURCE TO PRY2206_PRUEBA3;
ALTER USER PRY2206_PRUEBA3 DEFAULT ROLE RESOURCE;

/* =========================================================
   CASO 1 - TRIGGER TR_ACT_TOTAL_CONSUMOS + BLOQUE TEST
   ========================================================= */

SET SERVEROUTPUT ON;

-- Drop del trigger si existe (evita error si no está creado)
BEGIN
  EXECUTE IMMEDIATE 'DROP TRIGGER tr_act_total_consumos';
EXCEPTION
  WHEN OTHERS THEN
    NULL;
END;
/

CREATE OR REPLACE TRIGGER tr_act_total_consumos
AFTER INSERT OR UPDATE OR DELETE ON consumo
FOR EACH ROW
DECLARE
  v_existe NUMBER;
BEGIN
  -- INSERT
  IF INSERTING THEN
    SELECT COUNT(*)
      INTO v_existe
      FROM total_consumos
     WHERE id_huesped = :NEW.id_huesped;

    IF v_existe = 0 THEN
      INSERT INTO total_consumos (id_huesped, monto_consumos)
      VALUES (:NEW.id_huesped, NVL(:NEW.monto,0));
    ELSE
      UPDATE total_consumos
         SET monto_consumos = NVL(monto_consumos,0) + NVL(:NEW.monto,0)
       WHERE id_huesped = :NEW.id_huesped;
    END IF;

  -- DELETE
  ELSIF DELETING THEN
    UPDATE total_consumos
       SET monto_consumos = NVL(monto_consumos,0) - NVL(:OLD.monto,0)
     WHERE id_huesped = :OLD.id_huesped;

  -- UPDATE
  ELSIF UPDATING THEN
    IF :OLD.id_huesped = :NEW.id_huesped THEN
      UPDATE total_consumos
         SET monto_consumos = NVL(monto_consumos,0) + (NVL(:NEW.monto,0) - NVL(:OLD.monto,0))
       WHERE id_huesped = :NEW.id_huesped;
    ELSE
      -- si cambia huésped, rebaja al anterior y suma al nuevo
      UPDATE total_consumos
         SET monto_consumos = NVL(monto_consumos,0) - NVL(:OLD.monto,0)
       WHERE id_huesped = :OLD.id_huesped;

      SELECT COUNT(*)
        INTO v_existe
        FROM total_consumos
       WHERE id_huesped = :NEW.id_huesped;

      IF v_existe = 0 THEN
        INSERT INTO total_consumos (id_huesped, monto_consumos)
        VALUES (:NEW.id_huesped, NVL(:NEW.monto,0));
      ELSE
        UPDATE total_consumos
           SET monto_consumos = NVL(monto_consumos,0) + NVL(:NEW.monto,0)
         WHERE id_huesped = :NEW.id_huesped;
      END IF;
    END IF;
  END IF;
END;
/
------------------------------------------------------------
-- BLOQUE ANÓNIMO DE PRUEBA 
------------------------------------------------------------
DECLARE
  v_nuevo_id consumo.id_consumo%TYPE;
  v_total_340006 total_consumos.monto_consumos%TYPE;

  v_h_del consumo.id_huesped%TYPE;
  v_h_upd consumo.id_huesped%TYPE;

  v_total_del_before total_consumos.monto_consumos%TYPE;
  v_total_upd_before total_consumos.monto_consumos%TYPE;

  v_total_del_after total_consumos.monto_consumos%TYPE;
  v_total_upd_after total_consumos.monto_consumos%TYPE;
BEGIN
  -- Totales ANTES
  SELECT monto_consumos INTO v_total_340006
    FROM total_consumos
   WHERE id_huesped = 340006;

  SELECT id_huesped INTO v_h_del
    FROM consumo
   WHERE id_consumo = 11473;

  SELECT id_huesped INTO v_h_upd
    FROM consumo
   WHERE id_consumo = 10688;

  SELECT monto_consumos INTO v_total_del_before
    FROM total_consumos
   WHERE id_huesped = v_h_del;

  SELECT monto_consumos INTO v_total_upd_before
    FROM total_consumos
   WHERE id_huesped = v_h_upd;

  DBMS_OUTPUT.PUT_LINE('ANTES total 340006: ' || v_total_340006);
  DBMS_OUTPUT.PUT_LINE('ANTES total huesped(11473)='||v_h_del||': ' || v_total_del_before);
  DBMS_OUTPUT.PUT_LINE('ANTES total huesped(10688)='||v_h_upd||': ' || v_total_upd_before);

  -- 1) Inserta nuevo consumo con id siguiente
  SELECT NVL(MAX(id_consumo),0) + 1
    INTO v_nuevo_id
    FROM consumo;

  INSERT INTO consumo (id_consumo, id_reserva, id_huesped, monto)
  VALUES (v_nuevo_id, 1587, 340006, 150);

  -- 2) Elimina consumo 11473
  DELETE FROM consumo
   WHERE id_consumo = 11473;

  -- 3) Actualiza consumo 10688 a 95
  UPDATE consumo
     SET monto = 95
   WHERE id_consumo = 10688;

  -- Totales DESPUÉS
  SELECT monto_consumos INTO v_total_340006
    FROM total_consumos
   WHERE id_huesped = 340006;

  SELECT monto_consumos INTO v_total_del_after
    FROM total_consumos
   WHERE id_huesped = v_h_del;

  SELECT monto_consumos INTO v_total_upd_after
    FROM total_consumos
   WHERE id_huesped = v_h_upd;

  DBMS_OUTPUT.PUT_LINE('DESPUES total 340006: ' || v_total_340006);
  DBMS_OUTPUT.PUT_LINE('DESPUES total huesped(11473)='||v_h_del||': ' || v_total_del_after);
  DBMS_OUTPUT.PUT_LINE('DESPUES total huesped(10688)='||v_h_upd||': ' || v_total_upd_after);

  COMMIT;
END;
/
------------------------------------------------------------
-- CONSULTAS DE VERIFICACIÓN 
------------------------------------------------------------

-- Ver detalle de los consumos involucrados
SELECT *
  FROM consumo
 WHERE id_consumo IN (10688, 11473)
 ORDER BY id_consumo;

-- Ver el consumo insertado para 340006 / reserva 1587 / monto 150
SELECT *
  FROM consumo
 WHERE id_huesped = 340006
   AND id_reserva = 1587
 ORDER BY id_consumo DESC;

-- Ver totales de los huéspedes involucrados (al menos 340006 y los de 10688/11473)
SELECT *
  FROM total_consumos
 WHERE id_huesped IN (340006,
                      (SELECT id_huesped FROM consumo WHERE id_consumo = 10688)
                     )
 ORDER BY id_huesped;

SELECT *
  FROM total_consumos
 ORDER BY id_huesped;
 
 
/* =========================================================
   - CASO 2 
   COBRANZA DIARIA
   - Package: PKG_TOURS
   - Funciones: FN_AGENCIA (REG_ERRORES), FN_CONSUMOS 
   - Aux: FN_ALOJAMIENTO_USD, FN_PERSONAS_RESERVA, FN_DESC_CONSUMOS_USD
   - Procedimiento: SP_COBRANZA_DIARIA -> llena DETALLE_DIARIO_HUESPEDES
   ========================================================= */

--------------------------------------------------------------------------------
-- 1) PACKAGE: TOURS 
--------------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE pkg_tours AS
  g_monto_tours NUMBER := 0; -- opcional
  FUNCTION fn_monto_tours(p_id_huesped NUMBER) RETURN NUMBER;
END pkg_tours;
/
CREATE OR REPLACE PACKAGE BODY pkg_tours AS
  FUNCTION fn_monto_tours(p_id_huesped NUMBER) RETURN NUMBER IS
    v_monto NUMBER;
  BEGIN
    SELECT NVL(SUM(t.valor_tour * ht.num_personas), 0)
      INTO v_monto
      FROM huesped_tour ht
      JOIN tour t ON t.id_tour = ht.id_tour
     WHERE ht.id_huesped = p_id_huesped;

    g_monto_tours := v_monto;
    RETURN v_monto;

  EXCEPTION
    WHEN OTHERS THEN
      g_monto_tours := 0;

      INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
      VALUES (
        sq_error.NEXTVAL,
        'PKG_TOURS.FN_MONTO_TOURS',
        SUBSTR('Huésped ' || p_id_huesped || ' | ' || SQLERRM, 1, 300)
      );

      RETURN 0;
  END fn_monto_tours;
END pkg_tours;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 2) FUNCIÓN: FN_AGENCIA (con control + registro en REG_ERRORES)
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_agencia(p_id_huesped NUMBER) RETURN VARCHAR2 IS
  v_agencia agencia.nom_agencia%TYPE;
BEGIN
  SELECT a.nom_agencia
    INTO v_agencia
    FROM huesped h
    JOIN agencia a ON a.id_agencia = h.id_agencia
   WHERE h.id_huesped = p_id_huesped;

  RETURN v_agencia;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_AGENCIA',
      SUBSTR('No registra agencia | Huésped ' || p_id_huesped, 1, 300)
    );
    RETURN 'NO REGISTRA AGENCIA';

  WHEN OTHERS THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_AGENCIA',
      SUBSTR('Huésped ' || p_id_huesped || ' | ' || SQLERRM, 1, 300)
    );
    RETURN 'NO REGISTRA AGENCIA';
END fn_agencia;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 3) FUNCIÓN: FN_CONSUMOS desde TOTAL_CONSUMOS
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_consumos(p_id_huesped NUMBER) RETURN NUMBER IS
  v_monto total_consumos.monto_consumos%TYPE;
BEGIN
  SELECT monto_consumos
    INTO v_monto
    FROM total_consumos
   WHERE id_huesped = p_id_huesped;

  RETURN NVL(v_monto, 0);

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_CONSUMOS',
      SUBSTR('No registra consumos (TOTAL_CONSUMOS) | Huésped ' || p_id_huesped, 1, 300)
    );
    RETURN 0;

  WHEN OTHERS THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_CONSUMOS',
      SUBSTR('Huésped ' || p_id_huesped || ' | ' || SQLERRM, 1, 300)
    );
    RETURN 0;
END fn_consumos;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 4) AUX: ALOJAMIENTO 
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_alojamiento_usd(p_id_reserva NUMBER) RETURN NUMBER IS
  v_aloj NUMBER;
BEGIN
  SELECT NVL(SUM((ha.valor_habitacion + ha.valor_minibar) * r.estadia), 0)
    INTO v_aloj
    FROM reserva r
    JOIN detalle_reserva dr ON dr.id_reserva = r.id_reserva
    JOIN habitacion ha      ON ha.id_habitacion = dr.id_habitacion
   WHERE r.id_reserva = p_id_reserva;

  RETURN v_aloj;

EXCEPTION
  WHEN OTHERS THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_ALOJAMIENTO_USD',
      SUBSTR('Reserva ' || p_id_reserva || ' | ' || SQLERRM, 1, 300)
    );
    RETURN 0;
END fn_alojamiento_usd;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 5) AUX: PERSONAS por RESERVA (estimación por tipo habitación)
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_personas_reserva(p_id_reserva NUMBER) RETURN NUMBER IS
  v_personas NUMBER;
BEGIN
  SELECT NVL(SUM(
         CASE ha.tipo_habitacion
           WHEN 'S'  THEN 1
           WHEN 'D'  THEN 2
           WHEN 'T'  THEN 3
           WHEN 'C'  THEN 4
           WHEN 'SE' THEN 2
           WHEN 'SP' THEN 4
           ELSE 1
         END
       ), 0)
    INTO v_personas
    FROM detalle_reserva dr
    JOIN habitacion ha ON ha.id_habitacion = dr.id_habitacion
   WHERE dr.id_reserva = p_id_reserva;

  IF v_personas = 0 THEN
    v_personas := 1;
  END IF;

  RETURN v_personas;

EXCEPTION
  WHEN OTHERS THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_PERSONAS_RESERVA',
      SUBSTR('Reserva ' || p_id_reserva || ' | ' || SQLERRM, 1, 300)
    );
    RETURN 1;
END fn_personas_reserva;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 6) AUX: DESCUENTO por CONSUMOS según TRAMOS_CONSUMOS
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_desc_consumos_usd(p_consumos NUMBER) RETURN NUMBER IS
  v_pct tramos_consumos.pct%TYPE;
BEGIN
  SELECT pct
    INTO v_pct
    FROM tramos_consumos
   WHERE p_consumos BETWEEN vmin_tramo AND vmax_tramo;

  RETURN (p_consumos * NVL(v_pct, 0));

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 0;
  WHEN OTHERS THEN
    INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
    VALUES (
      sq_error.NEXTVAL,
      'FN_DESC_CONSUMOS_USD',
      SUBSTR('Consumos ' || p_consumos || ' | ' || SQLERRM, 1, 300)
    );
    RETURN 0;
END fn_desc_consumos_usd;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 7) PROCEDIMIENTO PRINCIPAL
--------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE sp_cobranza_diaria(
  p_fecha_proceso IN DATE,
  p_tipo_cambio   IN NUMBER
) IS
  CURSOR c_reservas IS
    SELECT r.id_reserva, r.id_huesped, r.estadia
      FROM reserva r
     WHERE TRUNC(r.ingreso + r.estadia) = TRUNC(p_fecha_proceso);

  v_nombre             VARCHAR2(60);
  v_agencia            VARCHAR2(40);

  v_aloj_usd           NUMBER;
  v_consum_usd         NUMBER;
  v_tours_usd          NUMBER;

  v_personas           NUMBER;
  v_valor_personas_usd NUMBER;

  v_subtotal_usd       NUMBER;
  v_desc_consum_usd    NUMBER;
  v_desc_agencia_usd   NUMBER;
  v_total_usd          NUMBER;

BEGIN
  IF p_tipo_cambio IS NULL OR p_tipo_cambio <= 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Tipo de cambio inválido.');
  END IF;

  -- Limpieza 
  DELETE FROM detalle_diario_huespedes;
  DELETE FROM reg_errores;
  COMMIT;

  FOR r IN c_reservas LOOP
    BEGIN
      -- Nombre huésped
      SELECT (h.appat_huesped || ' ' || h.apmat_huesped || ', ' || h.nom_huesped)
        INTO v_nombre
        FROM huesped h
       WHERE h.id_huesped = r.id_huesped;

      -- Agencia / consumos / tours
      v_agencia    := fn_agencia(r.id_huesped);
      v_consum_usd := fn_consumos(r.id_huesped);
      v_tours_usd  := pkg_tours.fn_monto_tours(r.id_huesped);

      -- Alojamiento 
      v_aloj_usd := fn_alojamiento_usd(r.id_reserva);

      -- Personas: 35.000 CLP por persona 
      v_personas := fn_personas_reserva(r.id_reserva);
      v_valor_personas_usd := (v_personas * 35000) / p_tipo_cambio;

      -- Subtotal 
      v_subtotal_usd := v_aloj_usd + v_consum_usd + v_valor_personas_usd + v_tours_usd;

      -- Descuentos
      v_desc_consum_usd := fn_desc_consumos_usd(v_consum_usd);

      IF UPPER(v_agencia) = 'VIAJES ALBERTI' THEN
        v_desc_agencia_usd := (v_aloj_usd + v_consum_usd + v_valor_personas_usd) * 0.12;
      ELSE
        v_desc_agencia_usd := 0;
      END IF;

      v_total_usd := v_subtotal_usd - v_desc_consum_usd - v_desc_agencia_usd;

      -- Guarda en CLP
      INSERT INTO detalle_diario_huespedes(
        id_huesped, nombre, agencia,
        alojamiento, consumos, tours,
        subtotal_pago, descuento_consumos, descuentos_agencia, total
      )
      VALUES(
        r.id_huesped, v_nombre, v_agencia,
        ROUND(v_aloj_usd * p_tipo_cambio),
        ROUND(v_consum_usd * p_tipo_cambio),
        ROUND(v_tours_usd * p_tipo_cambio),
        ROUND(v_subtotal_usd * p_tipo_cambio),
        ROUND(v_desc_consum_usd * p_tipo_cambio),
        ROUND(v_desc_agencia_usd * p_tipo_cambio),
        ROUND(v_total_usd * p_tipo_cambio)
      );

    EXCEPTION
      WHEN OTHERS THEN
        INSERT INTO reg_errores (id_error, nomsubprograma, msg_error)
        VALUES (
          sq_error.NEXTVAL,
          'SP_COBRANZA_DIARIA',
          SUBSTR('Reserva ' || r.id_reserva || ' | Huésped ' || r.id_huesped || ' | ' || SQLERRM, 1, 300)
        );
       
    END;
  END LOOP;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('Proceso OK. Fecha: ' || TO_CHAR(p_fecha_proceso,'DD/MM/YYYY') ||
                       ' | TC: ' || p_tipo_cambio);
END sp_cobranza_diaria;
/
SHOW ERRORS;

--------------------------------------------------------------------------------
-- 8) EJECUCIÓN 
--------------------------------------------------------------------------------
BEGIN
  sp_cobranza_diaria(TO_DATE('18/08/2021','DD/MM/YYYY'), 915);
END;
/

-- Resultados (detalle)
SELECT * FROM detalle_diario_huespedes ORDER BY id_huesped;

-- Errores registrados
SELECT * FROM reg_errores ORDER BY id_error;
