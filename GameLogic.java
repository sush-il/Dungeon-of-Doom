//Contains the main logic part of the game, as it processes.

public class GameLogic {
	
	/* Reference to the map,player being used */
	private Map map;
	private HumanPlayer player;
	
	/**
	 * Default constructor
	 */
	public GameLogic() {
		map = new Map();
		player = new HumanPlayer();
		}

	public void runGame(){
		String command = player.getCommand();
		switch(command){
			case "look":
				map.readMap("Example_maps/small_example_map.txt");
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