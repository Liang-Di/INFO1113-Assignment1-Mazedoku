import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Maze Game
 *
 * INFO1113 Assignment 1
 * 2018 Semester 2
 *
 * The Maze Game.
 * In this assignment you will be designing a maze game.
 * You will have a maze board and a player moving around the board.
 * The player can step left, right, up or down.
 * However, you need to complete the maze within a given number of steps.
 *
 * As in any maze, there are walls that you cannot move through. If you try to
 * move through a wall, you lose a life. You have a limited number of lives.
 * There is also gold on the board that you can collect if you move ontop of it.
 *
 * Please implement the methods provided, as some of the marks are allocated to
 * testing these methods directly.
 *
 * @author YOU :)
 * @date 23 August 2018
 *
 */
public class MazeGame {
    /* You can put variables that you need throughout the class up here.
     * You MUST INITIALISE ALL of these class variables in your initialiseGame
     * method.
     */

    // A sample variable to show you can put variables here.
    // You would initialise it in initialiseGame method.
    // e.g. Have the following line in the initialiseGame method.
    // sampleVariable = 1;
    static int lives;
    static int steps;
    static int gold;
    static int rows;
    static ArrayList<String[]> maze;
    static int destination_x;
    static int destination_y;
    static String save_file;


    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", load the default
     * game configuration.
     *
     * NOTE: Please also initialise all of your class variables.
     *
     * @args configFileName The name of the game configuration file to read from.
     * @throws IOException If there was an error reading in the game.
     *         For example, if the input file could not be found.
     */
    public static void initialiseGame(String configFileName) throws IOException {
        save_file = "";
        File f = new File(configFileName);
        try {
          Scanner scan = new Scanner(f);
          String[] title = scan.nextLine().split(" ");
          lives = Integer.parseInt(title[0]);
          steps = Integer.parseInt(title[1]);
          gold = Integer.parseInt(title[2]);
          rows = Integer.parseInt(title[3]);
          maze = new ArrayList<>();
          while (scan.hasNextLine()) {
            maze.add(scan.nextLine().split(""));
          }
          for (int i = 0; i < maze.size() ; i++) {
            for (int j = 0; j < maze.get(i).length; j++) {
              if (maze.get(i)[j].equals("@")) {
                destination_y = i;
                destination_x = j;
              }
            }
          }
          if (maze.size() != rows) {
            throw new IOException();
          }
        }
        catch (FileNotFoundException e) {
          throw new IOException();
        }


    }

    /**
     * Save the current board to the given file name.
     * Note: save it in the same format as you read it in.
     * That is:
     *
     * <number of lives> <number of steps> <amount of gold> <number of rows on the board>
     * <BOARD>
     *
     * @args toFileName The name of the file to save the game configuration to.
     * @throws IOException If there was an error writing the game to the file.
     */
    public static void saveGame(String toFileName) throws IOException {
      save_file = toFileName;
      File f = new File(toFileName);
      try {
        PrintWriter writer = new PrintWriter(f);
        writer.println(lives + " " + steps + " " + gold + " " +rows);
        for (String[] x : maze) {
          for (String s : x) {
            writer.print(s);
          }
          writer.println();
        }
        writer.close();
        System.out.printf("Successfully saved the current game configuration to '%s'.\n", toFileName);
        }
      catch (FileNotFoundException e) {
        throw new IOException();
      }
    }

    /**
     * Gets the current x position of the player.
     *
     * @return The players current x position.
     */
    public static int getCurrentXPosition() {
      int y = getCurrentYPosition();
      for (int i = 0; i < maze.get(y).length ; i++) {
        if (maze.get(y)[i].equals("&")) {
          return i;
        }
      }
      //System.out.println("bugs in x func");
      return -1;
    }

    /**
     * Gets the current y position of the player.
     *
     * @return The players current y position.
     */
    public static int getCurrentYPosition() {
        for (int i = 0 ; i < maze.size() ; i++) {
          ArrayList<String> row = new ArrayList<>(Arrays.asList(maze.get(i)));
          if (row.contains("&")) {
            return i;
          }
        }
        //System.out.println("bugs in y func");
        return -1;
    }

    /**
     * Gets the number of lives the player currently has.
     *
     * @return The number of lives the player currently has.
     */
    public static int numberOfLives() {
        return lives;
    }

    /**
     * Gets the number of remaining steps that the player can use.
     *
     * @return The number of steps remaining in the game.
     */
    public static int numberOfStepsRemaining() {
        return steps;
    }

    /**
     * Gets the amount of gold that the player has collected so far.
     *
     * @return The amount of gold the player has collected so far.
     */
    public static int amountOfGold() {
        return gold;
    }


    /**
     * Checks to see if the player has completed the maze.
     * The player has completed the maze if they have reached the destination.
     *
     * @return True if the player has completed the maze.
     */
    public static boolean isMazeCompleted() {
        if (getCurrentYPosition() == destination_y && getCurrentXPosition() == destination_x) {
          return true;
        }
        return false;
    }

    /**
     * Checks to see if it is the end of the game.
     * It is the end of the game if one of the following conditions is true:
     *  - There are no remaining steps.
     *  - The player has no lives.
     *  - The player has completed the maze.
     *
     * @return True if any one of the conditions that end the game is true.
     */
    public static boolean isGameEnd() {
        if (lives <= 0 || steps <= 0) {
          return true;
        }
        return false;
    }


	//This function check whether it's a wall
	public static boolean isWall(int x, int y) {
        if (maze.get(y)[x].equals("#")) {
          return true;
        }
		return false;
	}

    /**
     * Checks if the coordinates (x, y) are valid.
     * That is, if they are on the board.
     *
     * @args x The x coordinate.
     * @args y The y coordinate.
     * @return True if the given coordinates are valid (on the board),
     *         otherwise, false (the coordinates are out or range).
     */
    public static boolean isValidCoordinates(int x, int y) {
        if (y >= maze.size() || y < 0) {
          return false;
        }
        if (x >= maze.get(0).length || x < 0) {
          return false;
        }
        return true;
    }

    /**
     * Checks if a move to the given coordinates is valid.
     * A move is invalid if:
     *  - It is move to a coordinate off the board.
     *  - There is a wall at that coordinate.
     *  - The game is ended.
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     * @return True if the move is valid, otherwise false.
     */
    public static boolean canMoveTo(int x, int y) {
        if (!isGameEnd() && isValidCoordinates(x, y) && !isWall(x, y)) {
          return true;
        }
        return false;
    }

    /**
     * Move the player to the given coordinates on the board.
     * After a successful move, it prints "Moved to (x, y)."
     * where (x, y) were the coordinates given.
     *
     * If there was gold at the position the player moved to,
     * the gold should be collected and the message "Plus n gold."
     * should also be printed, where n is the amount of gold collected.
     *
     * If it is an invalid move, a life is lost.
     * The method prints: "Invalid move. One life lost."
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     */
    public static void moveTo(int x, int y) {
        if ( canMoveTo(x, y)) {
          System.out.printf("Moved to (%d, %d).\n", x, y);
          maze.get(getCurrentYPosition())[getCurrentXPosition()] = ".";
          int raise_gold = 0;
          try {
            raise_gold += Integer.parseInt(maze.get(y)[x]);
            System.out.printf("Plus %s gold.\n", raise_gold);
            gold += raise_gold;
          }
          catch(NumberFormatException nef) {
            raise_gold += 0;
          }
          maze.get(y)[x] = "&";
        }
        else {
            System.out.println("Invalid move. One life lost.");
            lives -= 1;
        }
        steps -= 1;
    }

    /**
     * Prints out the help message.
     */
    public static void printHelp() {
        System.out.println("Usage: You can type one of the following commands.");
        System.out.println("help         Print this help message.");
        System.out.println("board        Print the current board.");
        System.out.println("status       Print the current status.");
        System.out.println("left         Move the player 1 square to the left.");
        System.out.println("right        Move the player 1 square to the right.");
        System.out.println("up           Move the player 1 square up.");
        System.out.println("down         Move the player 1 square down.");
        System.out.println("save <file>  Save the current game configuration to the given file.");
    }

    /**
     * Prints out the status message.
     */
    public static void printStatus() {
      System.out.println("Number of live(s): " + lives);
      System.out.println("Number of step(s) remaining: " + steps);
      System.out.println("Amount of gold: " + gold);
    }

    /**
     * Prints out the board.
     */
    public static void printBoard() {
      for (String[] x : maze) {
        for (String s : x) {
          System.out.print(s);
        }
        System.out.println();
      }
    }

    /**
     * Performs the given action by calling the appropriate helper methods.
     * [For example, calling the printHelp() method if the action is "help".]
     *
     * The valid actions are "help", "board", "status", "left", "right",
     * "up", "down", and "save".
     * [Note: The actions are case insensitive.]
     * If it is not a valid action, an IllegalArgumentException should be thrown.
     *
     * @args action The action we are performing.
     * @throws IllegalArgumentException If the action given isn't one of the
     *         allowed actions.
     */
    public static void performAction(String action) throws IllegalArgumentException {

      String[] command = action.split(" ");
      if (command.length == 1) {
        if (command[0].equalsIgnoreCase("help")) {
          printHelp();
        }
        else if (command[0].equalsIgnoreCase("board")) {
          printBoard();
        }
        else if (command[0].equalsIgnoreCase("status")) {
          printStatus();
        }
        else if (command[0].equalsIgnoreCase("left")) {
          moveTo(getCurrentXPosition() - 1, getCurrentYPosition());
        }
        else if (command[0].equalsIgnoreCase("right")) {
          moveTo(getCurrentXPosition() + 1, getCurrentYPosition());
        }
        else if (command[0].equalsIgnoreCase("up")) {
          moveTo(getCurrentXPosition(), getCurrentYPosition() - 1);
        }
        else if (command[0].equalsIgnoreCase("down")) {
          moveTo(getCurrentXPosition(), getCurrentYPosition() + 1);
        }
        else if (command[0].equalsIgnoreCase("down")) {
          moveTo(getCurrentXPosition(), getCurrentYPosition() + 1);
        }
        else if (command[0].equals("")) {
          //Do nothing.
        }
        else {
          throw new IllegalArgumentException();
        }
      }
      else if (command.length == 2) {
        if (command[0].equalsIgnoreCase("save")) {
            try {
              saveGame(command[1]);
            }
            catch (IOException e) {
              System.out.printf("Error: Could not save the current game configuration to '%s'.\n", command[1]);
            }
        }
        else {
          throw new IllegalArgumentException();
        }
      }
      else {
        throw new IllegalArgumentException();
      }
    }


    public static void gameEndsType() {
      if (isMazeCompleted()) {
        // Complete the maze.
        System.out.println("Congratulations! You completed the maze!");
        System.out.println("Your final status is:");
        printStatus();
      }
      else { // Do not complete the maze.
        if (isGameEnd()) {  // End because no lives or steps.
          if (lives <= 0 && steps <= 0) {
            System.out.println("Oh no! You have no lives and no steps left.");
            System.out.println("Better luck next time!");
          }
          else if (lives <= 0) {
            System.out.println("Oh no! You have no lives left.");
            System.out.println("Better luck next time!");
          }
          else if (steps <= 0) {
            System.out.println("Oh no! You have no steps left.");
            System.out.println("Better luck next time!");
          }
        }
        else {  // End because EOF.
          if ((destination_x != getCurrentXPosition() || destination_y != getCurrentYPosition())) {
            System.out.println("You did not complete the game.");
          }
        }

      }
    }

    /**
     * The main method of your program.
     *
     * @args args[0] The game configuration file from which to initialise the
     *       maze game. If it is DEFAULT, load the default configuration.
     */
    public static void main(String[] args) {
        // Run your program (reading in from args etc) from here.
        if (args.length == 1) {
          try {
            Scanner scan = new Scanner(System.in);
            initialiseGame(args[0]);
            while (!isMazeCompleted() &&!isGameEnd() && scan.hasNextLine()) {

              String input = scan.nextLine();
                try {
                  performAction(input);
                }
                catch (IllegalArgumentException e) {
                  System.out.printf("Error: Could not find command '%s'.\n", input);
                  System.out.println("To find the list of valid commands, please type 'help'.");
                }
            }
            gameEndsType();
          }
          catch (IOException e) {
            System.out.printf("Error: Could not load the game configuration from '%s'.\n", args[0]);
          }
        }
        else if (args.length < 1){
          System.out.printf("Error: Too few arguments given. Expected 1 argument, found %d.\n", args.length);
          System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
        }
        else {
          System.out.printf("Error: Too many arguments given. Expected 1 argument, found %d.\n", args.length);
          System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
        }
    }

}

