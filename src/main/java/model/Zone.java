package model;

public class Zone {
    private String venueId;
    private int orderId;
    private String zoneName;

    public String getVenueId() {
        return venueId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
