-- liquibase formatted sql

-- changeset dimi:1759223487337-1
INSERT INTO person (id, name, birth_date, city, street, building)
VALUES (1000, 'Ivan', '1990-01-01', 'Moscow', 'Lenina', 10),
        (2, 'Anna Ivanova', '1985-05-05', 'Kazan', 'Pushkina', 15),
        (3, 'Mike', '1985-05-05', 'Stuttgart', 'Hauptstr', 1);




