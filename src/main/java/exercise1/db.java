package exercise1;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Db implements Config {

    //Initial database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_ADDR, DB_USER, DB_PASSWORD);
    }


    //Query for the Game table and return the List<Game> for the TableView to list out
    public List<Game> readGame() {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM \""+ gameTable+ "\"";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //query for each row
            while (rs.next()) {
                Game game = new Game(); //create a game obj for each row
                game.setGameId(rs.getInt("game_id"));
                game.setGameTitle(rs.getString("game_title"));
                games.add(game);
            }

        } catch (SQLException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
        return games;
    }

    //Query for the Player table and return the List<Player> for the TableView to list out
    public List<Player> readPlayer() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM \""+ playerTable+ "\"";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //query for each row
            while (rs.next()) {
                Player player = new Player(); //create a game obj for each row
                player.setPlayerId(rs.getInt("player_id"));
                player.setPlayerFirstName(rs.getString("first_name"));
                player.setPlayerLastName(rs.getString("last_name"));
                player.setPlayerAddress(rs.getString("address"));
                player.setPlayerPostalCode(rs.getString("postal_code"));
                player.setPlayerProvince(rs.getString("province"));
                player.setPlayerPhoneNumber(String.valueOf(rs.getInt("phone_number")));
                players.add(player);
            }

        } catch (SQLException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
        return players;
    }

    //Query for the Player table filtered by ID and return the List<Player> for the TableView to list out
    public List<Player> readPlayerById(int playerId) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM \"" + playerTable + "\" WHERE player_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {

                // Process each row
                while (rs.next()) {
                    Player player = new Player();
                    player.setPlayerId(rs.getInt("player_id"));
                    player.setPlayerFirstName(rs.getString("first_name"));
                    player.setPlayerLastName(rs.getString("last_name"));
                    player.setPlayerAddress(rs.getString("address"));
                    player.setPlayerPostalCode(rs.getString("postal_code"));
                    player.setPlayerProvince(rs.getString("province"));
                    player.setPlayerPhoneNumber(rs.getString("phone_number")); // Use getString for phone number
                    players.add(player);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
        return players;
    }


    //Query for the Player table and return the List<Player> for the TableView to list out
    public List<PaG> readPaG() {
        List<PaG> pags = new ArrayList<>();
        String query = "SELECT " +
                "pg.PLAYER_GAME_ID," +
                "pg.GAME_ID," +
                "pg.PLAYER_ID," +
                "pg.PLAYING_DATE," +
                "pg.SCORE," +
                "(p.FIRST_NAME || ' ' || p.LAST_NAME) AS FULLNAME," +
                "g.GAME_TITLE " +
                "FROM \""+ playerAndGameTable+ "\" pg " +
                "JOIN \""+ playerTable+ "\" p ON pg.PLAYER_ID = p.PLAYER_ID " +
                "JOIN \""+ gameTable+ "\" g ON pg.GAME_ID = g.GAME_ID";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //query for each row
            while (rs.next()) {
                PaG pag = new PaG(); //create a game obj for each row
                pag.setPlayerGameId(rs.getInt("player_game_id"));
                pag.setGameId(rs.getInt("game_id"));
                pag.setPlayerId(rs.getInt("player_id"));
                pag.setPlayingDate(rs.getString("playing_date"));
                pag.setScore(rs.getInt("score"));
                pag.setFullName(rs.getString("fullname"));
                pag.setGameTitle(rs.getString("game_title"));
                pags.add(pag);
            }

        } catch (SQLException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
        return pags;
    }

    //Add Game to Game Table
    public int addGameData(String gameTitle) {
        String query = "INSERT INTO \""+gameTable+"\" (game_title) VALUES (?)";
        int rowsAffected = 0; //initial nil rows affected

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gameTitle);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding data: " + e.getMessage());
        }
        return rowsAffected;
    }

    //Add Player to Player table
    public int addPlayerData(String firstName, String lastName, String address, String postalCode, String province, String phoneNumber) {
        String query = "INSERT INTO \""+playerTable+"\" (FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsAffected = 0; //initial nil rows affected

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,firstName);
            pstmt.setString(2,lastName);
            pstmt.setString(3,address);
            pstmt.setString(4,postalCode);
            pstmt.setString(5,province);
            pstmt.setString(6,phoneNumber);
            rowsAffected = pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error adding data: " + e.getMessage());
        }
        return rowsAffected;
    }

    //Edit Player in Player table
    public int editPlayerData(int playerId, String firstName, String lastName, String address, String postalCode, String province, String phoneNumber) {
        String query = "UPDATE \""+playerTable+"\" SET FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PROVINCE = ?, PHONE_NUMBER = ? WHERE player_id = ?";
        int rowsAffected = 0; //initial nil rows affected

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,firstName);
            pstmt.setString(2,lastName);
            pstmt.setString(3,address);
            pstmt.setString(4,postalCode);
            pstmt.setString(5,province);
            pstmt.setString(6,phoneNumber);
            pstmt.setInt(7,playerId);
            rowsAffected = pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error adding data: " + e.getMessage());
        }

        return rowsAffected;
    }

    //Add Played Games in Player and game table
    public int addPaGData(int gameId, int playerId, String playingDate, int score) {
        String query = "INSERT INTO \""+playerAndGameTable+"\" (game_id, player_id, playing_date, score) VALUES (?, ?, ?, ?)";
        int rowsAffected = 0; //initial nil rows affected

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            java.util.Date utilDate = dateFormat.parse(playingDate); // Convert to java.util.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date

            pstmt.setInt(1,gameId);
            pstmt.setInt(2,playerId);
            pstmt.setDate(3,sqlDate);
            pstmt.setInt(4,score);
            rowsAffected = pstmt.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error adding data: " + e.getMessage());
        }

        return rowsAffected;
    }

}