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
 
 
COMMIT TRANSACTION;
