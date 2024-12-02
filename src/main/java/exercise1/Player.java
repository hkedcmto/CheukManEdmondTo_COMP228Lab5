/*
Purpose: as a bridge to provide query result to the tableview in fxml
 */

package exercise1;

public class Player {

    private int playerId;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String province;
    private String phoneNumber;

    //constructor
    public Player() {
    }

    //setters
    public void setPlayerId(int playerId){
        this.playerId = playerId;
    }
    public void setPlayerFirstName(String playerFirstName){
        this.firstName = playerFirstName;
    }
    public void setPlayerLastName(String playerLastName){this.lastName = playerLastName;}
    public void setPlayerAddress(String playerAddress){this.address = playerAddress;}
    public void setPlayerPostalCode(String playerPostalCode){this.postalCode = playerPostalCode;}
    public void setPlayerProvince(String playerProvince){this.province = playerProvince;}
    public void setPlayerPhoneNumber(String playerPhoneNumber){this.phoneNumber = playerPhoneNumber;}

    //getters
    public int getPlayerId(){return this.playerId;}
    public String getFirstName(){return this.firstName;}
    public String getLastName(){return this.lastName;}
    public String getAddress(){return this.address;}
    public String getPostalCode(){return this.postalCode;}
    public String getProvince(){return this.province;}
    public String getPhoneNumber(){return this.phoneNumber;}

    //return the fullname in string
    @Override
    public String toString(){
        return getFirstName() + " " + getLastName();
    }
}