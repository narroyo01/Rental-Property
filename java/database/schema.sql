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

INSERT INTO users (username,password_hash,role) VALUES ('maintenance1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_MAINTENANCE');
INSERT INTO users (username,password_hash,role) VALUES ('maintenance2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_MAINTENANCE');

INSERT INTO users (username,password_hash,role) VALUES ('landlord1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_LANDLORD');
INSERT INTO users (username,password_hash,role) VALUES ('landlord2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_LANDLORD');

INSERT INTO users (username,password_hash,role) VALUES ('user2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user4','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

-- new content

CREATE TABLE property (
        property_id INT DEFAULT nextval('seq_property_id'::regclass),
        address VARCHAR(100),
        rent INT,
        is_available BOOLEAN,
        tenant_id INT,
        image_url VARCHAR(500),
        
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
        comments VARCHAR(1000),
        
        CONSTRAINT pk_maintenancerequest PRIMARY KEY(maintenance_request_id),
        CONSTRAINT fk_propertyid FOREIGN KEY(property_id) REFERENCES property(property_id),
        CONSTRAINT fk_typeid FOREIGN KEY(type_id) REFERENCES maintenance_type(maintenance_type_id),
        CONSTRAINT fk_technicianid FOREIGN KEY(technician_id) REFERENCES users(user_id),
        CONSTRAINT fk_requesterid FOREIGN KEY(requester_id) REFERENCES users(user_id)
);

INSERT INTO property (address, rent, is_available, image_url) VALUES ('1234 Jimmy St', 1200, true, 'https://media.istockphoto.com/photos/exterior-of-small-american-house-with-blue-paint-picture-id589538090?k=20&m=589538090&s=612x612&w=0&h=5obd9GWPEDn4AzSe7TYlBeLbGKtzOol-xPf0nL474eY=');
INSERT INTO property (address, rent, is_available, image_url) VALUES ('5678 John Dr', 2100, false, 'https://images.pexels.com/photos/1396122/pexels-photo-1396122.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500');
INSERT INTO property (address, rent, is_available, tenant_id, image_url) VALUES ('9012 Luke Boulevard', 1800, true, 7, 'https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500');
INSERT INTO property (address, rent, is_available, tenant_id, image_url) VALUES ('223 Street St', 1400, false, 8, 'https://www.khov.com/blog/wp-content/uploads/2019/08/Ranch-Style-Home-at-K.-Hovnanians-Four-Seasons-at-Belle-Terre.jpg');
INSERT INTO property (address, rent, is_available, tenant_id, image_url) VALUES ('649 Road Rd', 900, false, 9, 'https://imgix.cosmicjs.com/31645c30-6cc5-11eb-bb6f-f146facd4cdc-shutterstock1188162424.jpg');
INSERT INTO property (address, rent, is_available, tenant_id, image_url) VALUES ('651 Road Rd', 950, false, 9, 'https://imgix.cosmicjs.com/31645c30-6cc5-11eb-bb6f-f146facd4cdc-shutterstock1188162424.jpg');
INSERT INTO property (address, rent, is_available, image_url) VALUES ('491 Circle Cir', 1950, true, 'https://cdn.shopify.com/s/files/1/0043/8471/8938/products/163604829176012541_812x.jpg?v=1637609172');
INSERT INTO property (address, rent, is_available, image_url) VALUES ('3915 Highway Hwy', 1200, true, 'https://cdn.houseplansservices.com/product/mqftss0ua1lo14mkqohojlvq0h/w620x413.jpg?v=8');
INSERT INTO property (address, rent, is_available, image_url) VALUES ('12 Court Ct', 2200, true, 'https://www.familyhomeplans.com/varnish-images/plans/56705/56705-b600.jpg');


INSERT INTO inquiry (email, phone, message, property_id) VALUES ('someone@yahoo.com', '123-456-7890', 'I am intersted in renting this apartment', 1);

INSERT INTO transactions (time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id)
VALUES ('10/1/2021', '11/1/2021', 1400, 1400, 4, 8),('10/1/2021', null, 900, null, 5, 9);

INSERT INTO maintenance_type (description) VALUES ('Plumbing'),('Structural'),('Electrical'),('HVAC'),('Exterior'),('Other');

INSERT INTO maintenance_request 
(property_id, type_id, technician_id, requester_id, time_stamp, is_complete, email, phone, name)
VALUES 
(1, 1, 3, 2, '12/8/2021', false, 'john@gmail.com', '2345678901', 'John Jonaby'),
(2, 2, 4, 5, '12/8/2021', false, 'doug@gmail.com', '2345678901', 'Doug Donnelly'),
(7, 3, 3, 2, '12/8/2021', false, 'guy@gmail.com', '2345678901', 'Guy Guillermo'),
(7, 4, null, 5, '12/8/2021', false, 'jason@gmail.com', '2345678901', 'Jason Jarr'),
(8, 5, null, 6, '12/8/2021', false, 'thomas@gmail.com', '2345678901', 'Thomas Tore'),
(9, 6, null, 2, '12/8/2021', false, 'george@gmail.com', '2345678901', 'George Wash');
 
 
COMMIT TRANSACTION;
