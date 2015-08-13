START TRANSACTION;

INSERT INTO owner (id, firstname, lastname, routingproperty) VALUES ('1', 'John', 'Doe', 1);
INSERT INTO card (id, encodeddata, owner_id, routingproperty) VALUES ('2', 'MC4wOmZhbHNl', '1', 1);

COMMIT;
