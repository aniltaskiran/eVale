package model;

public class Admin {
    private String venueId;
    private String valetPhone;

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
