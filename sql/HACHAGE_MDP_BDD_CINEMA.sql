-- ATTENTION ! Ne pas exécuter ces requêtes si les mots de passe dans la base de données sont déja hachés.

-- Cette suite de requêtes sert à convertir le type de données des mots de passe et de chiffrer ceux existants, mais affichés en clair dans la base de données.
-- Le chiffrage s'effectue sur toutes les lignes.

-- 0. Activation de l'extension de chiffrage
-- Temporaire, le temps de hacher les mots de passe
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 1. Conversion des mots de passe en text(pour le hachage)
ALTER TABLE utilisateur ALTER COLUMN mdp TYPE text;

-- 2. Hachage des mots de passe en BCrypt(salage a 64 itérations)
UPDATE utilisateur SET mdp = crypt(mdp, gen_salt('bf',6));

-- 3. Désactivation de l'extension de chiffrage
DROP EXTENSION pgcrypto;