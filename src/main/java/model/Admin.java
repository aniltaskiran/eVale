package model;

public class Admin {
    private String venueId;
    private String valetPhone;
    private Boolean authorizationStatus;

    public Boolean getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(Boolean authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public void setValetPhone(String valetPhone) {
        this.valetPhone = valetPhone;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getValetPhone() {
        return valetPhone;
    }

    public String getVenueId() {
        return venueId;
    }
}
