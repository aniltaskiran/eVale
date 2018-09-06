package model;

public class Car {
    private String keyNumber;
    private String licenseTag;
    private String venueId;
    private int brandId;
    private String zone;
    private String phone;
    private String status;
    private String registrationTimestamp;
    private String licenseTagHash;

    public String getLicenseTagHash() {
        return licenseTagHash;
    }

    public void setLicenseTagHash(String licenseTagHash) {
        this.licenseTagHash = licenseTagHash;
    }

    public String getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(String registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getLicenseTag() {
        return licenseTag;
    }

    public void setLicenseTag(String licenseTag) {
        this.licenseTag = licenseTag;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


