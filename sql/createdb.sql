

drop table if exists clients;
drop table if exists jeux_genres;
drop table if exists genres;
drop table if exists exemplaires_jeux;
drop table if exists jeux;

CREATE TABLE clients (
    no_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    no_tel VARCHAR(20),
    rue VARCHAR(255) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(100) NOT NULL
);

CREATE TABLE GENRES (
	no_genre INTEGER PRIMARY KEY,
	libelle varchar(100) UNIQUE NOT NULL
);

CREATE TABLE JEUX (
    no_jeu SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    reference VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    tarif_journee NUMERIC(8,2) NOT NULL,
    ageMin INTEGER NOT NULL,
	duree INTEGER NOT NULL
);

CREATE TABLE JEUX_GENRES (
    no_jeu INTEGER NOT NULL,
    no_genre INTEGER NOT NULL,
    PRIMARY KEY (no_jeu, no_genre),
    FOREIGN KEY (no_jeu) REFERENCES JEUX(no_jeu),
    FOREIGN KEY (no_genre) REFERENCES GENRES(no_genre)
);

CREATE TABLE EXEMPLAIRES_JEUX (
    no_exemplaire_jeu SERIAL PRIMARY KEY,
	no_jeu INTEGER NOT NULL,
    codebarre VARCHAR(13) UNIQUE NOT NULL,
	louable BOOLEAN NOT NULL DEFAULT TRUE,
	FOREIGN KEY (no_jeu) REFERENCES JEUX(no_jeu)
);

-- Merci d'exécuter les scripts createProcedure.sql et insertTestSet.sql après ce script.

