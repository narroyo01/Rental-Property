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
        description VARCHAR(2000),
        
        CONSTRAINT pk_property PRIMARY KEY(property_id),
        CONSTRAINT fk_tenantid FOREIGN KEY(tenant_id) REFERENCES users(user_id)
);

CREATE TABLE inquiry (
        inquiry_id INT DEFAULT nextval('seq_inquiry_id'::regclass),
        name VARCHAR(100),
        email VARCHAR(100),
        phone VARCHAR(20),
        message VARCHAR(300),
        property_id INT,
        
        CONSTRAINT pk_inquiry PRIMARY KEY(inquiry_id),
        CONSTRAINT fk_propertyid FOREIGN KEY(property_id) REFERENCES property(property_id)
);

CREATE TABLE transactions (
        transaction_id INT DEFAULT nextval('seq_transaction_id'::regclass),
        time_initiated date,
        time_paid date DEFAULT '01/01/2000',
        amount_due INT,
        amount_paid INT DEFAULT 0,
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

INSERT INTO property (address, rent, is_available, image_url, description) VALUES ('1234 Jimmy St', 1200, true, 'https://media.istockphoto.com/photos/exterior-of-small-american-house-with-blue-paint-picture-id589538090?k=20&m=589538090&s=612x612&w=0&h=5obd9GWPEDn4AzSe7TYlBeLbGKtzOol-xPf0nL474eY=', 'Barely a block from North High Street. Walking distance to OSU, close to Fisher College of Business. 6 Bedrooms and 2 Full Bathrooms. Huge living room and dining room. Brand new kitchen appliances. New ACs and Furnaces. Laundry room is in the house. Waterproofed Luxury Vinyl Flooring thru entry level. Detached basement offers additional storage space. 6 off-street parking spots and a huge backyard. Cannot miss!');
INSERT INTO property (address, rent, is_available, image_url, description) VALUES ('5678 John Dr', 2100, false, 'https://images.pexels.com/photos/1396122/pexels-photo-1396122.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','8 Bedroom Flat Just Off Indianola and Minutes From Campus - Flat with 8 bedrooms and a large kitchen. The kitchen is equipped with multiple stainless steel refrigerators, built in microwave, and dishwasher, all laid out on ceramic tile. Ceiling fans in every bedroom, central AC and washer/dryer for your convenience.');
INSERT INTO property (address, rent, is_available, tenant_id, image_url, description) VALUES ('9012 Luke Boulevard', 1800, true, 7, 'https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500', ' a custom Georgian-inspired home nestled in the neighborhood of Kenbrook in Upper Arlington. Inside is the epitome of casual-formal living flooded with natural light. Features on the main level include a dream Wood-Mode kitchen, 2 fireplaces, formal dining & living room, family room, powder room, laundry, and a 3 car garage. The serene screened-in porch and rooftop terrace are truly magical spaces for entertaining. Take the main staircase or back stairs to the 2nd floor to see a master suite with fireplace, 2 secondary bedrooms with en suites, and 4th bedroom being used as a gym. Thoughtful attention to the details includes oversized windows with transoms, crown molding & rich baseboards throughout, historic door knobs, beveled edge shelving, and ample storage.');
INSERT INTO property (address, rent, is_available, tenant_id, image_url, description) VALUES ('223 Street St', 1400, false, 8, 'https://www.khov.com/blog/wp-content/uploads/2019/08/Ranch-Style-Home-at-K.-Hovnanians-Four-Seasons-at-Belle-Terre.jpg', 'Relax on the country porch overlooking the green space and pond. First floor features a formal dining room, family room, large kitchen with stainless steel appliances and an eating area with access to the extended rear patio and first floor laundry. Upstairs offers 4 nice sized bedrooms. The master suite has a tray ceiling, ensuite bath with a double basin vanity and large walk-in closet. Lawn Care Included.');
INSERT INTO property (address, rent, is_available, tenant_id, image_url, description) VALUES ('649 Road Rd', 900, false, 9, 'https://imgix.cosmicjs.com/31645c30-6cc5-11eb-bb6f-f146facd4cdc-shutterstock1188162424.jpg', 'Spacious 4/2 home in the Northern Woods neighborhood of Columbus. Nice sized kitchen has lots of cabinet space. Good sized family room that walks out to the back yard. Separate living room and a formal dining room.');
INSERT INTO property (address, rent, is_available, tenant_id, image_url, description) VALUES ('651 Road Rd', 950, false, 9, 'https://imgix.cosmicjs.com/31645c30-6cc5-11eb-bb6f-f146facd4cdc-shutterstock1188162424.jpg', 'Check out this BEAUTIFUL contemporary style home that has a versatile open floor plan. The great room opens into the eat in kitchen (with stainless steel appliances) and has a formal dining room as well. The spacious master suite includes a walk in closet and private bath. 3 nicely sized additional bedrooms and 2.5 baths. This home has wonderful outdoor space with a deck and patio that overlooks the large fenced yard. Storage for bikes and yard equipment tucked on the side of the back of the home. Desirable Canal Winchester school district.');
INSERT INTO property (address, rent, is_available, image_url, description) VALUES ('491 Circle Cir', 1950, true, 'https://cdn.shopify.com/s/files/1/0043/8471/8938/products/163604829176012541_812x.jpg?v=1637609172', 'Gorgeous house in the desired CRAMERS CROSSING subdivision, tons of upgrades.Washington Elementary Tharp Middle and Hilliard Davidson High school.');
INSERT INTO property (address, rent, is_available, image_url, description) VALUES ('3915 Highway Hwy', 1200, true, 'https://cdn.houseplansservices.com/product/mqftss0ua1lo14mkqohojlvq0h/w620x413.jpg?v=8', 'Beautiful Four Bedroom home with Two and a half Bathrooms, located in the City of Dublin is available for lease immediately. This home includes all you need in a kitchen, from beautiful cabinetry, to Granite countertops, stainless steel appliances, and hardwood floors throughout. Come to see this beautiful home with the amazing back patio area for all your grilling and leisure needs.');
INSERT INTO property (address, rent, is_available, image_url, description) VALUES ('12 Court Ct', 2200, true, 'https://www.familyhomeplans.com/varnish-images/plans/56705/56705-b600.jpg', 'A wonderful two-story home in the Dublin Schools! Freshly painted, hardwood floors, newer carpet, with a great open-concept kitchen/living area featuring quartz counters and stainless steel appliances, spacious bedrooms, a finished lower level, and ample storage. The home also has wonderful outdoor space - all in an established neighborhood near to all that the Dublin area has to offer: from great public spaces, fantastic entertaining districts, and highway access.');


INSERT INTO inquiry (name, email, phone, message, property_id) VALUES 
('John Smith', 'someone@yahoo.com', '123-456-7890', 'I am intersted in renting this apartment', 1), ('Ashley Brown', 'somebody@gmail.com', '098-765-4321', 'This condo looks lovely and has my interest', 3), ('Henrique Heranandez', 'hhernandez@hotmail.com', '321-654-0987', 'This place looks nice and is in my price range, would love tot alk more about it', 5);

INSERT INTO transactions (time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id)
VALUES ('10/1/2021', '11/1/2021', 1400, 1400, 4, 8), ('11/1/2021', '12/2/2021', 1560, 1560, 2, 7);

INSERT INTO transactions (time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id)
VALUES ('10/1/2021', '10/1/2021',  900, 0, 5, 9), ('11/1/2021', '11/1/2021', 900, 0, 5, 9);

INSERT INTO transactions (time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id)
VALUES ('10/1/2021', '11/10/2021', 1000, 500, 1, 2);

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
