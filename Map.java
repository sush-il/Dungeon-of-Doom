// Reads and contains in memory the map of the game.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; // Class to handle IO errors

public class Map {
	/* Representation of the map */
	private char[][] map;
	/* Map name */
	private String mapName;
	/* Gold required for the player to win */
	private int goldRequired;
	
	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}
	
	/**
	 * Constructor that accepts a map to read in from.
	 *
	 * @param : The filename of the map file.
	 */
	public Map(String fileName) {
		readMap(fileName);
	}
    /**
     * Reads the map from file.
     *
     * @param : Name of the map's file.
     */
    public void readMap(String fileName) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			// Variables store Map Name and the number of Gold to win
			mapName = reader.readLine().replace("name ","");
			goldRequired = Integer.parseInt(reader.readLine().replace("win ",""));
			
			//count the number of lines in the file and -2 to get the map count
			int linesCount = numberOfLines(fileName) -2;
			
			//storing the map from the file in the map array
			map = new char[linesCount][];
			for(int i = 0; i<linesCount;i++){
				map[i] = reader.readLine().toCharArray();
			}

			reader.close();

		} 
		catch (IOException e){
			System.out.println("File not Found");
		}
    }

	//get the number of lines in the map
	public int numberOfLines(String fileName){
		int lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	//displays the map to the screen
	public void displayMap(int[] playerLocation){
		for(char[] row:map){
			System.out.println(row);
		}
	}

	//method returns the current map
	public char[][] getMap(){
		return map;
	}

	//Total gold required to win the game
	public int goldToWin(){
		return goldRequired;
	}

	//returns the name of the map
	public String mapName(){
		return mapName;
	}
	//Returns the sign in the given location
}
