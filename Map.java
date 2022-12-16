import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; // Class to handle IO errors

// Reads and contains in memory the map of the game.
public class Map {
	private char[][] map;
	private String mapName;
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
	 * @param : fileName The filename of the map file.
	 */
	public Map(String fileName) {
		readMap(fileName);
	}
    
	/**
     * Reads the map from file.
     * @param : fileName Name of the map's file.
     */
    public void readMap(String fileName) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			mapName = reader.readLine().replace("name ","");
			goldRequired = Integer.parseInt(reader.readLine().replace("win ",""));
			int linesCount = numberOfLines(fileName) -2;
			
			//storing the map from the file in the 2d array map
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

	/** get the number of lines in the map
	 * @param : name of the file
	 * @return : lines number of lines in the file
	*/
	public int numberOfLines(String fileName){
		int lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/** @return: map the current map */
	public char[][] getMap(){
		return map;
	}
	
	/** @return mapName name of the map */
	public String mapName(){
		return mapName;
	}
	
	/** @return: goldRequired total gold required for win */
	public int goldToWin(){
		return goldRequired;
	}
}

