package exercise1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GameController{

    //Row 1
    @FXML private Label addGameLbl;

    //Row 2
    @FXML private Label addGameTitleLbl;
    @FXML private TextField addGameTitleTf;
    //Game table
    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, String> gameIdColumn;
    @FXML private TableColumn<Game, Integer> gameTitleColumn;
    private ObservableList<Game> gameList;

    //Row 3
    @FXML private Button addGameSubmitBtn;

    //Row 4
    @FXML private Label addPlayerLbl;
    @FXML private Label addPlayerEditLbl;

    //Row 5
    @FXML private Label addPlayerFirstNameLbl;
    @FXML private TextField addPlayerFirstNameTf;
    //Player table
    @FXML private TableView<Player> playerTable;
    @FXML private TableColumn<Player, Integer> playerIdColumn;
    @FXML private TableColumn<Player, String> playerFirstNameColumn;
    @FXML private TableColumn<Player, String> playerLastNameColumn;
    @FXML private TableColumn<Player,String> playerAddressColumn;
    @FXML private TableColumn<Player, String> playerPostalCodeColumn;
    @FXML private TableColumn<Player, String> playerProvinceColumn;
    @FXML private TableColumn<Player, String> playerPhoneNoColumn;
    private ObservableList<Player> playerList;

    //Row 6
    @FXML private Label addPlayerLastNameLbl;
    @FXML private TextField addPlayerLastNameTf;

    //Row 7
    @FXML private Label addPlayerAddressLbl;
    @FXML private TextField addPlayerAddressTf;

    //Row 8
    @FXML private Label addPlayerPostalCodeLbl;
    @FXML private TextField addPlayerPostalCodeTf;

    //Row 9
    @FXML private Label addPlayerProvinceLbl;
    @FXML private TextField addPlayerProvinceTf;

    //Row 10
    @FXML private Label addPlayerPhoneNumberLbl;
    @FXML private TextField addPlayerPhoneNumberTf;

    //Row 11
    @FXML private Button addPlayerSubmitBtn;
    @FXML private TextField playerIdHidden;
    @FXML private Button editPlayerSubmitBtn;
    @FXML private Button editPlayerCancelBtn;

    //Row 12
    @FXML private Label filterPlayerLbl;
    private ObservableList<Player> playerFilteredList;

    //Row 13
    @FXML private Label filterPlayerIdLbl;
    @FXML private ComboBox<Player> filterPlayerCbx;

    //Row 14
    @FXML private Button filterPlayerCancelBtn;
    @FXML private Button filterPlayerSubmitBtn;

    //Row 15
    @FXML
    private Label addPaGLbl;

    //Row 16
    @FXML private Label addPaGGameLbl;
    @FXML private ComboBox<Game> addPaGGameCbx;
    //Player And Game Table
    @FXML private TableView<PaG> pagTable;
    @FXML private TableColumn<Player, Integer> pagIdColumn;
    @FXML private TableColumn<Player, Integer> pagGameIdColumn;
    @FXML private TableColumn<Player, String> pagGameTitleColumn;
    @FXML private TableColumn<Player, Integer> pagPlayerIdColumn;
    @FXML private TableColumn<Player, String> pagFullNameColumn;
    @FXML private TableColumn<Player, String> pagPlayingDateColumn;
    @FXML private TableColumn<Player, Integer> pagScoreColumn;
    private ObservableList<PaG> pagList;


    //Row 17
    @FXML private Label addPaGPlayerLbl;
    @FXML private ComboBox<Player> addPaGPlayerCbx;

    //Row 18
    @FXML private Label addPaGPlayingDateLbl;
    @FXML private TextField addPaGPlayingDateTf;

    //Row 19
    @FXML private Label addPaGScoreLbl;
    @FXML private TextField addPaGScoreTf;

    //Row 20
    @FXML private Button addPaGSubmitBtn;

    //Initializer
    @FXML
    public void initialize() {

        //fetch content to Game table
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        gameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));

        Db gameDb = new Db();
        gameList = FXCollections.observableArrayList(
                gameDb.readGame()
        );
        gameTable.setItems(gameList); //add to Game Table
        addPaGGameCbx.setItems(gameList); //add to Game combobox
        addPaGGameCbx.setConverter(new javafx.util.StringConverter<Game>() { //display string in the obj
            @Override
            public String toString(Game game) {
                return game == null ? "" : game.getGameTitle();
            }

            @Override
            public Game fromString(String string) {
                return null;
            }
        });

        //fetch content to Player table
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("playerId"));
        playerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        playerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        playerProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        playerPhoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        Db playerDb = new Db();
        playerList = FXCollections.observableArrayList(
                playerDb.readPlayer()
        );
        playerTable.setItems(playerList); //add to Player Table
        addPaGPlayerCbx.setItems(playerList); //add to Player combobox
        addPaGPlayerCbx.setConverter(new javafx.util.StringConverter<Player>() { //display string in the obj
            @Override
            public String toString(Player player) {
                // Display "FirstName LastName" in the ComboBox
                return player == null ? "" : player.getFirstName() + " " + player.getLastName();
            }

            @Override
            public Player fromString(String string) {
                return null;
            }
        });
        filterPlayerCbx.setItems(playerList); //add to filter player combobox
        filterPlayerCbx.setConverter(new javafx.util.StringConverter<Player>() { //display string in combobox
            @Override
            public String toString(Player player) {
                // Display the Player ID as a string in the ComboBox
                return player == null ? "" : String.valueOf(player.getPlayerId());
            }

            @Override
            public Player fromString(String string) {
                return null;
            }
        });


        //handle double-click the row in player table to edit mode
        playerTable.setRowFactory(tv -> {
            TableRow<Player> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    updatePlayerMode(row.getItem()); //pass the item to method by obj
                }
            });
            return row;
        });


        //fetch content to Player And Game table
        pagIdColumn.setCellValueFactory(new PropertyValueFactory<>("playerGameId"));
        pagGameIdColumn.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        pagGameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
        pagPlayerIdColumn.setCellValueFactory(new PropertyValueFactory<>("playerId"));
        pagFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        pagPlayingDateColumn.setCellValueFactory(new PropertyValueFactory<>("playingDate"));
        pagScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        //insert into DB
        Db pagDb = new Db();
        pagList = FXCollections.observableArrayList(
                pagDb.readPaG()
        );
        pagTable.setItems(pagList);
    }

    //Add Game
    public void onAddGameSubmitBtnClicked(ActionEvent actionEvent) {
        if(!addGameTitleTf.getText().isEmpty()){ //Fill in all fields
            Db addGameDb = new Db();
            int result = addGameDb.addGameData(addGameTitleTf.getText());
            if (result > 0) { //add success
                showMessage(0,result + " Game added successfully"); //show msg to user
                addGameTitleTf.clear(); //clear the Tf
                initialize(); //reload the screen
            }else{ //fail
                showMessage(1,"Game not added");
            }
        }else{ //not fill in all fields
            showMessage(1,"Please fill in Game Title");
        }
    }

    //Add Player
    public void onAddPlayerSubmitBtnClicked(ActionEvent actionEvent) {
        if(!addPlayerFirstNameTf.getText().isEmpty() &&
            !addPlayerLastNameTf.getText().isEmpty() &&
            !addPlayerAddressTf.getText().isEmpty() &&
            !addPlayerPostalCodeTf.getText().isEmpty() &&
            !addPlayerProvinceTf.getText().isEmpty() &&
            !addPlayerPhoneNumberTf.getText().isEmpty()) { //Fill in all fields
            //insert into DB
            Db addPlayerDb = new Db();
            int result = addPlayerDb.addPlayerData(
                    addPlayerFirstNameTf.getText(),
                    addPlayerLastNameTf.getText(),
                    addPlayerAddressTf.getText(),
                    addPlayerPostalCodeTf.getText(),
                    addPlayerProvinceTf.getText(),
                    addPlayerPhoneNumberTf.getText()
            );
            if (result > 0) { //add success
                showMessage(0,result + " Player added successfully"); //show msg to user
                //clear the Tf
                addPlayerFirstNameTf.clear();
                addPlayerLastNameTf.clear();
                addPlayerAddressTf.clear();
                addPlayerPostalCodeTf.clear();
                addPlayerProvinceTf.clear();
                addPlayerPhoneNumberTf.clear();
                initialize(); //reload the screen
            } else { //fail
                showMessage(1,"Player not added");
            }
        }else{ //not Fill in all fields
            showMessage(1,"Please fill in all the fields");
        }
    }

    //Add Played Game Info
    public void onAddPaGSubmitBtnClicked(ActionEvent actionEvent) {
        try {
            if (addPaGGameCbx.getValue() == null ||
                    addPaGPlayerCbx.getValue() == null ||
                    addPaGPlayingDateTf.getText().isEmpty() ||
                    addPaGScoreTf.getText().isEmpty()
            ) { //Fill in all fields validation
                throw new Exception("Please fill in all the fields.");
            }
            if (!isValidDate(addPaGPlayingDateTf.getText())) { //Date format validation
                throw new Exception("Please input the date in correct format (MM-DD-YYYY)");
            }
            if (!isValidScore(Integer.parseInt(addPaGScoreTf.getText()))) { //Score format validation
                throw new Exception("Please input the score greater than 0");
            }

            //Convert Game Title to Game ID
            Game selectedGame = addPaGGameCbx.getValue(); //put the selected combobox obj to game obj
            int gameId = selectedGame.getGameId();

            //Convert Player full name to Player ID
            Player selectedPlayer = addPaGPlayerCbx.getValue();//put the selected combobox obj to player obj
            int playerId = selectedPlayer.getPlayerId();

            //insert into DB
            Db addPaGDb = new Db();
            int result = addPaGDb.addPaGData(
                    gameId,
                    playerId,
                    addPaGPlayingDateTf.getText(),
                    Integer.parseInt(addPaGScoreTf.getText())
            );
            if (result > 0) {
                showMessage(0,result + " Played Game added successfully");
                addPaGGameCbx.getSelectionModel().clearSelection();
                addPaGPlayerCbx.getSelectionModel().clearSelection();
                addPaGPlayingDateTf.clear();
                addPaGScoreTf.clear();
                initialize();
            }else{
                throw new Exception("Played Game not added");
            }

        } catch (Exception e) {
            showMessage(1, e.getMessage());
        }
    }

    //Edit Player
    public void onEditPlayerSubmitBtnClicked(ActionEvent actionEvent) {
        if(!addPlayerFirstNameTf.getText().isEmpty() &&
                !addPlayerLastNameTf.getText().isEmpty() &&
                !addPlayerAddressTf.getText().isEmpty() &&
                !addPlayerPostalCodeTf.getText().isEmpty() &&
                !addPlayerProvinceTf.getText().isEmpty() &&
                !addPlayerPhoneNumberTf.getText().isEmpty()) { //Fill in all fields
            //update DB
            Db editPlayerDb = new Db();
            int result = editPlayerDb.editPlayerData(
                    Integer.parseInt(playerIdHidden.getText()),
                    addPlayerFirstNameTf.getText(),
                    addPlayerLastNameTf.getText(),
                    addPlayerAddressTf.getText(),
                    addPlayerPostalCodeTf.getText(),
                    addPlayerProvinceTf.getText(),
                    addPlayerPhoneNumberTf.getText()
            );
            if (result > 0) { //edit success
                showMessage(0,"Player #"+playerIdHidden.getText()+" edited successfully"); //show msg to user
                //clear the Tf
                addPlayerMode();
                initialize(); //reload the screen
            } else { //fail
                showMessage(1,"Player not edited");
            }
        }else{ //not Fill in all fields
            showMessage(1,"Please fill in all the fields");
        }
    }

    //Method for Date format validation (DD-MM-YY)
    private boolean isValidDate(String dateStr) {
        // Regex for MM-DD-YY format
        String regex = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-(\\d{4})$";
        if (!dateStr.matches(regex)) {
            return false; // Fails regex pattern check
        }

        // Attempt parsing using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
            return true; // Valid date
        } catch (DateTimeParseException e) {
            return false; // Invalid date
        }
    }

    //Method for the Score format validation (>=0)
    private boolean isValidScore(int score) {
        return score >= 0;
    }

    //Method to show a message box when success
    @FXML
    private void showMessage(int type, String message) {
        Alert alert = null;
        if (type==0){// Create an Information alert
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
        }else{ //Create an Error alert
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
        }
        alert.setHeaderText(null);  // No header text
        alert.setContentText(message);  // message to display
        alert.showAndWait(); // Show the dialog and wait for the user to close it
    }

    //Edit Player Mode - change form to edit player
    public void updatePlayerMode(Player player){
        Player selectedPlayer = addPaGPlayerCbx.getValue();
        addPlayerLbl.setText("Edit Player #" +player.getPlayerId()); //change to Edit Player
        addPlayerFirstNameTf.setText(player.getFirstName());
        addPlayerLastNameTf.setText(player.getLastName());
        addPlayerAddressTf.setText(player.getAddress());
        addPlayerPostalCodeTf.setText(player.getPostalCode());
        addPlayerProvinceTf.setText(player.getProvince());
        addPlayerPhoneNumberTf.setText(player.getPhoneNumber());
        addPlayerSubmitBtn.setManaged(false);
        addPlayerSubmitBtn.setVisible(false);
        editPlayerSubmitBtn.setManaged(true);
        editPlayerSubmitBtn.setVisible(true);
        editPlayerCancelBtn.setManaged(true);
        editPlayerCancelBtn.setVisible(true);
        playerIdHidden.setText(String.valueOf(player.getPlayerId()));
    }

    //Add Player Mode - change the form to Add Player
    public void addPlayerMode(){
        addPlayerLbl.setText("Add Player"); //change to Add Player
        //clear the Tf
        addPlayerFirstNameTf.clear();
        addPlayerLastNameTf.clear();
        addPlayerAddressTf.clear();
        addPlayerPostalCodeTf.clear();
        addPlayerProvinceTf.clear();
        addPlayerPhoneNumberTf.clear();
        playerIdHidden.clear();
        addPlayerSubmitBtn.setManaged(true);
        addPlayerSubmitBtn.setVisible(true);
        editPlayerSubmitBtn.setManaged(false);
        editPlayerSubmitBtn.setVisible(false);
        editPlayerCancelBtn.setManaged(false);
        editPlayerCancelBtn.setVisible(false);
    }

    //Cancel edit mode and return to add player mode
    public void onEditPlayerCancelBtnClicked(ActionEvent actionEvent) {
        addPlayerMode();
    }

    //Filter player by id
    public void onFilterPlayerSubmitBtnClicked(ActionEvent actionEvent) throws Exception {
        try {
            if (filterPlayerCbx.getValue() == null) { //Fill in all fields validation
                throw new Exception("Please select player ID.");
            }

            //Convert Player to Player ID
            Player selectedPlayer = filterPlayerCbx.getValue();//put the selected combobox obj to player obj
            int playerId = selectedPlayer.getPlayerId();

            playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("playerId"));
            playerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            playerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            playerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            playerProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
            playerPhoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            //query DB
            Db filterPlayerDb = new Db();
            playerFilteredList = FXCollections.observableArrayList(
                    filterPlayerDb.readPlayerById(selectedPlayer.getPlayerId())
            );
            playerTable.setItems(playerFilteredList); //add to Player Table

        } catch (Exception e) {
            showMessage(1, e.getMessage());
        }
    }

    public void onFilterPlayerCancelBtnClicked(ActionEvent actionEvent) throws Exception {
        initialize();
    }
}
