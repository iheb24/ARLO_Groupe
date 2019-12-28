# Phase1_Groupe

## Général
Pour éditer le code, voici la procédure avec l’IDE Eclipse :
- Installer JavaFX : 
help -> Eclipse market -> Rechercher “javafx” -> e(fx)clipse
- Importer le projet
- Avoir Java JDK 1.8

Nécessite le jar “sqlite-jdbc-3.27.2.1.jar”
https://bitbucket.org/xerial/sqlite-jdbc/downloads/
Pour l’intégrer dans le projet il y a deux méthodes :
- soit le copier à la racine du projet , ensuite cliquer droit sur le projet build path -> configure build path -> libraries -> add Jars -> choisir le jar qui est à la racine -> apply and close
- soit l’importer depuis un dossier local en cliquant droit sur le projet build path -> configure build path -> libraries -> add External JARs -> choisir le jar que vous avez téléchargé sur votre ordinateur

Identifiant de connexion temporaire
    admin ; admin


## Architecture générale
Modèle MVC(Modèle-Vue-Contrôleur)
Chaque vue possède un modèle et un contrôleur. 

Le fichier MainApp.java lance la fênetre ainsi que la 1ère interface de connexion.
le MainApp.java lance également notre base de données locale par l’appel : DataBaseManager.initialisationBD()  qui va créer un fichier groupe.db à la racine du projet si celui-ci n’existe pas déjà, puis stocker la connexion à cette base dans un attribut de type Connection. Elle va ensuite créer les différentes tables si elles n’existent pas déjà.

Toute la gestion de la base de données se fait grâce à la classe DataBaseManager. 
Les fonctions sont séparées en quatre parties qui correspondent aux quatre opérations CRUD.

## JAR Runnable
- Eclipse : clique droit sur le projet > Export > Dossier "Java", choisir "Jar runnable"
- IntelliJ : File > Project Structure > Artifact > + > "Jar" "from module" > Choisir la classe "MainApp" du dossier "Main" > Puis "Ok". 
Ensuite, il faut aller dans "Build" > "Build artifact" > "Build"

## Procédure d’installation
Exécuter le programme qui est un "Jar Runnable"
 
## Procédure de désinstallation 
Actuellement, aucune installation n’est requise, par conséquent, aucune désinstallation.


## Environnement matériel
Aucune spécification particulière, code compilable sous Windows, Mac OS et Linux.

## Environnement logiciel
- Java 8 (JDK 1.😎
- JavaFx 8 
- SQLite : système de gestion de base de données léger, gratuit, facile à implémenter et fortement documenté. Pour l’utilisation d’une telle base de données nous utilisons sqlite-jdbc.jar situé à la racine du projet()
- Scene Builder : outil interactif de conception d'interface graphique pour JavaFX. Ceci n’est pas indispensable pour le fonctionnement de l’application mais cet outil aide à concevoir des interfaces rapidement et facilement.

## Procédure de compilation
javac
Néanmoins, nous avons utiliser Eclipse ou IntelliJ pour compiler et nous n'avons pas la ligne de compilation précise.
On utilise "Run" du MainApp.java (dossier "Main") avec Eclipse ou IntelliJ

## Anomalies détectées
Aucune anomalie détectée par notre équipe

## Entrées et sorties des composants
Notre application est basé sur une architecture MVC , donc 3 composant principaux :

- Modèle: 
  Entrée : la requête invoquée
  Sortie : Exécution de la requête 
- Contrôleur:
 Entrée :  Demande de l’utilisateur
 Sortie: Réponse à l’utilisateur , Lancement d’une requête(Appel du modèle)
- Vue : 
  Entrée :  données du modèle 
  Sortie : l’interface mise à jour

## Exigences réalisées
- [Req-doc-01] La documentation doit décrire la procédure d'installation.
- [Req-doc-02] La documentation doit décrire la procédure de désinstallation.
- [Req-doc-03] La documentation doit décrire Les entrées et sorties de chaque composant.
- [Req-doc-04] La documentation doit décrire l'environnement matériel nécessaire à l'installation.
- [Req-doc-05] La documentation doit décrire l'environnement logiciel nécessaire à l'installation.
- [Req-doc-06] La documentation doit décrire le cheminement des appels de fonctions pour 2 fonctionnalités.
- [Req-doc-07] La documentation doit lister les exigences satisfaites.
- [Req-doc-08] La documentation doit lister les exigences non satisfaites.
- [Req-doc-09] La documentation doit lister les anomalies.
- [Req-doc-10] La documentation doit décrire la procédure de compilation.
			
- [Req-exp-01] Le système doit fonctionner sous Linux.
- [Req-exp-02] Le système doit fonctionner sous Windows.
- [Req-exp-03] Le système doit fonctionner sous Mac OS.
- [Req-exp-04] Le système doit être développé en Java 8.
			
- [Req-liv-01] La livraison doit contenir tous les éléments nécessaires à la génération de la version binaire.
> Jar sur eclipse : https://www.cs.utexas.edu/~scottm/cs307/handouts/Eclipse%20Help/jarInEclipse.htm
- [Req-liv-02] La livraison doit contenir la version binaire du système.
- [Req-liv-03] La livraison doit contenir toute la documentation.
			
- [Req-arc-01] Le système est constitué d'un seul exécutable.
- [Req-arc-02] Le programme principal instancie deux objets : l'un implémentant l'IHM et l'autre exposant les fonctionnalités.
- [Req-arc-03] Les communications entre IHM et fonctions passent par une unique interface Java.
- [Req-arc-04] Les requêtes vont uniquement de l'IHM vers l'objet exposant les fonctions.
- [Req-arc-05] Les opérations exposées par l'interface sont de 4 types : lecture, création, modification, suppression d'un objet.
- [Req-arc-07] L'identifiant des objets créés est attribuée par l'IHM.
			
- [Req-fct-01] Après re-démarrage du système il est dans le même état qu'avant son arrêt (données).
- [Req-fct-02] Il est possible de mettre à jour l'IHM (automatiquement ou à la demande de l'utilisateur)

- [Req-gro-01] L'utilisateur peut créer une Unité d'Enseignement.
- [Req-gro-02] L'utilisateur peut supprimer une Unité d'Enseignement.
- [Req-gro-03] L'utilisateur peut créer un élève.
- [Req-gro-04] L'utilisateur peut supprimer un élève.
- [Req-gro-05] L'utilisateur peut créer un sujet.
- [Req-gro-06] L'utilisateur peut supprimer un sujet.
- [Req-gro-07] L'utilisateur peut créer un groupe (UE – élèves – sujet)
- [Req-gro-08] L'utilisateur peut supprimer un groupe (UE – élèves – sujet)
- [Req-gro-09] L'utilisateur peut créer aléatoirement des groupes pour un ensemble d'élèves.
- [Req-gro-10] L'utilisateur peut changer un élève de groupe.


## Exigences non réalisées
- [Req-arc-06] Les classes implémentant l'IHM et les fonctionnalités sont packagées dans des jar distincts.
Nous n'avons pas trouvé de moyen de packager le "back" et le "front" dans deux jars séparés.


## Cheminement des appels de fonctions pour 2 fonctionnalités

Ajouter un élève :
1. La méthode start (Scene primaryStage ) qui est dans le MainApp.java est automatiquement appelée. Cette méthode démarre l’application et affiche l’interface de connexion.
2. Dans l’interface de connexion, quand on entre le login et mot de passe(Admin et Admin)  et nous validons, la fonction handleSubmitButtonAction(ActionEvent event) appelé dans le contrôleur de la connexion.
Si les champs ne sont pas vides, la fonction qui affiche la vue de l’élève est appelé.
3. Pour ajouter un élève, remplir les trois champs id , nom , prénom et cliquez sur Enregistrer.La fonction handleEnregistrerButtonAction(ActionEvent event) est appelé, crée un élève en Utilisant le model Eleve.java et lance la requête insert.
4. La requete Insert se trouve dans DataBaseManager.java qui est la classe qui gére notre de base de données, dans lequel on trouve toutes les requêtes CRUD.

Suppression d’un élève :
1. Dans l’interface Elève ,pour supprimer un élève on sélectionne l'élève dans la table le handler du clique sera appelé qui affiche dans les champs de textes l’id le nom et le prénom de l’élève selectionné.
2. Pour supprimer cet élève on clique sur supprimer.la Requete Delete du DataBaseManager sera appelé.

3. On clique sur le bouton “ actualiser “ pour appeler de nouveau la vue de l'élève afin de la mettre à jour