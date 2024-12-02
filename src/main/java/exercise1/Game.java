/*
Purpose: as a bridge to provide query result to the tableview in fxml
 */

package exercise1;

public class Game {

    private int gameId;
    private String gameTitle;

    //Constructor
    public Game() {
    }

    //setters
    public void setGameId(int gameId){ this.gameId = gameId; }
    public void setGameTitle(String gameTitle){ this.gameTitle = gameTitle; }

    //getters
    public int getGameId() { return gameId; }
    public String getGameTitle() { return gameTitle; }

    //return the game title in string
    @Override
    public String toString() {
        return gameTitle;
    }
}