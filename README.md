# Network-Chess
University project 2020. Chess playable with friend through network. Chess should not check the validity of moves or whenever game is over but the interface is provided a few buttons for this purpose. 

Steps to run the project:
1) First you should run the server from "Chess Server" folder
2) Now you can run chess themselves from Project.Chess folder.
3) First player should press "Start Game" button to create game
4) Second player should press "Find Game" button to connect

### Configuration

The project is built using JavaFX library (Project.Chess), in order to run the project you should download JavaFX library and make a few configuration steps. You can find the library [here](https://gluonhq.com/products/javafx/ "JavaFX library").
You can find instructions to configure your ide to work with JavaFX [here](https://www.jetbrains.com/help/idea/javafx.html "Configuration help").

VM options for the project:
```
--add-exports=javafx.base/com.sun.javafx.reflect=ALL-UNNAMED
--add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED
--module-path
*Your full path to JavaFX library, for example: C:\javafx-sdk-11.0.2\lib*
--add-modules=javafx.controls,javafx.media,javafx.fxml
```
 
The server is using 8080 port on default. Before running the project, you should check that specified port is opened and not used by another app at the moment.
