package Model;

import javafx.beans.property.*;

public class Kullanıcılar {

    private  int userID ;
    private final StringProperty username = new SimpleStringProperty(this, "username", null);
    private final StringProperty password = new SimpleStringProperty(this, "password", null);
    private final BooleanProperty status = new SimpleBooleanProperty(this, "status");


    public Kullanıcılar(){
    }

    public Kullanıcılar(int userID, String username, String password, Boolean status) {
        this.userID = userID ;
        this.username.set(username);
        this.password.set(password);
        this.status.set(status);
    }

    public int getUserID() {//2
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID ;
    }



    public final String username() {
        return username.get();
    }

    public final void setUsername(String username) {//3
        usernameProperty().set(username);
    }

    public final StringProperty usernameProperty() {
        return username;
    }

    public final String getPassword() {
        return password.get();
    }

    public final void setPassword(String password) {
        passwordProperty().set(password);
    }

    public final StringProperty passwordProperty() {
        return password;
    }

    public final Boolean getStatus() {
        return status.get();
    }

    public final void setStatus(Boolean status) {
        statusProperty().set(status);
    }

    public final BooleanProperty statusProperty() {
        return status;
    }


}
