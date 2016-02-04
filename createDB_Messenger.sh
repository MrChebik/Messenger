#!bin/sh
mysql -e "CREATE DATABASE Messenger; USE Messenger; CREATE TABLE Users(id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(12) UNIQUE, password VARCHAR(16), PRIMARY KEY(id));" -u root -p
