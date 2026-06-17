-- =====================================================================
-- SCRIPT DE CRÉATION DE LA BASE DE DONNÉES : GESTION DE PHARMACIE
-- =====================================================================

-- Suppression des tables si elles existent déjà (ordre critique pour les clés étrangères)
DROP TABLE IF EXISTS vente;
DROP TABLE IF EXISTS medicament;

-- 1. Création de la table 'medicament'
CREATE TABLE medicament (
                            id INT PRIMARY KEY, -- ID manuel choisi par le pharmacien (Code CIP / Code-barres)
                            nom VARCHAR(150) NOT NULL,
                            laboratoire VARCHAR(100),
                            dosage DOUBLE NOT NULL,
                            prix DOUBLE NOT NULL,
                            stock INT NOT NULL DEFAULT 0,
                            ordonnance BOOLEAN NOT NULL DEFAULT FALSE,
                            categorie VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Création de la table 'vente'
CREATE TABLE vente (
                       id INT AUTO_INCREMENT PRIMARY KEY, -- ID auto-incrémenté géré par MySQL
                       date DATE NOT NULL,
                       quantite INT NOT NULL,
                       total DOUBLE NOT NULL,
                       medicament_id INT NOT NULL,
    -- Déclaration de la contrainte de clé étrangère vers la table medicament
                       CONSTRAINT fk_vente_medicament
                           FOREIGN KEY (medicament_id)
                               REFERENCES medicament(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================================
-- INSERTS DE TEST (Optionnel - pour remplir tes tables au départ)
-- =====================================================================
INSERT INTO medicament (id, nom, laboratoire, dosage, prix, stock, ordonnance, categorie) VALUES
                                                                                              (101, 'Doliprane', 'Sanofi', 1000.0, 15.50, 100, FALSE, 'Analgésique'),
                                                                                              (102, 'Amoxicilline', 'Biogaran', 500.0, 42.00, 30, TRUE, 'Antibiotique');

INSERT INTO vente (date, quantite, total, medicament_id) VALUES
                                                             ('2026-06-17', 2, 31.00, 101),
                                                             ('2026-06-17', 1, 42.00, 102);