//Contains the main logic part of the game, as it processes.
import java.util.Arrays;
public class GameLogic {
	
	/* Reference to the map,player being used */
	private Map map;
	private HumanPlayer player;
	//location of the palyer
	private int[] playerLocation;
	/**
	 * Default constructor
	 */
	public GameLogic() {
		player = new HumanPlayer();
		}

	public void runGame(){	
		//generates a map from userinput
		generateMap();
		playerLocation = player.generateStartLocation(map.getMap());
		//System.out.println(Arrays.toString(playerLocation));
		//isValidLocation(locationValue(playerLocation[0],playerLocation[1]));
		spawnPlayer();
		boolean gameRunning = true;
		
		while(gameRunning){		
			System.out.println("Enter command: ");
			String command = player.getCommand().toUpperCase();

			switch(command){
				case "HELLO":
					System.out.println("Gold to Win: "+map.goldToWin());
				case "LOOK":
					map.displayMap();
				case "GOLD":
					;

				case "PICKUP":
					;
			}

			if(command.contains("MOVE ")){
				String direction = command.split(" ")[1];
				player.ValidMoveCommand(direction);
				move(direction,playerLocation);
			}
		}
	}

	//Function returns a map based on userInput
	public String generateMap(){
		System.out.println("Enter the name of the map or leave empty for default: ");
		//user inputs a map to play
		String name = "small_example_map"; //player.getCommand();
		String fileName = "";
		if(! name.isEmpty()){
			//Generate filename and return it
			fileName = "Example_maps/"+name+".txt";
			map = new Map(fileName);

			return fileName;
		}
		map = new Map();
		return "";
	}

	public char locationValue(int row,int col){
		char[][] currentMap = map.getMap();
		//returns the value at location in map; i.e. # . G etc..
		//return currentMap[playerLocation[0]][playerLocation[1]];
		return currentMap[row][col];
	}

	public boolean isValidLocation(char locationValue){
		/*char[][] currentMap = map.getMap();*/
		
		if(locationValue != '#' && locationValue != 'G'){
			return true;
		}
		else{
			System.out.println("Not a valid location");
			return false;
		}
	}

	public void spawnPlayer(){
		char[][] currentMap = map.getMap();
		int row = playerLocation[0];
		int col = playerLocation[1];
		if(isValidLocation(locationValue(row, col))){
			currentMap[playerLocation[0]][playerLocation[1]] = 'P';
		}
		
	}

	public void move(String direction,int[] currentPosition){
		char[][] currentMap = map.getMap();
        if(direction.equals("S")){
			int row = playerLocation[0]+1;
			int col = playerLocation[1];
			if(isValidLocation(locationValue(row, col))){
				currentMap[playerLocation[0]][col] = '.';
				currentMap[row][col] = 'P';
				playerLocation[0] = row;
			};
        }
    }

    /**
	 * Checks if the game is running
	 *
     * @return if the game is running.
     */
    public boolean gameRunning() {
        return false;
    }

    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    public String hello() {
        return null;
    }
	
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.runGame();
}
}