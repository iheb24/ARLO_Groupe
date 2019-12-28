# ARLO_Groupe

# Complément phase 2 
- Le type de L'ID a changé de entier en string (changement sur inteface graphique + base de données)
- L'ajout des champs suivants sur l'interface graphique et la base de données (email, code unité enseignement, TD, TP, Valeur ) .
- Création d'un bouton mise à jour (update) des valeurs (eleve et unité enseignement ) sur l'interface graphique et le serveur 
- Changement de l'API en REST 
- Resolution des dépendances externes du serveur et packaging du serveur en jar
- Démarrage du serveur en une seule commande 
# Lancement Client-serveur
## installation & Lancement kafka 
(version tested  kafka_2.12-2.4.0)
````sh
wget http://mirrors.ircam.fr/pub/apache/kafka/2.4.0/kafka_2.12-2.4.0.tgz
tar xvf kafka_2.12-2.4.0.tgz
cd kafka_2.12-2.4.0
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
````
## installation & Lancement ngrok 
````sh
snap install ngrok 
# brew install ngrok 
# add token (premier démarrage) 
 ngrok authtoken (copier le token depuis PROCÉDURE DE COMPILATION(page 8) dans le fichier pdf)
````
## Lancement du serveur
````sh
cd executables
./start-server.sh
````
## Lancement client
````
cd executables
# copier l'adresse du serveur dans urlConfig.ini
java -jar client.jar
````
# Fichiers de logs 
un fichier de log est automatiquement créer dans le répertoire courant `log.txt`
## Exemples de logs
### Execution réussite 
![reussi](png/log1.png)

### Execution echoué ( kafka non démarré )
![reussi](png/log2.png)
