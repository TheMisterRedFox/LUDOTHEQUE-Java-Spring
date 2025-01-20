CREATE OR REPLACE PROCEDURE supprimer_jeu(no_jeu_param INT)
AS
$$
BEGIN

DELETE FROM JEUX_GENRES
WHERE no_jeu = no_jeu_param;

DELETE FROM EXEMPLAIRES_JEUX
WHERE no_jeu = no_jeu_param;

DELETE FROM JEUX
WHERE no_jeu = no_jeu_param;
END;
$$ LANGUAGE plpgsql;