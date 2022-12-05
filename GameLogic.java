//Contains the main logic part of the game, as it processes.
import java.util.Arrays;
import java.lang.Math;

public class GameLogic {
	/* Reference to the map,player being used */
	private Map map;
	private HumanPlayer player;
	private char[][] mapInPlay;
	private Bot bot;
	
	//co-ordinates of the palyer
	private int[] playerLocation;
	private int[] botLocation;
	boolean gameRunning;
	//Total gold collected by the player
	int goldCollected;
	//Defaullt constructor 
	public GameLogic() {
		player = new HumanPlayer();
		bot = new Bot();
	}

	//Main method running game
	public void runGame(){	
		//generates a map from userinput
		generateMap();
		//spawns the player and the bot at a random location
		spawnPlayer();
		writeBot();
		 
		gameRunning = true;
		
		while(gameRunning){		
			//Ask user for command
			System.out.println("Enter command: ");
			String command = player.getCommand().toUpperCase();

			switch(command){
				case "HELLO":
					System.out.println("Gold to Win: "+map.goldToWin());
					break;
				case "LOOK":
					smallMap(playerLocation);
					printMap(writeBot());
					break;
				case "GOLD":
					countGold();
					break;

				case "PICKUP":
					pickGold();
					break;

				case "QUIT":
					exit();
					gameRunning = false;
					break;	
			}
			//Actions when move function is called
			if(command.contains("MOVE ")){
				String direction = command.split(" ")[1];
				if(player.ValidMoveCommand(direction)){
					move(direction);
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
			//System.out.println("Default Map Loaded");

			return fileName;
		}
		map = new Map();
		return "";
	}

	//Spawns the player at a random location
	public void spawnPlayer(){
		char[][] currentMap = map.getMap();
		playerLocation = player.generateStartLocation(currentMap);
		botLocation = player.generateStartLocation(currentMap);

		int row = playerLocation[0];
		int col = playerLocation[1];

		boolean playerNotSpawned = true;
		boolean botNotSpawned = true;
		
		//get new locations until a valid one is found
		while(playerNotSpawned){
			//ensure the location is valid; i.e. not a wall of gold
			if(isNotAWall(locationValue(row, col)) && locationValue(row,col) != 'G'){
				playerNotSpawned = false;
			}
			else{
				playerLocation = player.generateStartLocation(currentMap);
			}
		}
	}

	/* 
	* @param: row --> any given row index , 
	* @param: col --> any given column index
	* @return --> the value at given row,col in map; i.e, #,G etc...
	*/
	public char locationValue(int row,int col){
		char[][] currentMap = map.getMap();
		return currentMap[row][col];
	}

	/*
	 * Check if the chosen location is valid; not a wall
	 * @param locationValue --> the value at location; i.e, #, G, E, .
	 */
	public boolean isNotAWall(char locationValue){
		if(locationValue != '#'){
			return true;
		}
		return false;
	}

	/*
	Controls player movements
	@param: direction --> a direction is passed; ie. N,E,S,w
	*/
	public void move(String direction){

		int row = playerLocation[0];
		int col = playerLocation[1];
		//Increment / Decrement player location based on choice
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
		if(isNotAWall(locationValue(row, col))){
			playerLocation[0] = row;
			playerLocation[1] = col;
			System.out.println("MOVE SUCCESS");
		}
		else{
			System.out.println("MOVE FAIL");
		}
    }

	//pickup the gold when on a gold tile
	public void pickGold(){
		//total gold collected by the player
		if(locationValue(playerLocation[0],playerLocation[1]) == 'G'){
			goldCollected += 1;
			System.out.println("SUCCESS");
			countGold();
			//empty tile when gold picked up
			map.getMap()[playerLocation[0]][playerLocation[1]] = '.';
		}
	}

	// Returns the total gold collected by the humanPlayer
	public int countGold(){
		System.out.println("GOLD COLLECTED: "+ goldCollected);
		return goldCollected;
	}

	//Exit the map
	public void exit(){
		if(goldCollected == map.goldToWin() && locationValue(playerLocation[0],playerLocation[1]) == 'E'){
			System.out.println("WIN!! GAME COMPLETE");
		}
		else{
			System.out.println("FAIL!");
		}
	}

	/*
	 5x5 map visible to the player
	 @param: userLocation --> The co-ordinates of the player
	*/
	public char[][] smallMap(int[] userLocation){
		char miniMap[][] = new char[5][5];
		
		for(int i = 0; i< 5; i++){
			for(int j = 0; j< 5; j++){
				try{
					miniMap[i][j] = map.getMap()[userLocation[0] - 2 + i][userLocation[1] - 2 + j];
				}
				catch (ArrayIndexOutOfBoundsException e) {
					miniMap[i][j] = '#';
				}
			}		
		}
		//Player position is fixed in the middle
		miniMap[2][2] = 'P';
		mapInPlay = miniMap;
		return mapInPlay;
	}

	public char[][] writeBot(){
		//char[][] currentMap = mapInPlay;
		System.out.println("Bot is in location:" + Arrays.toString(botLocation));
		System.out.println("Player in in location: "+ Arrays.toString(playerLocation));

		try{

			if(Math.abs(botLocation[0] - playerLocation[0]) <= 2 && Math.abs(botLocation[1] - playerLocation[1]) <= 2 ){
				int row = Math.abs(2 - (playerLocation[0] - botLocation[0]));
				int col = Math.abs(2 - (playerLocation[1] - botLocation[1]));

				if(row == 2 && col == 2){
					System.out.println("Game Over");
					gameRunning = false;
				}

				System.out.println("Small Row:" + row);
				System.out.println("Small Col: "+ col);
				
				if(isNotAWall(locationValue(row, col)) && map.getMap()[botLocation[0]][botLocation[1]] != 'G'){
					mapInPlay[row][col] = 'B';
				}
				
				return mapInPlay;
			}
			
			else{
				System.out.println("wait what");
				botLocation = player.generateStartLocation(map.getMap());
				return mapInPlay;
			}
			
		}catch(ArrayIndexOutOfBoundsException e){
			return mapInPlay;
		}

    }

    //print out the given map
	public void printMap(char[][] mapToPrint){
		for(char[] row:mapToPrint){
			System.out.println(row);
		}
	}
	
	//Checks if the game is running
    public boolean gameRunning(boolean value) {
        return value;
    }

	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.runGame();
	}
}