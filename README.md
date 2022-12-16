The Dungeon of Doom is played on a rectangular grid. The map is either created from a text file or
a default map is loaded from the Map class. A human player can move around the map and pick up gold. 
The goal is to get gold to meet a win condition and then exit the dungeon. A bot spawns,
teleports randomly around the map until it is within 5x5 map range of the player. 
After which it follows the player, one move at a time, trying to catch the player. The bot can also collect gold.
The maps are read from the 'maps' folder; i.e. for any map the path my look as such: "./maps/example_map.txt".

The project utilises many ideas of OOP. Firstly a player class is created from which the HumanPlayer and the Bot inherit. 
This improves code reusability as allows us to write DRY code.
Certain actions such as generating spawn location can be utilised for both players. 
Through the inheritance of this class we utilise polymorphism where the player can take different forms; i.e., bot or Humanplayer. 
The humanplayer and the bot will perform a task in multiple ways. The human player, bot and the map are instantiated in the GameLogic class.
