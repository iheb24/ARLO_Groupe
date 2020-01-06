x=$(cat application.yml | sed -nE 's/.*server.port:..([^"]*).*/\1/p')

#ngrok http $x &

java -jar server.jar --spring.config.location=./application.yml 
