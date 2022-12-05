import java.util.Random;

public class player {
    int[] currentLocation;
    int goldOwned = 0;
    /*
    * Generates a random spawn location for the player
    * @param: map --> The map currently being played
    */
    public int[] generateStartLocation(char[][] map){
        //generates a location within map boundries
        Random rand = new Random();
        currentLocation = new int[2];

        currentLocation[0] = rand.nextInt(map.length);
        currentLocation[1] = rand.nextInt(map[0].length);
        return currentLocation;
    }
    
    //returns the current postion of the human player
    public int[] getCurrentPosition(){
        return currentLocation;
    }

    //Returns the total gold the player owns
    public int goldOwned(){
        return goldOwned;
    }
}
