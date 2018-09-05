package model;

public class Valet {
    private boolean isAuthorized;
    private boolean isAdmin;
    private String phone;
    private String firstName;
    private String surname;
    private String venueId;

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }
}