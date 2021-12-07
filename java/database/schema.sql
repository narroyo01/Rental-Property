BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, transactions, property, inquiry, maintenance_type, maintenance_request;
DROP SEQUENCE IF EXISTS seq_user_id, seq_transaction_id, seq_property_id, seq_inquiry_id, seq_type_id, seq_request_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_transaction_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1; --?????unsure
  
CREATE SEQUENCE seq_property_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_inquiry_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_type_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_request_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

-- new content

CREATE TABLE property (
        property_id INT DEFAULT nextval('seq_property_id'::regclass),
        address VARCHAR(100),
        rent INT,
        is_available BOOLEAN,
        tenant_id INT,
        
        CONSTRAINT pk_property PRIMARY KEY(property_id),
        CONSTRAINT fk_tenantid FOREIGN KEY(tenant_id) REFERENCES users(user_id)
);

CREATE TABLE inquiry (
        inquiry_id INT DEFAULT nextval('seq_inquiry_id'::regclass),
        email VARCHAR(100),
        phone VARCHAR(20),
        message VARCHAR(300),
        property_id INT,
        
        CONSTRAINT pk_inquiry PRIMARY KEY(inquiry_id),
        CONSTRAINT fk_propertyid FOREIGN KEY(property_id) REFERENCES property(property_id)
);

CREATE TABLE transactions (
        transaction_id INT DEFAULT nextval('seq_transaction_id'::regclass),
        time_initiated timestamp,
        time_paid timestamp,
        amount_due INT,
        amount_paid INT,
        property_id INT,
        tenant_id INT,
        
        CONSTRAINT pk_transaction PRIMARY KEY(transaction_id),
        CONSTRAINT fk_propertyid FOREIGN KEY(property_id) REFERENCES property(property_id),
        CONSTRAINT fk_tenantid FOREIGN KEY(tenant_id) REFERENCES users(user_id)
);

CREATE TABLE maintenance_type (
        maintenance_type_id INT DEFAULT nextval('seq_type_id'::regclass),
        description VARCHAR(300),
        
        CONSTRAINT pk_maintenancetype PRIMARY KEY(maintenance_type_id)
);

CREATE TABLE maintenance_request (
        maintenance_request_id INT DEFAULT nextval('seq_request_id'::regclass),
        property_id INT,
        type_id INT,
        technician_id INT,
        requester_id INT,
        time_stamp TIMESTAMP,
        is_complete BOOLEAN,
        email VARCHAR(100),
        phone VARCHAR(20),
        name VARCHAR(20),
        
        CONSTRAINT pk_maintenancerequest PRIMARY KEY(maintenance_request_id),
        CONSTRAINT fk_propertyid FOREIGN KEY(property_id) REFERENCES property(property_id),
        CONSTRAINT fk_typeid FOREIGN KEY(type_id) REFERENCES maintenance_type(maintenance_type_id),
        CONSTRAINT fk_technicianid FOREIGN KEY(technician_id) REFERENCES users(user_id),
        CONSTRAINT fk_requesterid FOREIGN KEY(requester_id) REFERENCES users(user_id)
);

INSERT INTO property (address, rent, is_available, tenant_id) VALUES ('1234 Jimmy St', 1200, true, 1);
INSERT INTO property (address, rent, is_available) VALUES ('5678 John Dr', 2100, false);
INSERT INTO property (address, rent, is_available, tenant_id) VALUES ('9012 Luke Boulevard', 1800, true, 2);

INSERT INTO inquiry (email, phone, message, property_id) VALUES ('someone@yahoo.com', '123-456-7890', 'I am intersted in renting this apartment', 1);

INSERT INTO transactions (time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id)
VALUES ('10/1/2021', '11/1/2021', 1200, 1200, 1, 1);

INSERT INTO maintenance_type (description) VALUES ('Sink is leaking water');

INSERT INTO maintenance_request (property_id, type_id, technician_id, requester_id, time_stamp, is_complete, email, phone, name)
VALUES (1, 1, 2, 1, '12/8/2021', false, 'requester@gmail.com', '2345678901', 'John');
 
 
COMMIT TRANSACTION;
