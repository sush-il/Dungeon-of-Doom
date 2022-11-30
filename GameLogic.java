//Contains the main logic part of the game, as it processes.
//import java.util.Arrays;
public class GameLogic {
	
	/* Reference to the map,player being used */
	private Map map;
	private HumanPlayer player;
	//location of the palyer
	private int[] playerLocation;
	private char replacer = 'G';
	/**
	 * Default constructor
	 */
	public GameLogic() {
		player = new HumanPlayer();
		}

	public void runGame(){	
		//generates a map from userinput
		generateMap();
		//spawns the player at a random location
		spawnPlayer();
		boolean gameRunning = gameRunning(true);
		
		while(gameRunning){		
			//Ask user for command
			System.out.println("Enter command: ");
			String command = player.getCommand().toUpperCase();

			switch(command){
				case "HELLO":
					System.out.println("Gold to Win: "+map.goldToWin());
					break;
				case "LOOK":
					map.smallMap(playerLocation);;
					break;
				case "GOLD":
					;

				case "PICKUP":
					;

				case "QUIT":
					;
					
			}

			//Actions when move function is called
			if(command.contains("MOVE ")){
				String direction = command.split(" ")[1];
				if(player.ValidMoveCommand(direction)){
					move(direction,replacer);
				}
				
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

	//returns the value at location in map; i.e. # , G etc..
	public char locationValue(int row,int col){
		char[][] currentMap = map.getMap();
		return currentMap[row][col];
	}

	// Check if the chosen location is valid; not a wall
	public boolean isNotAWall(char locationValue){
		if(locationValue != '#'){
			return true;
		}
		return false;
	}

	//Spawns the player at a random location
	public void spawnPlayer(){
		char[][] currentMap = map.getMap();
		playerLocation = player.generateStartLocation(currentMap);
		int row = playerLocation[0];
		int col = playerLocation[1];
		boolean playerNotSpawned = true;
		//ensure the location is valid; i.e. not a wall of gold
		while(playerNotSpawned){
			if(isNotAWall(locationValue(row, col)) && locationValue(row,col) != 'G'){
				currentMap[playerLocation[0]][playerLocation[1]] = 'P';
				playerNotSpawned = false;
			}
			else{
				playerLocation = player.generateStartLocation(currentMap);
			}
		}
	}

	//Control player movements
	public void move(String direction,char replacer){
		char[][] currentMap = map.getMap();

		int row = playerLocation[0];
		int col = playerLocation[1];

		switch(direction){
			case "N":
				row -= 1;
				break;
			case "E": 
				col += 1;
				break;

			case "S":
				row += 1;
				break;

			case "W":
				col -= 1;
				break;
		}

		char nxtValue = locationValue(row, col);
		System.out.println(nxtValue);
		//if valid moveg the player to the desired location
		if(isNotAWall(locationValue(row, col))){
			//temp = locationValue(row,col);
			currentMap[playerLocation[0]][playerLocation[1]] = '.';
			currentMap[row][col] = 'P';
			playerLocation[0] = row;
			playerLocation[1] = col;
		};
		System.out.println(replacer);
		//replacer = nxtValue;	
    }

    /**
	 * Checks if the game is running
	 *
     * @return if the game is running.
     */
    public boolean gameRunning(boolean value) {
        return value;
    }

	public void quit(){
		System.out.println("Qutitting Game");
		//System.exit(0);
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