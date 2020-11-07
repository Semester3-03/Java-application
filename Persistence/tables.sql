CREATE DATABASE brewmes;

CREATE TABLE Vibration (
    id SERIAL PRIMARY KEY,
    time_stamp TIME,
    vibration FLOAT
);

CREATE TABLE Temperature (
    id SERIAL PRIMARY KEY,
    time_stamp TIME,
    temperature FLOAT
);

CREATE TABLE Humidity (
    id SERIAL PRIMARY KEY,
    time_stamp TIME,
    humidity FLOAT
);

CREATE TABLE Time_in_states (
    id SERIAL PRIMARY KEY,
    time_stamp TIME,
    machine_state INT
);

CREATE TABLE Product_type (
    id SERIAL PRIMARY KEY,
    product_type INT
);

CREATE TABLE Machine (
    id SERIAL PRIMARY KEY,
    ip varchar(100)
);

CREATE TABLE Batch (
    id SERIAL PRIMARY KEY,
    product_type_id INT REFERENCES Product_type(id),
    machine_id INT REFERENCES Machine(id),
    acceptable_products INT,
    defect_products INT,
    total_products INT,
    min_temp FLOAT,
    max_temp FLOAT,
    avg_temp FLOAT
);

CREATE TABLE Batch_vibration (
    batch_id INT REFERENCES Batch(id),
    vibration_id INT REFERENCES Vibration(id)
);

CREATE TABLE Batch_temperature (
    batch_id INT REFERENCES Batch(id),
    temperature_id INT REFERENCES temperature(id)
);

CREATE TABLE Batch_humidity (
    batch_id INT REFERENCES Batch(id),
    humidity_id INT REFERENCES humidity(id)
);

CREATE TABLE Batch_time_in_states (
    batch_id INT REFERENCES Batch(id),
    time_in_states_id INT REFERENCES Time_in_states(id)
);
