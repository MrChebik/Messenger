#!bin/sh
createDatabase() {
  mysql -u $1 -p$2 -e "
CREATE DATABASE Messenger;
USE Messenger;
CREATE TABLE Users(id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(12) UNIQUE, password VARCHAR(16), PRIMARY KEY(id));"
}

echo "Necessary database: MySQL"
echo "Choose way to create database (1/2):
-Automatically
-Setting"
read way
if [ $way -eq 1 ]
then
  if [ -d  ./Server/src/main/resources ]
  then
    cd ./Server/src/main/resources
    if [ -e ./config_server.properties ]
    then
      login=`grep "db.login" ./config_server.properties | cut -d '=' -f 2`
      passwd=`grep "db.password" ./config_server.properties | cut -d '=' -f 2`
      createDatabase $login $passwd
    else
      echo "'config_server.properties' does not exists"
    fi
  else
    echo "'./Server/src/main/resources' does not directory"
  fi
elif [ $way -eq 2 ]
then
  echo -n "Username: "
  read username
  echo -n "Password: "
  read -s password
  echo
  createDatabase $username $password
else
  echo "Way not found"
fi