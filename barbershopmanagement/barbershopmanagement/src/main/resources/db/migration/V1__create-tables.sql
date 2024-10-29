CREATE TABLE IF NOT EXISTS barber_shop (
barber_shop_id SERIAL PRIMARY KEY,
adress VARCHAR(255) DEFAULT NULL,
email VARCHAR(255) DEFAULT NULL,
name VARCHAR(255) DEFAULT NULL,
phone VARCHAR(255) DEFAULT NULL,
zip_code VARCHAR(255) DEFAULT NULL,
status SMALLINT DEFAULT NULL,
closing_time TIME DEFAULT NULL,
opening_time TIME DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS client (
client_id SERIAL PRIMARY KEY,
cpf VARCHAR(255) DEFAULT NULL,
name VARCHAR(255) DEFAULT NULL,
phone VARCHAR(255) DEFAULT NULL,
status SMALLINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS employee (
employee_id SERIAL PRIMARY KEY,
cpf VARCHAR(255) DEFAULT NULL,
email VARCHAR(255) DEFAULT NULL,
name VARCHAR(255) DEFAULT NULL,
phone VARCHAR(255) DEFAULT NULL,
status SMALLINT DEFAULT NULL,
fk_barber_shop INT,
FOREIGN KEY (fk_barber_shop) REFERENCES barber_shop (barber_shop_id)
);

CREATE TABLE IF NOT EXISTS item (
item_id SERIAL PRIMARY KEY,
name VARCHAR(255) DEFAULT NULL,
price DOUBLE PRECISION DEFAULT NULL,
time INT DEFAULT NULL,
fk_barber_shop INT,
status SMALLINT DEFAULT NULL,
FOREIGN KEY (fk_barber_shop) REFERENCES barber_shop (barber_shop_id)
);

CREATE TABLE IF NOT EXISTS scheduling (
scheduling_id SERIAL PRIMARY KEY,
fk_barber_shop INT,
fk_employee INT,
fk_client INT,
scheduling_time TIMESTAMP,
status VARCHAR(5),
FOREIGN KEY (fk_barber_shop) REFERENCES barber_shop (barber_shop_id),
FOREIGN KEY (fk_employee) REFERENCES employee (employee_id),
FOREIGN KEY (fk_client) REFERENCES client (client_id)
);

CREATE TABLE IF NOT EXISTS barber_shops_clients (
fk_barber_shops INT NOT NULL,
fk_clients INT NOT NULL,
FOREIGN KEY (fk_clients) REFERENCES client (client_id),
FOREIGN KEY (fk_barber_shops) REFERENCES barber_shop (barber_shop_id)
);

CREATE TABLE IF NOT EXISTS items_schedulings (
fk_schedulings INT NOT NULL,
fk_items INT NOT NULL,
FOREIGN KEY (fk_items) REFERENCES item (item_id),
FOREIGN KEY (fk_schedulings) REFERENCES scheduling (scheduling_id)
);

INSERT INTO barber_shop (adress, email, name, phone, zip_code, status, closing_time, opening_time) VALUES
('123 Rua Principal', 'info@barbershop1.com', 'Barbearia Estilo', '(11) 1234-5678', '12345-678', 0, '21:00:00', '08:00:00'),
('1428 Elm St', 'info@barbershop2.com', 'Barbearia Elegante', '(11) 8765-4321', '23456-789', 0, '20:00:00', '09:00:00');

INSERT INTO client (cpf, name, phone, status) VALUES
('123.456.789-00', 'João Silva', '(11) 9988-7766', 0),
('987.654.321-00', 'Maria Souza', '(11) 5544-3322', 0);

INSERT INTO employee (cpf, email, name, phone, status, fk_barber_shop) VALUES
('111.222.333-44', 'jose@barbeariaestilo.com', 'José Almeida', '(11) 1122-3344', 0, 1),
('444.555.666-77', 'ana@barbeariaelegante.com', 'Ana Costa', '(11) 2233-4455', 0, 2);

INSERT INTO item (name, price, time, fk_barber_shop, status) VALUES
('Corte de Cabelo', 25.00, 30, 1, 0),
('Barba', 15.00, 15, 1, 0),
('Aparar Barba', 20.00, 20, 2, 0);