# Messenger
Messenger it's a program, who help you to communicate with others people who are in this program.

Technologies, that are used:
- AWT
- Log4j
- Maven
- MySQL
- Sockets
- Swing
- XML

On the current version of the project, it looks like a usual chat

## Before you start running
- I do not keep the server
- Without a running server, the client will not start
- You must create database for Server:

```
sh createDB.sh
```

> For Windows users, you must independently create database and table with special fields (in createDB.sh you can get more details).

## Running
```
mvn package
java -jar ~/Server/target/server-0.06.jar
java -jar ~/Client/target/client-0.06.jar
```
If you want to change language to RU, you can to execute with argument **_ru_**.

## License
[GNU General Public License, version 3 (GPL-3.0)] (https://opensource.org/licenses/GPL-3.0)

© 2016 MrChebik
