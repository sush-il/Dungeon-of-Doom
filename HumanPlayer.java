// Runs the game with a human player and contains code needed to read inputs.
import java.util.Scanner;

public class HumanPlayer extends player{
    
    //get input from the player
    public String getCommand(){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        return input;
    }

    /**
     * function checks if the given direction is valid
     * @param : direction --> takes the direction entered by the user
     * @return : boolean value (true,false)
    */
    public boolean ValidMoveCommand(String direction){
        String[] directions = {"N","E","S","W"};
        boolean validDirection = false;
        //if entered direction is in directions then return true
        for(String item: directions){
            if(item.equals(direction)){
                validDirection = true;
            }
        }
        return validDirection;  
    }
}