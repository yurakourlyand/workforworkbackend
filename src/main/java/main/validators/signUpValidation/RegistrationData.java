package main.validators.signUpValidation;

import main.beans.Address;
import main.beans.User;


/**
 * @author Yura Kourlyand
 */
public class RegistrationData {
    private String profileImagePath;
    private String name;
    private String username;
    private String password;
    private String email;
    private Address area;
    private String summery;

    public RegistrationData(User user) throws Exception {
        GeneralClientNoNullsChecker(user);
        this.name = user.fullName;
        this.username = user.userName;
        this.email = user.email;
        this.password = user.getPassword();
        this.area = user.address;
        this.summery = user.summary;
        this.profileImagePath = user.profileImagePath;
    }

    public static void GeneralClientNoNullsChecker(User user) throws Exception {
        if (user.userName == null ||
                user.getPassword() == null ||
//                user.address == null ||
                user.email == null ||
                user.fullName == null) throw new Exception("Please fill all the sign-up fields");
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public Address getArea() {
        return area;
    }

    public void setArea(Address area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegistrationData{" +
                "profileImagePath='" + profileImagePath + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + area + '\'' +
                ", summery='" + summery + '\'' +
                '}';
    }
}
