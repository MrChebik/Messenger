# Messenger
Messenger it's a program, who help you to communicate with others people who are in this program.

Technologies, that are used:
- AWT
- Log4j
- JUnit
- Maven
- MySQL
- Sockets
- Swing
- XML

The current version of the project, looks like a usual chat.

## Before you start running
- I do not keep the server.
- Without a running server, the client will not start.

> Linux users can manually create a database (createDB.sh), but since version 0.07 server can create the database if it does not exist.

## Running
```
mvn package
java -jar ./Server/target/server-0.07.jar
java -jar ./Client/target/client-0.07.jar
```
> Linux users can execute 'run.sh' in every moduls, i.e ```sh ./Server/run.sh```.

If you want to change language to RU, you can to execute with argument **_ru_** (only Client, available for _.sh_ file).

## Release Notes
If you want to see release notes, you can go to the next link [EN] (https://github.com/MrChebik/Messenger/blob/master/Release_Notes_en.txt) (may be some errors) or [RU] (https://github.com/MrChebik/Messenger/blob/master/Release_Notes_ru.txt).

## License
[GNU General Public License, version 3 (GPL-3.0)] (https://opensource.org/licenses/GPL-3.0)

© 2016 MrChebik