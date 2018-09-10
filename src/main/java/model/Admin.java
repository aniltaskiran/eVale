package model;

public class Admin {
    private String venueID;
    private String valetPhone;
    private Boolean authorizationStatus;
    private String selectedDateFirst;
    private String selectedDateSecond;

    public String getSelectedDateFirst() {
        return selectedDateFirst;
    }

    public void setSelectedDateFirst(String selectedDateFirst) {
        this.selectedDateFirst = selectedDateFirst;
    }

    public String getSelectedDateSecond() {
        return selectedDateSecond;
    }

    public void setSelectedDateSecond(String selectedDateSecond) {
        this.selectedDateSecond = selectedDateSecond;
    }

    public Boolean getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(Boolean authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public void setValetPhone(String valetPhone) {
        this.valetPhone = valetPhone;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getValetPhone() {
        return valetPhone;
    }

    public String getVenueID() {
        return venueID;
    }
}
