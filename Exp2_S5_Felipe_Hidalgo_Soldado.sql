/* ============================================================
   CASO SBIF: Avances y Súper Avances con aporte al fondo SEF
   BLOQUE PL/SQL ANÓNIMO (sin modificar tablas ni poblamiento)

   Nota importante sobre “orden almacenado”:
   - En bases de datos, el orden físico de una tabla NO está garantizado.
   - Por pauta (e) y (f), el orden se asegura:
       1) recorriendo cursores con ORDER BY
       2) mostrando evidencia con SELECT ... ORDER BY al final del script.

   Nota importante sobre el año SBIF:
   - Según anexo, si se envía en enero del año N, se informan transacciones del año N-1.
   - Por eso, :b_periodo representa el año de envío/ejecución, y se procesa (b_periodo - 1).
   ============================================================ */

-- (n) VARIABLE BIND: año de envío/ejecución (enero del año N)
VARIABLE b_periodo NUMBER;
EXEC :b_periodo := 2026;

SET SERVEROUTPUT ON;

DECLARE
    -------------------------------------------------------------------------
    -- (n) Año de envío/ejecución (bind) y año real de transacciones a procesar
    -------------------------------------------------------------------------
    v_anno_envio    NUMBER := :b_periodo;
    v_periodo       NUMBER := :b_periodo - 1; -- año de transacciones (SBIF: N-1)

    -------------------------------------------------------------------------
    -- (k) VARRAY tipos de transacción a informar
    -------------------------------------------------------------------------
    TYPE t_tipos_trx IS VARRAY(2) OF VARCHAR2(50);
    v_tipos t_tipos_trx := t_tipos_trx('Avance en Efectivo', 'Super Avance en Efectivo');

    -------------------------------------------------------------------------
    -- (m) Contadores: COMMIT solo si se procesan todos los registros
    -------------------------------------------------------------------------
    v_total_registros  NUMBER := 0;
    v_iteraciones      NUMBER := 0;

    -------------------------------------------------------------------------
    -- (h) Variables de cálculo de aporte (en PL/SQL)
    -------------------------------------------------------------------------
    v_porc_aporte NUMBER := 0;
    v_aporte      NUMBER := 0;

    -------------------------------------------------------------------------
    -- Variables/acumuladores para resumen por mes y tipo
    -------------------------------------------------------------------------
    v_mes_anno VARCHAR2(6);

    v_tot_monto_av   NUMBER := 0;
    v_tot_aporte_av  NUMBER := 0;
    v_cnt_av         NUMBER := 0;

    v_tot_monto_sav  NUMBER := 0;
    v_tot_aporte_sav NUMBER := 0;
    v_cnt_sav        NUMBER := 0;

    -------------------------------------------------------------------------
    -- (j) EXCEPCIONES (3)
    -------------------------------------------------------------------------
    e_sin_registros EXCEPTION; -- usuario
    e_tabla_no_existe EXCEPTION; -- no predefinida
    PRAGMA EXCEPTION_INIT(e_tabla_no_existe, -942);

    e_tramo_no_encontrado EXCEPTION; -- usuario (derivada de NO_DATA_FOUND)

    -------------------------------------------------------------------------
    -- (b)(c) CURSORES EXPLÍCITOS (uno con parámetro)
    -------------------------------------------------------------------------

    -- Cursor 1: meses del año a procesar (orden ascendente por mes)
    CURSOR c_meses IS
        SELECT DISTINCT TO_NUMBER(TO_CHAR(t.fecha_transaccion, 'MM')) AS mes
        FROM transaccion_tarjeta_cliente t
        JOIN tipo_transaccion_tarjeta tt
          ON tt.cod_tptran_tarjeta = t.cod_tptran_tarjeta
        WHERE TO_CHAR(t.fecha_transaccion, 'YYYY') = TO_CHAR(v_periodo)
          AND (tt.nombre_tptran_tarjeta = v_tipos(1)
               OR tt.nombre_tptran_tarjeta = v_tipos(2))
        ORDER BY mes;

    -- Cursor 2: detalle del mes (orden ascendente por fecha y RUN)
    CURSOR c_detalle(p_mes NUMBER) IS
        SELECT
            c.numrun,
            c.dvrun,
            t.nro_tarjeta,
            t.nro_transaccion,
            t.fecha_transaccion,
            tt.nombre_tptran_tarjeta AS tipo_transaccion,
            t.monto_total_transaccion
        FROM transaccion_tarjeta_cliente t
        JOIN tarjeta_cliente tc
          ON tc.nro_tarjeta = t.nro_tarjeta
        JOIN cliente c
          ON c.numrun = tc.numrun
        JOIN tipo_transaccion_tarjeta tt
          ON tt.cod_tptran_tarjeta = t.cod_tptran_tarjeta
        WHERE TO_CHAR(t.fecha_transaccion, 'YYYY') = TO_CHAR(v_periodo)
          AND TO_NUMBER(TO_CHAR(t.fecha_transaccion, 'MM')) = p_mes
          AND (tt.nombre_tptran_tarjeta = v_tipos(1)
               OR tt.nombre_tptran_tarjeta = v_tipos(2))
        ORDER BY t.fecha_transaccion, c.numrun;

    -------------------------------------------------------------------------
    -- (l) REGISTRO PL/SQL para fetch del cursor
    -------------------------------------------------------------------------
    v_det c_detalle%ROWTYPE;

BEGIN
    -------------------------------------------------------------------------
    -- Evidencia de año usado (evita confusión de pauta SBIF)
    -------------------------------------------------------------------------
    DBMS_OUTPUT.PUT_LINE('Año de envío/ejecución (bind): ' || v_anno_envio);
    DBMS_OUTPUT.PUT_LINE('Año de transacciones procesadas (SBIF N-1): ' || v_periodo);

    -------------------------------------------------------------------------
    -- (g) TRUNCAR TABLAS DE SALIDA EN TIEMPO DE EJECUCIÓN
    -------------------------------------------------------------------------
    EXECUTE IMMEDIATE 'TRUNCATE TABLE DETALLE_APORTE_SBIF';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE RESUMEN_APORTE_SBIF';

    -------------------------------------------------------------------------
    -- Contar total de transacciones del año a procesar (av/sav)
    -------------------------------------------------------------------------
    SELECT COUNT(*)
      INTO v_total_registros
    FROM transaccion_tarjeta_cliente t
    JOIN tipo_transaccion_tarjeta tt
      ON tt.cod_tptran_tarjeta = t.cod_tptran_tarjeta
    WHERE TO_CHAR(t.fecha_transaccion, 'YYYY') = TO_CHAR(v_periodo)
      AND (tt.nombre_tptran_tarjeta = v_tipos(1)
           OR tt.nombre_tptran_tarjeta = v_tipos(2));

    IF v_total_registros = 0 THEN
        RAISE e_sin_registros;
    END IF;

    -------------------------------------------------------------------------
    -- Procesar por mes (cursor 1) y detalle (cursor 2) generando DETALLE + RESUMEN
    -------------------------------------------------------------------------
    FOR r_mes IN c_meses LOOP

        v_tot_monto_av   := 0;  v_tot_aporte_av  := 0;  v_cnt_av  := 0;
        v_tot_monto_sav  := 0;  v_tot_aporte_sav := 0;  v_cnt_sav := 0;

        v_mes_anno := LPAD(r_mes.mes, 2, '0') || TO_CHAR(v_periodo);

        OPEN c_detalle(r_mes.mes);
        LOOP
            FETCH c_detalle INTO v_det;
            EXIT WHEN c_detalle%NOTFOUND;

            -----------------------------------------------------------------
            -- (h) cálculo aporte en PL/SQL
            -- (j) predefinida: NO_DATA_FOUND (capturada y transformada)
            -----------------------------------------------------------------
            BEGIN
                SELECT porc_aporte_sbif
                  INTO v_porc_aporte
                FROM tramo_aporte_sbif
                WHERE v_det.monto_total_transaccion
                      BETWEEN tramo_inf_av_sav AND tramo_sup_av_sav;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    RAISE e_tramo_no_encontrado;
            END;

            v_aporte := ROUND(v_det.monto_total_transaccion * (v_porc_aporte / 100));

            INSERT INTO detalle_aporte_sbif
                (numrun, dvrun, nro_tarjeta, nro_transaccion, fecha_transaccion,
                 tipo_transaccion, monto_transaccion, aporte_sbif)
            VALUES
                (v_det.numrun, v_det.dvrun, v_det.nro_tarjeta, v_det.nro_transaccion,
                 v_det.fecha_transaccion, v_det.tipo_transaccion,
                 v_det.monto_total_transaccion, v_aporte);

            v_iteraciones := v_iteraciones + 1;

            IF v_det.tipo_transaccion = v_tipos(1) THEN
                v_tot_monto_av  := v_tot_monto_av  + v_det.monto_total_transaccion;
                v_tot_aporte_av := v_tot_aporte_av + v_aporte;
                v_cnt_av        := v_cnt_av + 1;
            ELSE
                v_tot_monto_sav  := v_tot_monto_sav  + v_det.monto_total_transaccion;
                v_tot_aporte_sav := v_tot_aporte_sav + v_aporte;
                v_cnt_sav        := v_cnt_sav + 1;
            END IF;

        END LOOP;
        CLOSE c_detalle;

        IF v_cnt_av > 0 THEN
            INSERT INTO resumen_aporte_sbif
                (mes_anno, tipo_transaccion, monto_total_transacciones, aporte_total_abif)
            VALUES
                (v_mes_anno, v_tipos(1), v_tot_monto_av, v_tot_aporte_av);
        END IF;

        IF v_cnt_sav > 0 THEN
            INSERT INTO resumen_aporte_sbif
                (mes_anno, tipo_transaccion, monto_total_transacciones, aporte_total_abif)
            VALUES
                (v_mes_anno, v_tipos(2), v_tot_monto_sav, v_tot_aporte_sav);
        END IF;

    END LOOP;

    -------------------------------------------------------------------------
    -- (m) COMMIT solo si terminó correctamente y procesó todo
    -------------------------------------------------------------------------
    IF v_iteraciones = v_total_registros THEN
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('OK: Procesados ' || v_iteraciones || ' de ' || v_total_registros || '. COMMIT realizado.');
    ELSE
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: Procesados ' || v_iteraciones || ' de ' || v_total_registros || '. ROLLBACK realizado.');
    END IF;

EXCEPTION
    WHEN e_sin_registros THEN
        DBMS_OUTPUT.PUT_LINE('No existen avances/súper avances para el año de transacciones ' || v_periodo || '.');
        ROLLBACK;

    WHEN e_tramo_no_encontrado THEN
        DBMS_OUTPUT.PUT_LINE('No existe tramo en TRAMO_APORTE_SBIF para monto_total: ' || v_det.monto_total_transaccion);
        ROLLBACK;

    WHEN e_tabla_no_existe THEN
        DBMS_OUTPUT.PUT_LINE('ORA-00942: falta una tabla/vista. Revisa nombres y esquema.');
        ROLLBACK;

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error inesperado: ' || SQLERRM);
        ROLLBACK;
END;
/
--------------------------------------------------------------------------------
-- SELECTS DE VALIDACIÓN / EVIDENCIA (orden solicitado en pauta)
--------------------------------------------------------------------------------
SELECT numrun, dvrun, nro_tarjeta, nro_transaccion, fecha_transaccion, tipo_transaccion,
       monto_transaccion AS monto_total_transaccion, aporte_sbif
FROM detalle_aporte_sbif
ORDER BY fecha_transaccion, numrun;

SELECT mes_anno, tipo_transaccion, monto_total_transacciones, aporte_total_abif
FROM resumen_aporte_sbif
ORDER BY mes_anno, tipo_transaccion;


