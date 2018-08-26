package model;

import Security.Hash;

import java.util.Date;

public class Customer {

    private String phone;
    private String zone;
    private String carModel;
    private Integer carModelID;
    private int status;
    private String timestamp;

   public Customer(){}

    public Customer(String phone, String zone, String carModel, Integer carModelID, Integer status, String timestamp){
        this.phone = phone;
        this.zone = zone;
        this.carModel = carModel;
        this.carModelID = carModelID;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Customer(String phone, String zone, String carModel, Integer carModelID){
        this.phone = phone;
        this.zone = zone;
        this.carModel = carModel;
        this.carModelID = carModelID;
    }

    public Customer(String phone, String zone, String carModel, Integer carModelID, int status){
       this.phone = phone;
       this.zone = zone;
       this.carModel = carModel;
       this.carModelID = carModelID;
       this.status = status;
    }


    public Customer(String phone, String carModel, Integer carModelID){
        this.phone = phone;
        this.carModel = carModel;
        this.carModelID = carModelID;
    }

    public int getStatus() {
        return status;
    }

    public String getPhone(){
       return phone;
    }

    public String getPhoneHash() { return Hash.md5(phone);    }


    public String getZone() {
        return zone;
    }

    public String getCarModel() {
        return carModel;
    }

    public Integer getCarModelID() {
        return carModelID;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
