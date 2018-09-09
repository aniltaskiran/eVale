package model;

import Security.Hash;

public class CAR_LOG {
    private String registerDate;
    private String registerTimestamp;
    private String deliverDate;
    private String licenseTag;
    private String zone;
    private String brandId;
    private String valetFirstName;
    private String valetSurname;
    private String keyNumber;
    private String phone;
    private String registerValetId;

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(String registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
    }

    public String getRegisterValetId() {
        return registerValetId;
    }

    public void setRegisterValetId(String registerValetId) {
        this.registerValetId = registerValetId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getLicenseTag() {
        return licenseTag;
    }
    public String getLicenseTagHash() {
        return Hash.md5(licenseTag);
    }

    public void setLicenseTag(String licenseTag) {
        this.licenseTag = licenseTag;
    }

    public String getZone() {
        return zone;
    }

    public String getValetFirstName() {
        return valetFirstName;
    }

    public void setValetFirstName(String valetFirstName) {
        this.valetFirstName = valetFirstName;
    }

    public String getValetSurname() {
        return valetSurname;
    }

    public void setValetSurname(String valetSurname) {
        this.valetSurname = valetSurname;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

}
