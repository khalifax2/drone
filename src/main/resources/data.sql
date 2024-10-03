DELETE FROM drone;

INSERT INTO drone
(drone_id, battery_capacity, model, serial_no, state, weight_limit) VALUES
('drone-lightweight-1', 100, 'LIGHTWEIGHT', '001', 'IDLE', 100),
('drone-middletweight-2', 100, 'MIDDLEWEIGHT', '002', 'IDLE', 200),
('drone-cruiserweight-3', 100, 'CRUISERWEIGHT', '003', 'IDLE', 500),
('drone-heavyweight-4', 100, 'HEAVYWEIGHT', '004', 'IDLE', 1000);

DELETE FROM medication;

INSERT INTO medication (medication_id, code, image_path, name, weight, drone_id) VALUES
('id-paracetamol', 'MED_1', '/img1', 'PARACETAMOL', 10, NULL),
('id-ibuprofen', 'MED_2', '/img2', 'IBUPROFEN', 10, NULL),
('id-norgesic', 'MED_3', '/img1', 'NORGESIC', 20, NULL),
('id-twynsta', 'MED_4', '/img2', 'TWYNSTA', 20, NULL),
('id-amoxicillin', 'MED_5', '/img3', 'AMOXICILLIN', 50, NULL),
('id-atorvastatin', 'MED_6', '/img4', 'ATORVASTATIN', 50, NULL),
('id-metmorfin', 'MED_7', '/img5', 'METFORMIN', 100, NULL),
('id-aspirin', 'MED_8', '/img6', 'ASPIRIN', 100, NULL),
('id-omeprazole', 'MED_9', '/img7', 'OMEPRAZOLE', 200, NULL),
('id-lisinopril', 'MED_10', '/img8', 'LISINOPRIL', 200, NULL),
('id-albuterol', 'MED_11', '/img9', 'ALBUTEROL', 500, NULL),
('id-prednisone', 'MED_12', '/img10', 'PREDNISONE', 500, NULL);