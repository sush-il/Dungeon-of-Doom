//Contains the main logic part of the game, as it processes.
//import java.util.Arrays;
public class GameLogic {
	/* Reference to the map,player being used */
	private Map map;
	private HumanPlayer player;
	//location of the palyer
	private int[] playerLocation;
	private char replacer = 'G';

	//Defaullt constructor 
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
					printMap(smallMap(playerLocation));
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
				//currentMap[playerLocation[0]][playerLocation[1]] = 'P';
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
		if(isNotAWall(locationValue(row, col))){
			playerLocation[0] = row;
			playerLocation[1] = col;
		}
		else{
			System.out.println("Move Failed. Wall Ahead.");
		}
    }

	public void printMap(char[][] mapToPrint){
		for(char[] row:mapToPrint){
			System.out.println(row);
		}
	}

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
		//The board moves accordingly
		miniMap[2][2] = 'P';
		//return the 5x5 map with player in the middle;
		return miniMap;
	}

    
	//Checks if the game is running
    public boolean gameRunning(boolean value) {
        return value;
    }

	public void quit(){
		System.out.println("Qutitting Game");
		//System.exit(0);
	}
	
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.runGame();
	}
}