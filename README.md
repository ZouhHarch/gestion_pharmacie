# 📦 Gestion de Pharmacie - JavaFX

Application de gestion de pharmacie développée dans le cadre du module **Développement Java IHM** (2025/2026). Elle permet de gérer les médicaments et les ventes avec une interface graphique moderne et intuitive.

---

## 🚀 Fonctionnalités

| Fonctionnalité | Description |
|----------------|-------------|
| 💊 **Gestion des médicaments** | Ajouter, modifier, supprimer et rechercher des médicaments |
| 🛒 **Gestion des ventes** | Enregistrer une vente avec sélection du médicament et quantité |
| 📋 **Historique des ventes** | Consulter toutes les ventes effectuées |
| 📊 **Statistiques** | Afficher le total des médicaments, le stock faible, le chiffre d'affaires, le nombre de ventes |
| 📈 **Graphique** | Visualiser les ventes par médicament sous forme de barres |
| 📤 **Export CSV** | Exporter les données des médicaments et des ventes vers un fichier CSV |
| 🎨 **Interface moderne** | Design épuré avec feuille de style CSS personnalisée |

---

## 🧰 Prérequis

Avant de lancer l'application, assurez-vous d'avoir installé :

| Outil | Version | Téléchargement |
|-------|---------|----------------|
| **JDK** | 17 ou supérieur | https://adoptium.net/ |
| **JavaFX SDK** | 17 | https://gluonhq.com/products/javafx/ |
| **MySQL** | 8.0 ou supérieur | https://dev.mysql.com/downloads/mysql/ |
| **IntelliJ IDEA** | 2024 ou supérieur | https://www.jetbrains.com/idea/download/ |
| **Git** | (optionnel) | https://git-scm.com/ |

---

## 📁 Structure du projet

gestion_pharmacie/

├── src/
│ ├── Main.java
│ ├── main.fxml
│ ├── medicaments.fxml
│ ├── ventes.fxml
│ ├── statistiques.fxml
│ ├── style.css
│ ├── dao/
│ │ ├── Database.java
│ │ ├── MedicamentDAO.java
│ │ └── VenteDAO.java
│ ├── model/
│ │ ├── Medicament.java
│ │ └── Vente.java
│ └── controller/
│ ├── MainController.java
│ ├── MedicamentController.java
│ ├── VenteController.java
│ └── StatistiquesController.java
├── lib/
│ └── mysql-connector-j-9.7.0.jar
├── database/
│ └── script.sql
├── README.md
└── .gitignore

---

## 🗄️ Installation de la base de données

### 1. Démarrer MySQL

Assurez-vous que MySQL est bien démarré sur votre machine (WampServer, XAMPP, ou service MySQL).

### 2. Créer la base de données

Ouvrez un terminal MySQL et exécutez :

sql

CREATE DATABASE pharmacie_db;
USE pharmacie_db;

3. Exécuter le script SQL
Exécutez le fichier database/script.sql pour créer les tables et insérer des données de test :

sql

SOURCE C:/chemin/vers/database/script.sql;
Ou copiez-collez le contenu du fichier dans le terminal MySQL.

---


▶️ **Lancement de l'application**

Option 1 : Depuis IntelliJ IDEA
Ouvrez le projet dans IntelliJ IDEA

Assurez-vous que le SDK est bien configuré (JDK 17+)

Ajoutez les bibliothèques :

javafx-sdk-17/lib → comme librairie

mysql-connector-j-9.7.0.jar → depuis le dossier lib/

Ouvrez Main.java et cliquez sur le triangle vert

Option 2 : Depuis le terminal
bash
java --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml -cp "out/production/PharmaAPP;C:\javafx-sdk-17\lib\*;lib/mysql-connector-j-9.7.0.jar" Main

---

🖥️ **Captures d'écran**

Fenêtre principale
..

Gestion des médicaments
..

Gestion des ventes
..

Statistiques
..

---

🛠️ **Technologies utilisées**

Java 17	Langage de programmation
JavaFX 17	Interface graphique
MySQL 8	Base de données relationnelle
JDBC	Connexion Java ↔ MySQL
CSS	Personnalisation de l'interface
FXML	Définition des vues
Git & GitHub	Versionnage et collaboration

---


📝 **Développé par**

Zouhir Harchaoui	Back-end (DAO, Modèles, Base de données)
Mohamed-Taha Hrimache	Front-end (FXML, CSS, Contrôleurs, UI)
Encadrante : Mme Douae EL HILA

Module : Développement Java IHM

Année universitaire : 2025/2026

---

🔗 **Liens utiles**

Documentation JavaFX : https://openjfx.io/

Documentation MySQL : https://dev.mysql.com/doc/

Télécharger JDK 17 : https://adoptium.net/

Télécharger JavaFX SDK : https://gluonhq.com/products/javafx/

---

📧 **Contact**

Pour toute question, vous pouvez contacter les développeurs à l'adresse suivante :

medtahahrm@gmail.com

harchaouizouhir2005@gmail.com

Merci d'avoir utilisé notre application ! 🎉

---

📝 **Notes supplémentaires**

Si vous rencontrez l'erreur JavaFX runtime components are missing, assurez-vous d'ajouter les VM options :

--module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml

Si vous avez un problème de connexion MySQL, vérifiez le mot de passe dans Database.java :

private static final String PASSWORD = "votre_mot_de_passe";

---


🎯 **Vidéo de démonstration**

Lien vers la vidéo de démonstration : ..
