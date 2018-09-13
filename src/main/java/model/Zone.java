package model;

public class Zone {
    private String venueID;
    private int orderID;
    private String zoneName;

    public String getVenueID() {
        return venueID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
