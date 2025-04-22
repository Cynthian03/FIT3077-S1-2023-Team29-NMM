# FIT3077 project 9 Men's Morris

## Group 29 TYCL Team members
Terry Truong 32466870 \
Cynthian Thai 31477119\
Leon Ma 31449840 \
Yuhan Zhou 31468748 

## Nine Mens Morris Advanced Functionality
The additional functionality added to the Nine Men's Morris game include:<br />
1. A tutorial mode. Additionally, when playing a match, there should be an option for each player to toggle “hints” <br />
that show all the legal moves the player may make as their next move.
2. Players are allowed to undo their last move and the game client supports the undoing of moves until there are no <br />
more previous moves available. The game client also supports saving the state of the currently active game <br />
   (stored as a simple text file), and be able to fully reload any previously saved game(s) stored as a <br />
simple text file where each line in the text file represents the current state of the board and stores <br />
information about the previously made move.

## How to Run Nine Mens Morris Executable Application
* Download JavaFx JDK appropriate to machine architecture
* Run the executable file (.jar file) within the terminal through this command:
  * java --module-path **_%path of JavaFX SDK directory%_**\lib --add-modules javafx.controls,javafx.fxml -jar **_%path of JAR file%_**
    * Ensure the correct directory path is written in the command

## How to set up JavaFX and Run NineMensMorris Application on IntelliJ
* Download JavaFX (https://gluonhq.com/products/javafx/) [Windows x64 SDK] and put it in a folder where you know where it is
* Check JavaFx Plugins is enables: File > Settings> PlugIns > check JavaFx is enabled
* Need to add library to Project:
  * File > Project Structure > Libraries > + > Java > % PATH TO JavaFX.lib% (the actual path on your computer to the JavaFX.lib folder)
    * On left sidebar: Modules > Dependencies > tick JavaFx.lib to enable
  * Reboot IntelliJ IDE (close & reopen project)
* Need to edit run configurations:
  * Run > Edit Configurations >
    * if "No run configurations added":  + > Application > "give application name"
    * Modify Options > Add VM Options > paste this: --module-path **_%PATH TO JavaFx.lib%_** --add-modules javafx.base,javafx.controls,javafx.graphics,javafx.media
      * Change **_%PATH TO JavaFx.lib%_** to the path on your computer to the JavaFX.lib folder
* **Now you can Run NineMensMorris Application**

## Lucid Charts Link
Access Domain model, class Diagram and all sequence Diagrams here:
https://lucid.app/lucidchart/29fe7a0e-b009-461c-98a7-d61371b338c3/edit?viewport_loc=-482%2C-134%2C3231%2C1536%2C0_0&invitationId=inv_9f949830-71a1-4aa7-ad73-847dd12b6446

## Video Demo Link
Sprint 3: https://youtu.be/029FZ4jtkBQ
Sprint 4: https://youtu.be/6oflHtjCy0s