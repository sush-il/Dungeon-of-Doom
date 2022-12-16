import java.util.Arrays;
import java.io.File;
import java.lang.Math;

/** 
 * The class deals with the main logic of the game. 
*/
public class GameLogic {
	//declaring required variables
	private Map map;
	private HumanPlayer player;
	private BotPlayer bot;

	private char[][] mapInPlay;
	private int[] playerLocation;
	private int[] botLocation;

	int goldCollected;
	int botGold;
	boolean gameRunning;
	
	//Default constructor
	public GameLogic() {
		player = new HumanPlayer();
		bot = new BotPlayer();
	}

	/** Begins the game */
	public void runGame(){	
		generateMap();
		spawnPlayer();
		spawnBot();

		gameRunning = true;
		
		while(gameRunning){	
			checkCases();
			pickGold(botLocation);
			checkEnoughGold();

			//If Bot in 5x5 grid of player it follows player
			if(bot.inRange(botLocation,playerLocation)){
				followPlayer();
			}
			else{
				spawnBot();
			}

			caughtByBot();
		}

	}

	/** Checks all cases for player command */
	public void checkCases(){
		System.out.println("\nEnter command: ");
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
				break;

			case "PICKUP":
				pickGold(playerLocation);
				break;

			case "QUIT":
				exit();
				gameRunning = false;
				break;	
			
			default:
				if(! command.contains("MOVE ")){
					System.out.println("Invalid Command");
				}
		}
		
		try{
			
			if(command.contains("MOVE ")){
				String direction = command.split(" ")[1];
				if(player.ValidMoveCommand(direction)){
					move(direction);
				}
				else{
					System.out.println("Move is invalid");
				}	
			}
		}
		//Catch error when user types in "MOVE " without direction
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Invalid Move! Direction not specified.");
		}

	}

	/** Function generates a map based on users choice */
	public void generateMap(){
		System.out.println("Enter the name of the map (without extension) or leave empty for default: ");
		String name = player.getCommand();
		String fileName = "";
		
		if(! name.isEmpty()){
			//Generate filename
			fileName = "./maps/"+name+".txt";
			//Check if a file with the given name exists in the directory
			boolean fileExists = new File("./", fileName).exists();
			
			if(fileExists){
				map = new Map(fileName);
			}
			else{
				System.out.println("File not valid \nDefault map loaded");
				map = new Map();
			}
		}
		
		else{
			map = new Map();
			System.out.println("Default Map Loaded");
		}
		System.out.println("\nMap Name: " + map.mapName());
		System.out.println("Gold Required to Win: "+ map.goldToWin());
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
			//ensure the location is valid; i.e. not a wall or gold
			if(isNotAWall(locationValue(row, col)) && locationValue(row,col) != 'G'){
				playerNotSpawned = false;
			}
			else{
				playerLocation = player.generateStartLocation(currentMap);
			}
		}
	}

	/** Spawns the bot at a random location */
	public void spawnBot(){
		char[][] currentMap = map.getMap();
		botLocation = player.generateStartLocation(currentMap);
		
		boolean botNotSpawned = true;
		
		while(botNotSpawned){
			//ensure spawn location is valid; i.e. not a wall or gold or the player
			if(isNotAWall(locationValue(botLocation[0],botLocation[1])) && locationValue(botLocation[0],botLocation[1]) != 'G' && locationValue(botLocation[0],botLocation[1]) != 'P'){
				botNotSpawned = false;				
			}
			else{
				botLocation = player.generateStartLocation(currentMap);
			}
		}	
	}

	/** @return 5x5 map with bot written to the location  */
	public char[][] writeBot(){
		try{
			if(bot.inRange(botLocation,playerLocation)){
				int row = Math.abs(2 - (playerLocation[0] - botLocation[0]));
				int col = Math.abs(2 - (playerLocation[1] - botLocation[1]));
				mapInPlay = smallMap(playerLocation);
				mapInPlay[row][col] = 'B';
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
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
	 * @return boolean value: true,false
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
		int row = playerLocation[0];
		int col = playerLocation[1];
		// Increment / Decrement player location based on choice
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

	/** Method checks if user was caught by bot */
	public void caughtByBot(){
		if(Arrays.equals(playerLocation,botLocation)){
			System.out.println("Game is over. The bot has caught you");
			gameRunning = false;
		}
	}

	/** Follow the player */
	public void followPlayer(){
		//compare rows
		if(botLocation[1]>playerLocation[1] && isNotAWall(locationValue(botLocation[0], botLocation[1]-1))){
			botLocation[1] -= 1;
		}
		else if(botLocation[1]<playerLocation[1] && isNotAWall(locationValue(botLocation[0], botLocation[1]+1))){
			botLocation[1] += 1;
		}
		//compare Columns
		else if(botLocation[0]>playerLocation[0] && isNotAWall(locationValue(botLocation[0]-1, botLocation[1]))){
			botLocation[0] -= 1;
		}
		else if(botLocation[0]<playerLocation[0] && isNotAWall(locationValue(botLocation[0]+1, botLocation[1]))){
			botLocation[0] += 1;
		}
		//spawn at random location if wall and can't move
		else{
			spawnBot();
		}
	}

	/**
	 * pickup the gold when on a gold tile
	 * @param : playerCord Co-ordinates of teh player (bot/humanPlayer) 
	 */
	public void pickGold(int[] playerCord){
		if(locationValue(playerCord[0],playerCord[1]) == 'G'){
			incrGold(playerCord);
			//replace with empty tile when gold picked up
			map.getMap()[playerCord[0]][playerCord[1]] = '.';
		}
		else{
			if(Arrays.equals(playerLocation, playerCord)){
				System.out.println("No Gold at this location.");
			}	
		}
	}

	/** @param : playerCord player co-ordintes(user/bot) */
	public void incrGold(int[] playerCord){
		if(Arrays.equals(playerCord,playerLocation)){
			System.out.println("SUCCESS");
			goldCollected += 1;
			countGold();
		}
		else{
			System.out.println("Bot collected Gold");
			botGold += 1;
		}
	}

	/** @return total gold collected by the humanPlayer */
	public int countGold(){
		System.out.println("GOLD COLLECTED: "+ goldCollected);
		return goldCollected;
	}

	/** Check how many gold left in the map */
	public int goldLeft(){
		int goldLeft = 0;
		for(int i = 0; i< map.getMap().length; i++){
			for(int j = 0; j<map.getMap()[i].length; j++){
				if(map.getMap()[i][j] == 'G'){
					goldLeft += 1;
				}
			}
		}
		
		return goldLeft;
	}

	/** Checks if there is enough gold in the map for the humanPlayer to win */
	public void checkEnoughGold(){
		if(goldLeft()<map.goldToWin() && (goldCollected+goldLeft()) < map.goldToWin()){
			gameRunning = false;
			System.out.println("Not enough gold left in the map for WIN. Game Over.");
		}
	}
	
	/**
	 * creates a 5x5 map visible to the player
	 * @param : userLocation --> The co-ordinates of the player
	 * @return : 5x5 2d array, map currently in play
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