-- =========================
-- 1) PASILLOS
-- =========================

INSERT INTO pasillos (numero_pasillo) VALUES (1);
INSERT INTO pasillos (numero_pasillo) VALUES (2);

-- =========================
-- 2) ESTANTERIAS
-- =========================

INSERT INTO estanterias (codigo, niveles_maximos, capacidad_nivel, pasillo_id)
VALUES ('A', 4, 8, 1);

INSERT INTO estanterias (codigo, niveles_maximos, capacidad_nivel, pasillo_id)
VALUES ('B', 4, 8, 1);

INSERT INTO estanterias (codigo, niveles_maximos, capacidad_nivel, pasillo_id)
VALUES ('A', 4, 8, 2);

INSERT INTO estanterias (codigo, niveles_maximos, capacidad_nivel, pasillo_id)
VALUES ('B', 4, 8, 2);

-- =========================
-- 3) UBICACIONES ALMACEN
-- =========================

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('1A1', 1, '1');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('1A2', 1, '2');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('1B1', 2, '1');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('1B2', 2, '2');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('2A1', 3, '1');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('2A2', 3, '2');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('2B1', 4, '1');

INSERT INTO ubicaciones_almacen (referencia, estanteria_id, nivel)
VALUES ('2B2', 4, '2');

-- =========================
-- 4) PALETS
-- =========================

INSERT INTO palets (descripcion, material, tipo, capacidad_max_cajas, ubicacion_almacen_id, codigo_marca)
VALUES ('Palet plástico estándar', 'plastico', 'europeo', 8, 1, 'PAL-PL-001');

INSERT INTO palets (descripcion, material, tipo, capacidad_max_cajas, ubicacion_almacen_id, codigo_marca)
VALUES ('Palet madera reforzado', 'madera', 'americano', 8, 3, 'PAL-MA-002');

INSERT INTO palets (descripcion, material, tipo, capacidad_max_cajas, ubicacion_almacen_id, codigo_marca)
VALUES ('Palet plástico ligero', 'plastico', 'europeo', 8, 5, 'PAL-PL-003');

INSERT INTO palets (descripcion, material, tipo, capacidad_max_cajas, ubicacion_almacen_id, codigo_marca)
VALUES ('Palet madera industrial', 'madera', 'americano', 8, 7, 'PAL-MA-004');

-- =========================
-- 5) CAJAS
-- =========================

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-001-A', 'Ingenico Move5000', 1);

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-002-A', 'Verifone V240m', 1);

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-003-B', 'PAX A920', 2);

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-004-B', 'Ingenico Desk3500', 3);

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-005-C', 'Verifone VX520', 4);

INSERT INTO cajas (etiqueta, modelo_producto, palet_id)
VALUES ('CJ-006-C', 'PAX A80', NULL);

-- =========================
-- 6) TERMINALES DE PAGO
-- =========================

INSERT INTO terminales_pago (numero_serie, modelo, marca, estado, notas, fecha_ingreso, fecha_creacion, caja_id)
VALUES ('SN-10001', 'Move5000', 'Ingenico', 'operativo', 'Listo para envío', '2026-01-10', '2026-01-10', 1);

INSERT INTO terminales_pago (numero_serie, modelo, marca, estado, notas, fecha_ingreso, fecha_creacion, caja_id)
VALUES ('SN-10002', 'V240m', 'Verifone', 'pendiente_revision', 'Revisión batería', '2026-01-12', '2026-01-12', 2);

INSERT INTO terminales_pago (numero_serie, modelo, marca, estado, notas, fecha_ingreso, fecha_creacion, caja_id)
VALUES ('SN-10003', 'A920', 'PAX', 'nivel_1', 'Diagnóstico inicial', '2026-01-15', '2026-01-15', 3);

INSERT INTO terminales_pago (numero_serie, modelo, marca, estado, notas, fecha_ingreso, fecha_creacion, caja_id)
VALUES ('SN-10004', 'Desk3500', 'Ingenico', 'en_transito', 'Salida a cliente', '2026-02-01', '2026-02-01', 4);

INSERT INTO terminales_pago (numero_serie, modelo, marca, estado, notas, fecha_ingreso, fecha_creacion, caja_id)
VALUES ('SN-10005', 'VX520', 'Verifone', 'pendiente_laboratorio', 'Error firmware', '2026-02-05', '2026-02-05', 5);

-- =========================
-- 7) USUARIOS
-- =========================

-- Reiniciar AUTO_INCREMENT
ALTER TABLE usuarios 
ALTER COLUMN id 
RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM usuarios);

-- Datos usuario

INSERT INTO usuarios (id, nombre, apellido, nombre_usuario, hash_password, rol, lugar_trabajo) VALUES
(1, 'Carlos', 'Martínez', 'cmartinez', 'hash123', 'administrador', 'Central'),
(2, 'Lucía', 'Gómez', 'lgomez', 'hash456', 'logistica', 'Almacén A'),
(3, 'David', 'Ruiz', 'druiz', 'hash789', 'trabajador_almacen', 'Almacén B'),
(4, 'Ana', 'López', 'alopez', 'hash101', 'tecnico', 'Soporte Técnico');


-- =========================
-- 8) EXPEDICIONES
-- =========================

INSERT INTO expediciones 
(id, fecha_creacion, fecha_recepcion, fecha_modificacion, direccion_destino, paquetes, peso, notas, estado, usuario_id) 
VALUES
(1, CURRENT_TIMESTAMP, NULL, NULL, 'Calle Mayor 10, Madrid', 5, 120, 'Entrega urgente', 'abierta', 2),
(2, CURRENT_TIMESTAMP, NULL, NULL, 'Av. Andalucía 25, Sevilla', 3, 75, 'Cliente VIP', 'en_transito', 2),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'C/ Valencia 45, Barcelona', 8, 200, 'Recibido correctamente', 'recibida', 3),
(4, CURRENT_TIMESTAMP, NULL, NULL, 'Polígono Industrial Sur, Valencia', 2, 50, 'Pendiente confirmación', 'abierta', 1);

-- Reiniciar AUTO_INCREMENT
ALTER TABLE expediciones 
ALTER COLUMN id 
RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM expediciones);