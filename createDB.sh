#!bin/sh
echo "Create database 'Messenger'"
echo -n "Username: "
read username
echo -n "Password: "
read -s password
echo
mysql -u $username -p$password -e "
CREATE DATABASE Messenger;
USE Messenger;
CREATE TABLE Users(id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(12) UNIQUE, password VARCHAR(16), PRIMARY KEY(id));"
