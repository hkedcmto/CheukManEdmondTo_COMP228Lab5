/*
Purpose: as a bridge to provide query result to the tableview in fxml
 */

package exercise1;

public class PaG {

    private int playerGameId;
    private int gameId;
    private int playerId;
    private String playingDate;
    private int score;
    private String fullName;
    private String gameTitle;

    //constructor
    public PaG() {
    }

    //setters
    public void setPlayerGameId(int playerGameId){ this.playerGameId = playerGameId; }
    public void setGameId(int gameId){ this.gameId = gameId; }
    public void setPlayerId(int playerId){ this.playerId = playerId; }
    public void setPlayingDate(String playingDate){ this.playingDate = playingDate; }
    public void setScore(int score){ this.score = score; }
    public void setFullName(String fullName){ this.fullName = fullName; }
    public void setGameTitle(String gameTitle){ this.gameTitle = gameTitle; }


    //getters
    public int getPlayerGameId(){ return playerGameId; }
    public int getGameId(){ return gameId; }
    public int getPlayerId(){ return playerId; }
    public String getPlayingDate(){ return playingDate; }
    public int getScore(){ return score; }
    public String getFullName(){ return fullName; }
    public String getGameTitle(){ return gameTitle; }

}