public class BotPlayer extends player{
    /** @return is bot is within 5x5 map range (true,false) */
	public boolean inRange(int[] botLocation,int[]playerLocation){
		//if distance between bot and player is <= 2
		if(Math.abs(botLocation[0] - playerLocation[0]) <= 2 && Math.abs(botLocation[1] - playerLocation[1]) <= 2 ){
			return true;
		}
		return false;
	}
}
