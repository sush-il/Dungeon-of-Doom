/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
import java.util.Scanner;
public class HumanPlayer {

    public void currentPosition(){

    }

    public void move(String direction,int currentPosition){

    }

    public String getCommand(){
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        scan.close();
        return input;
    }

}