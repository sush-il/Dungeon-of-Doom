import java.util.Arrays;
import java.lang.Math;

public class GameLogic {
	//declaring required variables
	private Map map;
	private HumanPlayer player;
	private Bot bot;

	private char[][] mapInPlay;
	private int[] playerLocation;
	private int[] botLocation;

	int goldCollected;
	int botGold;
	boolean gameRunning;
	
	//Defaullt constructor 
	public GameLogic() {
		player = new HumanPlayer();
		bot = new Bot();
	}

	public void runGame(){	
		generateMap();
		spawnPlayer();
		spawnBot();

		gameRunning = true;
		
		
		while(gameRunning){	
			System.out.println("Enter command: ");
			String command = player.getCommand().toUpperCase();

			switch(command){
				case "HELLO":
					System.out.println("Gold Required to Win: "+map.goldToWin());
					break;
				case "LOOK":
					smallMap(playerLocation);
					printMap(writeBot());
					break;
				case "GOLD":
					countGold();
					goldLeft();
					break;

				case "PICKUP":
					pickGold(playerLocation);
					break;

				case "QUIT":
					exit();
					gameRunning = false;
					break;	
			}
			/** Actions when move function is called */
			if(command.contains("MOVE ")){
				String direction = command.split(" ")[1];
				if(player.ValidMoveCommand(direction)){
					move(direction);
				}
				
			}
			checkEnoughGold();
		}
	}

	/** Function generates a map based on users choice */
	public void generateMap(){
		System.out.println("Enter the name of the map or leave empty for default: ");
		String name = "goldMap"; //player.getCommand(); //
		String fileName = "";
		if(! name.isEmpty()){
			//Generate filename
			fileName = "Example_maps/"+name+".txt";
			map = new Map(fileName);
			
		}
		else{
			map = new Map();
			System.out.println("Default Map Loaded");
		}	
	}

	/** Spawns the player at a random location */
	public void spawnPlayer(){
		char[][] currentMap = map.getMap();
		playerLocation = player.generateStartLocation(currentMap);

		int row = playerLocation[0];
		int col = playerLocation[1];

		boolean playerNotSpawned = true;
		
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


	public void spawnBot(){
		char[][] currentMap = map.getMap();
		botLocation = player.generateStartLocation(currentMap);
		
		boolean botNotSpawned = true;
		
		//get new locations until a valid one is found
		while(botNotSpawned){
			//ensure the location is valid; i.e. not a wall or gold
			if(isNotAWall(locationValue(botLocation[0],botLocation[1]))){
				botNotSpawned = false;
				//writeBot();
				
			}
			else{
				botLocation = player.generateStartLocation(currentMap);
			}
		}
		
	}

	public char[][] writeBot(){
		char[][] currentMap = map.getMap();
		System.out.println("Bot is in location:" + Arrays.toString(botLocation));
		System.out.println("Player in in location: "+ Arrays.toString(playerLocation));

		if(currentMap[botLocation[0]][botLocation[1]] == 'G'){
			pickGold(botLocation);
		}

		try{
			//If distance between bot and player is <= 2 write the bot in the 5x5 map
			if(Math.abs(botLocation[0] - playerLocation[0]) <= 2 && Math.abs(botLocation[1] - playerLocation[1]) <= 2 ){
				int row = Math.abs(2 - (playerLocation[0] - botLocation[0]));
				int col = Math.abs(2 - (playerLocation[1] - botLocation[1]));
				mapInPlay = smallMap(playerLocation);
				mapInPlay[row][col] = 'B';
			}
			
			else{
				spawnBot();
			}	
		}catch(ArrayIndexOutOfBoundsException e){
		}
		return mapInPlay;
    }

	/**
	* @param row any given row index 
	* @param col any given column index
	* @return value at given row,col in map; i.e, #,G etc... 
	*/
	public char locationValue(int row,int col){
		char[][] currentMap = map.getMap();
		return currentMap[row][col];
	}

	/**
	 * Check if the chosen location is valid; not a wall
	 * @param locationValue the value at location; i.e, #, G, E, .
	 */
	public boolean isNotAWall(char locationValue){
		if(locationValue != '#'){
			return true;
		}
		return false;
	}

	/**
	Controls player movements
	@param direction N,E,S,w
	*/
	public void move(String direction){
		//checkEnoughGold();
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

		if(Arrays.equals(playerLocation,botLocation)){
			System.out.println("Game is over. The bot has caught you");
			gameRunning = false;
		}
    }

	/** pickup the gold when on a gold tile */
	public void pickGold(int[] playerCord){
		//total gold collected by the player
		if(locationValue(playerCord[0],playerCord[1]) == 'G'){
			incrGold(playerCord);
			//empty tile when gold picked up
			map.getMap()[playerCord[0]][playerCord[1]] = '.';
		}

		countGold();
	}

	public void incrGold(int[] playerCord){
		if(Arrays.equals(playerCord,playerLocation)){
			System.out.println("SUCCESS");
			goldCollected += 1;
		}
		else{
			System.out.println("Bot collected Gold");
			botGold += 1;
		}
	}

	// Returns the total gold collected by the humanPlayer
	public int countGold(){
		System.out.println("GOLD COLLECTED: "+ goldCollected);
		return goldCollected;
	}

	public int goldLeft(){
		int goldLeft = 0;
		for(int i = 0; i< map.getMap().length; i++){
			for(int j = 0; j<map.getMap()[i].length; j++){
				if(map.getMap()[i][j] == 'G'){
					goldLeft += 1;
				}
			}
		}
		//System.out.println("Gold left: "+goldLeft);
		return goldLeft;
	}

	public void checkEnoughGold(){
		System.out.println("Gold left: "+goldLeft());
		if(goldLeft()<map.goldToWin() && (goldCollected+goldLeft()) < map.goldToWin()){
			gameRunning = false;
			System.out.println("Not enough gold left. Game Over.");
		}
	}
	/**
	 * 5x5 map visible to the player
	 * @param : userLocation --> The co-ordinates of the player
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

    /** print out the given map */
	public void printMap(char[][] mapToPrint){
		for(char[] row:mapToPrint){
			System.out.println(row);
		}
	}

	/** Exits the game */
	public void exit(){
		if(goldCollected == map.goldToWin() && locationValue(playerLocation[0],playerLocation[1]) == 'E'){
			System.out.println("WIN!! GAME COMPLETE");
		}
		else{
			System.out.println("FAIL!");
		}
	}

	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.runGame();
	}
}