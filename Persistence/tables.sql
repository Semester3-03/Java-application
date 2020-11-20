CREATE DATABASE brewmes;


CREATE TABLE Product_type (
    id SERIAL PRIMARY KEY,
    product_type INT
);

CREATE TABLE Machine (
    id UUID PRIMARY KEY,
    ip varchar(100)
);

CREATE TABLE Batch (
    id UUID PRIMARY KEY,
    product_type_id INT REFERENCES Product_type(id),
    machine_id UUID REFERENCES Machine(id),
    acceptable_products INT,
    defect_products INT,
    total_products INT
);

CREATE TABLE Vibration (
    id SERIAL PRIMARY KEY,
    batch_id UUID REFERENCES Batch(id),
    time_stamp TIME,
    vibration FLOAT
);

CREATE TABLE Temperature (
    id SERIAL PRIMARY KEY,
    batch_id UUID REFERENCES Batch(id),
    time_stamp TIME,
    temperature FLOAT
);

CREATE TABLE Humidity (
    id SERIAL PRIMARY KEY,
    batch_id UUID REFERENCES Batch(id),
    time_stamp TIME,
    humidity FLOAT
);

CREATE TABLE Time_in_states (
    id SERIAL PRIMARY KEY,
    machine_state INT,
    time FLOAT,
    batch_id UUID references Batch(id)
);