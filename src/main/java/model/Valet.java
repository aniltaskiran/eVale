package model;

import Utils.CurrentTimestamp;

public class Valet {

    private Boolean isAuthorized;
    private Boolean isAdmin;
    private String phone;
    private String firstName;
    private String surname;
    private String venueId;
    private String income;

    public String getIncomeLicenseTag() {
        return incomeLicenseTag;
    }

    public void setIncomeLicenseTag(String incomeLicenseTag) {
        this.incomeLicenseTag = incomeLicenseTag;
    }

    private String incomeLicenseTag;
    private CAR_LOG car;

    public CAR_LOG getCar() {
        return car;
    }

    public void setCar(CAR_LOG car) {
        this.car = car;
    }

    public Boolean isAvailableToUpdateDB(){
        if (isAuthorized != null && venueId != null && phone != null) {
            return true;
        }
        return false;
    }

    public Boolean isAvailableToSaveTip(){
        if (phone != null && income != null && incomeLicenseTag != null && venueId != null) {
            return true;
        }
        return false;
    }

    public String getIncome() {
        return income;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getTimestamp(){
        return CurrentTimestamp.getTimestamp();
    }

    public String getSurname() {
        return surname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
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