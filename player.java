import java.util.Random;

/** Contains the methods and attribures inherited by both players */
public class player {
    int[] currentLocation;
    int goldOwned = 0;
    /**
    * Generates a random spawn location for the player
    * @param : map --> The map currently being played
    * @return : integer array with co-ordinates
    */
    public int[] generateStartLocation(char[][] map){
        Random rand = new Random();
        currentLocation = new int[2];
        //ensuring location within map boundries
        currentLocation[0] = rand.nextInt(map.length);
        currentLocation[1] = rand.nextInt(map[0].length);
        return currentLocation;
    }
    
    /** @return the current postion of the player */
    public int[] getCurrentPosition(){
        return currentLocation;
    }

    /** @return the gold owned by the player */
    public int goldOwned(){
        return goldOwned;
    }
}