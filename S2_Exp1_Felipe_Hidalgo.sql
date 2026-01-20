CREATE USER SUMATIVA_2206_P1 IDENTIFIED BY "PRY2206.sumativa_1"
DEFAULT TABLESPACE "DATA"
TEMPORARY TABLESPACE "TEMP";
ALTER USER SUMATIVA_2206_P1  QUOTA UNLIMITED ON DATA;
GRANT CREATE SESSION TO SUMATIVA_2206_P1;
GRANT "RESOURCE" TO SUMATIVA_2206_P1;
ALTER USER SUMATIVA_2206_P1 DEFAULT ROLE "RESOURCE";


/* Mostrar salidas por consola */
SET SERVEROUTPUT ON;

/* (Fuera del bloque) Truncar para re-ejecutar
  
TRUNCATE TABLE USUARIO_CLAVE;

/* Bind: fecha de proceso */
VAR p_fecha_proceso VARCHAR2(10);
EXEC :p_fecha_proceso := TO_CHAR(SYSDATE,'YYYY-MM-DD');

DECLARE
  /* %TYPE (al menos 3) */
  v_nombre_empleado  USUARIO_CLAVE.nombre_empleado%TYPE;
  v_nombre_usuario   USUARIO_CLAVE.nombre_usuario%TYPE;
  v_clave_usuario    USUARIO_CLAVE.clave_usuario%TYPE;

  /* escalares */
  v_total_emp        NUMBER;          -- SQL: total empleados
  v_cont_emp         NUMBER := 0;      -- contador iteraciones
  v_annos_trab       NUMBER;          -- PL/SQL: años trabajados
  v_letras_ap        VARCHAR2(2);      -- PL/SQL: letras apellido
  v_sueldo_menos1    NUMBER;          -- PL/SQL: sueldo - 1
  v_fecha_proceso    DATE;            -- PL/SQL: fecha real (DATE)
BEGIN
  /* Convertimos el bind (texto) a DATE una sola vez */
  v_fecha_proceso := TO_DATE(:p_fecha_proceso, 'YYYY-MM-DD');

  DBMS_OUTPUT.PUT_LINE('=============================================');
  DBMS_OUTPUT.PUT_LINE('INICIO PROCESO USUARIO_CLAVE');
  DBMS_OUTPUT.PUT_LINE('Fecha proceso: ' || TO_CHAR(v_fecha_proceso,'YYYY-MM-DD'));
  DBMS_OUTPUT.PUT_LINE('=============================================');

  /* SQL #1: total empleados */
  SELECT COUNT(*)
    INTO v_total_emp
    FROM EMPLEADO;

  DBMS_OUTPUT.PUT_LINE('Total empleados a procesar: ' || v_total_emp);

  /* SQL #2: cursor con datos base (sin armar usuario/clave en el SELECT) */
  FOR r IN (
    SELECT e.id_emp, e.numrun_emp, e.dvrun_emp,
           e.pnombre_emp, e.snombre_emp, e.appaterno_emp, e.apmaterno_emp,
           e.fecha_nac, e.fecha_contrato, e.sueldo_base,
           ec.nombre_estado_civil
      FROM EMPLEADO e
      JOIN ESTADO_CIVIL ec ON ec.id_estado_civil = e.id_estado_civil
     ORDER BY e.id_emp
  ) LOOP
    v_cont_emp := v_cont_emp + 1;

    /* PL/SQL: años trabajando (entero) */
    v_annos_trab := TRUNC(MONTHS_BETWEEN(v_fecha_proceso, r.fecha_contrato) / 12);

    /* PL/SQL: nombre empleado */
    v_nombre_empleado :=
      RTRIM(UPPER(r.pnombre_emp || ' ' ||
            NVL(r.snombre_emp || ' ', '') ||
            r.appaterno_emp || ' ' || r.apmaterno_emp));

    /* PL/SQL: nombre usuario según reglas */
    v_nombre_usuario :=
        LOWER(SUBSTR(r.nombre_estado_civil, 1, 1)) ||
        UPPER(SUBSTR(r.pnombre_emp, 1, 3)) ||
        LENGTH(r.pnombre_emp) || '*' ||
        SUBSTR(TO_CHAR(r.sueldo_base), -1, 1) ||
        r.dvrun_emp ||
        v_annos_trab;

    IF v_annos_trab < 10 THEN
      v_nombre_usuario := v_nombre_usuario || 'X';
    END IF;

    /* PL/SQL: 2 letras del apellido según estado civil */
    IF UPPER(r.nombre_estado_civil) IN ('CASADO', 'ACUERDO DE UNION CIVIL') THEN
      v_letras_ap := LOWER(SUBSTR(r.appaterno_emp, 1, 2));
    ELSIF UPPER(r.nombre_estado_civil) IN ('DIVORCIADO', 'SOLTERO') THEN
      v_letras_ap := LOWER(SUBSTR(r.appaterno_emp, 1, 1) || SUBSTR(r.appaterno_emp, -1, 1));
    ELSIF UPPER(r.nombre_estado_civil) = 'VIUDO' THEN
      v_letras_ap := LOWER(SUBSTR(r.appaterno_emp, LENGTH(r.appaterno_emp) - 2, 2));
    ELSIF UPPER(r.nombre_estado_civil) = 'SEPARADO' THEN
      v_letras_ap := LOWER(SUBSTR(r.appaterno_emp, -2, 2));
    ELSE
      v_letras_ap := LOWER(SUBSTR(r.appaterno_emp, 1, 2));
    END IF;

    /* PL/SQL: sueldo base disminuido en 1 */
    v_sueldo_menos1 := r.sueldo_base - 1;

    /* PL/SQL: clave usuario según reglas */
    v_clave_usuario :=
        SUBSTR(TO_CHAR(r.numrun_emp), 3, 1) ||
        (EXTRACT(YEAR FROM r.fecha_nac) + 2) ||
        SUBSTR(TO_CHAR(v_sueldo_menos1), -3) ||
        v_letras_ap ||
        r.id_emp ||
        TO_CHAR(v_fecha_proceso, 'MMYYYY');

    /* SQL #3: INSERT resultado */
    INSERT INTO USUARIO_CLAVE
      (id_emp, numrun_emp, dvrun_emp, nombre_empleado, nombre_usuario, clave_usuario)
    VALUES
      (r.id_emp, r.numrun_emp, r.dvrun_emp, v_nombre_empleado, v_nombre_usuario, v_clave_usuario);

    /* Salida DBMS por empleado */
    DBMS_OUTPUT.PUT_LINE(
      'OK ID=' || r.id_emp ||
      ' USER=' || v_nombre_usuario ||
      ' CLAVE=' || v_clave_usuario
    );
  END LOOP;

  /* Control transaccional */
  IF v_cont_emp = v_total_emp THEN
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('---------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Confirmación terminada. Procesados ' || v_cont_emp || ' de ' || v_total_emp || '.');
  ELSE
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('---------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Rollback ejecutado. Procesados ' || v_cont_emp || ' de ' || v_total_emp || '.');
  END IF;

END;
/

SELECT *
  FROM USUARIO_CLAVE
 ORDER BY id_emp;
