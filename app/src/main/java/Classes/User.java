package Classes;

/**
 * Created by hieuduong on 10/25/17.
 */

public class User {
    private int UserID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Username;
    private String RefID;
    private int UserRole;
    private String SessionID;
    private String Status;

    public User(int userID, String firstName, String lastName, String email, String refID, String username, int userRole, String sessionID, String status) {
        UserID = userID;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        RefID = refID;
        Username = username;
        UserRole = userRole;
        SessionID = sessionID;
        Status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + Email + '\'' +
                ", Username='" + Username + '\'' +
                ", RefID='" + RefID + '\'' +
                ", UserRole=" + UserRole +
                ", SessionID='" + SessionID + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRefID() {
        return RefID;
    }

    public void setRefID(String refID) {
        RefID = refID;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int userRole) {
        UserRole = userRole;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
