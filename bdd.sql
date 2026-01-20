CREATE DATABASE IF NOT EXISTS PROG_BD;
USE PROG_BD;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS programmer_project;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS programmers;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE programmers
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    first_name  VARCHAR(100)   NOT NULL,
    address     VARCHAR(255)   NULL,       
    username    VARCHAR(50)    NOT NULL UNIQUE,
    manager     VARCHAR(100)   NULL,
    hobby       VARCHAR(100)   NULL,
    birth_year  INT            NOT NULL,
    salary      DECIMAL(10, 2) NOT NULL,
    bonus       DECIMAL(10, 2) NULL
);

CREATE TABLE projects
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    start_date  DATE         NOT NULL,
    end_date    DATE         NULL,
    state       VARCHAR(50)  NOT NULL
);

CREATE TABLE programmer_project
(
    programmer_id INT NOT NULL,
    project_id    INT NOT NULL,
    PRIMARY KEY (programmer_id, project_id),
    FOREIGN KEY (programmer_id) REFERENCES programmers (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE
);

INSERT INTO programmers (name, first_name, address, username, manager, hobby, birth_year, salary, bonus) VALUES 
('Torvalds', 'Linus', '2 avenue Linux Git', 'linuxroot', 'Didier Achvar', 'Salsa', 1969, 2170.00, 50.00),
('Stroustrup', 'Bjarne', '294 rue C++', 'c++1', 'Karim Lahlou', 'Voyages', 1950, 2466.00, 80.00),
('Gosling', 'James', '3 bvd JVM', 'javapapa', 'Jacques Augustin', 'Peinture', 1955, 1987.00, 10.00),
('Ritchie', 'Dennis', '6 rue des Pointeurs', 'comoi', 'Didier Achvar', 'Boxe', 1941, 1688.00, 88.00),
('Lovelace', 'Ada', '10 Lovelace Lane', 'firstone', 'Charles Babbage', 'Maths', 1815, 3000.00, 100.00);

INSERT INTO projects (name, start_date, end_date, state) VALUES 
('Projet 2025', '2025-01-01', '2025-06-30', 'En cours'),
('Refonte Site Web', '2024-09-01', '2024-12-31', 'Terminé'),
('Migration BDD', '2025-03-15', NULL, 'Planifié');

INSERT INTO programmer_project (programmer_id, project_id) VALUES
((SELECT id FROM programmers WHERE username = 'linuxroot'), (SELECT id FROM projects WHERE name = 'Projet 2025')),
((SELECT id FROM programmers WHERE username = 'javapapa'), (SELECT id FROM projects WHERE name = 'Projet 2025')),

((SELECT id FROM programmers WHERE username = 'c++1'), (SELECT id FROM projects WHERE name = 'Refonte Site Web')),

((SELECT id FROM programmers WHERE username = 'comoi'), (SELECT id FROM projects WHERE name = 'Migration BDD'));