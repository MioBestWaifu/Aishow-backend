package info;

public class UserConnection {
    private UserInformation userInformation;
    public boolean refreshing;
    public String lastPage;
    public UserConnection(){}

    public UserConnection (UserInformation info){
        userInformation = info;
    }
    
    public UserInformation getUserInformation() {
        return userInformation;
    }
    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }
    
}
