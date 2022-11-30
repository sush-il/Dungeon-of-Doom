// Runs the game with a human player and contains code needed to read inputs.
import java.util.Random;
import java.util.Scanner;

public class HumanPlayer {
    int[] currentLocation;
    int goldOwned = 0;
    
    public String getCommand(){
        //get user input and turn to upper case
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        return input;
    }

    //Starting location for the human player
    public int[] generateStartLocation(char[][] map){
        currentLocation = new int[2];
        //generates a random location within boundries
        Random rand = new Random();
        currentLocation[0] = rand.nextInt(map.length);
        currentLocation[1] = rand.nextInt(map[0].length);
        return currentLocation;
    }

    /*function checks if the given direction is valid
     * @param: direction takes the direction entered by the user
    */
    public boolean ValidMoveCommand(String direction){
        String[] directions = {"N","E","S","W"};
        boolean validDirection = false;
        for(String item: directions){
            if(item.equals(direction)){
                validDirection = true;
            }
        }
        return validDirection;  
    }

    //returns the current postion of the human player
    public int[] getCurrentPosition(){
        return currentLocation;
    }

    public int goldOwned(){
        return goldOwned;
    }

}