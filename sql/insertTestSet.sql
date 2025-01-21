INSERT INTO clients (nom, prenom, email, no_tel, rue, code_postal, ville) VALUES
('Hanks', 'Tom', 'tom.hanks@example.com', '+1-555-123-4567', '123 Hollywood Blvd', '90001', 'Los Angeles'),
('Streep', 'Meryl', 'meryl.streep@example.com', '+1-555-987-6543', '456 Broadway', '10001', 'New York'),
('DiCaprio', 'Leonardo', 'leo.dicaprio@example.com', '+1-555-789-0123', '789 Sunset Blvd', '90028', 'Hollywood');
insert into genres (no_genre, libelle)
values (1, 'Collaboratif'), (2, 'Cartes'), (3, 'Plateau'), (4, 'Strategie')
,(5, 'Jeu de rôle'),(6, 'Educatif');
INSERT INTO JEUX (titre, reference, description, tarif_journee, ageMin, duree)
VALUES 
('Monopoly', 'MONO001', 'Jeu de société classique de gestion immobilière', 3.50, 8, 120),
('Cluedo', 'CLUE001', 'Jeu de déduction pour résoudre un meurtre mystérieux', 3.00, 8, 60),
('Welcome', 'WELC001', 'Jeu de société de construction de quartier', 2.50, 10, 25),
('Skyjo', 'SKYJ001', 'Jeu de cartes stratégique et familial', 2.00, 8, 30),
('Donjon & Dragon', 'D&D001', 'Jeu de rôle fantastique', 5.00, 12, 240),
('Catan', 'CATA001', 'Jeu de stratégie et de développement', 3.50, 10, 90);
insert into jeux_genres (no_jeu, no_genre)
values (1, 3), (2, 3), (3, 3), (4, 2), (5, 5), (6, 4);

INSERT INTO ROLES VALUES ('CLIENT')
INSERT INTO ROLES VALUES ('ADMIN')
INSERT INTO UTILISATEURS (pseudo_utilisateur, mot_de_passe) VALUES ('admin', '{bcrypt}$2a$10$Ccwq/gX3VJPYbuXArteqiuDBQwCYr0AIv9tJf6KgpgFGkRw8B9JOm');
INSERT INTO UTILISATEURS (pseudo_utilisateur, mot_de_passe) VALUES ('client', '{bcrypt}$2a$10$7JgxiZGEvY7SZvzaF2xhLOU189QMHql0rGS7Q7KM/60qQw9zvv.Pa');
INSERT INTO UTILISATEURS_ROLES VALUES (1, 'ADMIN');
INSERT INTO UTILISATEURS_ROLES VALUES (2, 'CLIENT');
