/*
Purpose: Store parameters of the program. (not use env because of GitHub)
 */

package exercise1;

//parameters
public interface Config {
    final String DB_ADDR = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD"; //Remote
    //final String DB_ADDR = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD"//On-Premises
    final String DB_USER = "COMP228_F24_soh_26";
    final String DB_PASSWORD = "comp22820241128";

    final String gameTable = "Cheuk Man_To_game";
    final String playerTable = "Cheuk Man_To_player";
    final String playerAndGameTable = "Cheuk Man_To_player_and_game";
}
