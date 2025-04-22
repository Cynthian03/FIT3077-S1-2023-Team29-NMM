package Controller;
import Model.MoveEnum;
import Model.Pet;
import Model.Player;
import Model.moves.Move;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameExporter {
    private final String FILE_NAME = "scr/Save_Files/game_state.txt";
    public GameExporter(Boolean to_load) {
        if (!to_load){
            try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
                // If the file already exist, wipe existing saved information
                Files.write(Path.of(FILE_NAME), new byte[0]);
            } catch (IOException e) {
                System.out.println("An error occurred while appending to the file: " + e.getMessage());
            }
        }
    }
    public void exportState(ArrayList<Player> players) {
        Player cat = players.get(1);
        Player dog = players.get(0);

        // Reverse the order of the moves made
        cat.translateAllMoves();
        dog.translateAllMoves();

        // Retrieve all the moves in order
        Stack<Move> catMoves = cat.getAllMoves();
        Stack<Move> dogMoves = dog.getAllMoves();

        //set current player
        Player currentPlayer = cat;
        while ((!catMoves.empty() || !dogMoves.empty())){
            if (currentPlayer.getAllMoves().empty()){
                currentPlayer = catMoves.empty() ? dog : cat;
            }

            Move previousMove = currentPlayer.getAllMoves().pop();
            try {
                String content = " " + previousMove.getUpdateBoard().getStringRepresentation() + "Move: " + previousMove.getMoveName().name() + ", Player: " + currentPlayer.getPet().name() + "\n";
                Files.write(Path.of(FILE_NAME), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("An error occurred while appending to the file: " + e.getMessage());
            }

            if (!currentPlayer.getAllMoves().empty() && currentPlayer.getAllMoves().peek().getMoveName() != MoveEnum.Mill) {
                currentPlayer = currentPlayer == cat ? dog : cat;
            }
        }

    }

    public int[][] loadGameState(){
        // pass the path to the file as a parameter
        int numLines = 9;
        List<String> lastLines = readLastLines(FILE_NAME, numLines);
        List<String> boardString = new ArrayList<>();
        for (int i = 0; i < lastLines.size()-1; i++) {
            boardString.add(lastLines.get(i));
        }
       return getLoadBoard(boardString);
    }

    public Pet getLastPlayer(){
        int numLines = 2;
        Pet pet = null;
        List<String> lastLines = readLastLines(FILE_NAME, numLines);
        for (String line: lastLines) {
            String catPattern = "\\bCAT\\b";
            String dogPattern = "\\bDOG\\b";

            // Create a Pattern object from the pattern string
            Pattern catRegex = Pattern.compile(catPattern);
            Pattern dogRegex = Pattern.compile(dogPattern);

            Matcher catMatcher = catRegex.matcher(line);
            Matcher dogMatcher = dogRegex.matcher(line);
            // Find and extract matches
            if (catMatcher.find()) {
                pet = Pet.CAT;
            } else if (dogMatcher.find()){
                pet = Pet.DOG;
            }
        }
        return pet;
    }

    public int[][] getLoadBoard(List<String> input){
        int[][] result = new int[input.size()][];

        for (int i = 0; i < input.size(); i++) {
            String[] elements = input.get(i).split(","); // Split row string into elements
            int[] rowArray = new int[elements.length];

            for (int j = 0; j < elements.length; j++) {
                elements[j] = elements[j].replace("[", "");
                elements[j] = elements[j].replace("]", "");
                rowArray[j] = Integer.parseInt(elements[j].trim()); // Convert element string to integer
            }

            result[i] = rowArray; // Add row to the result
        }

        return result;
    }


    private ArrayList<String> translatePlayerMove() {
        ArrayList<String> allPlayerMoves = new ArrayList<>();

        String pattern = "\\bMove: \\b";

        // Create a Pattern object from the pattern string
        Pattern regex = Pattern.compile(pattern);

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Create a Matcher object to perform the matching
                Matcher matcher = regex.matcher(line);
                // Find and extract matches
                while (matcher.find()) {
                    allPlayerMoves.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPlayerMoves;
    }

    public ArrayList<String> getPlayerStringMove(Pet pet){
        ArrayList<String> l = new ArrayList<>();
        String pattern = pet == Pet.CAT? "\\bCAT\\b" : "\\bDOG\\b";

        // Create a Pattern object from the pattern string
        Pattern regex = Pattern.compile(pattern);
        for (String line: translatePlayerMove()){
            // Create a Matcher object to perform the matching
            Matcher matcher = regex.matcher(line);
            // Find and extract matches
            while (matcher.find()) {
                l.add(line);
            }
        }
        return l;
    }

    public static List<String> readLastLines(String filePath, int numLines) {
        List<String> lastLines = new ArrayList<>();

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")) {
            long fileLength = randomAccessFile.length(); // Get the length of the file
            long position = fileLength - 1; // Start from the end of the file
            int linesRead = 0;

            while (position >= 0 && linesRead < numLines) {
                randomAccessFile.seek(position); // Set the file pointer to the current position
                char currentChar = (char) randomAccessFile.read(); // Read the current character

                if (currentChar == '\n') {
                    linesRead++; // Increment the line count when a newline character is encountered

                    if (linesRead >= numLines) {
                        break; // Stop reading once the desired number of lines is reached
                    }
                }

                position--; // Move the file pointer backward
            }

            // Read the last lines
            String line;
            while ((line = randomAccessFile.readLine()) != null) {
                lastLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lastLines;
    }

}
