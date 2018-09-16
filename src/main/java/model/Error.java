package model;

public enum Error {
     BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404),
    INTERNAL_DB_ERROR(505),
    KEY_NUMBER_NOT_AVAILABLE(1001), LICENSE_TAG_NOT_AVAILABLE(1002), CAR_IS_NOT_REGISTERED(1003),
    CANT_UPDATE_ZONE(2001), CANT_UPDATE_BRAND_ID(2002);


    private final int code;

    private Error(int code){
        this.code = code;

    }

    public String getErrorCode() {
        return String.valueOf(code);
    }

    public String getErrorMessage(){
        switch (this) {
            case BAD_REQUEST : return "Bad Request";
            case NOT_FOUND   : return "Not found";
            case UNAUTHORIZED: return "Unauthorized";

            case INTERNAL_DB_ERROR: return "Fuck up nights!!!";

            case CAR_IS_NOT_REGISTERED: return "Car is not registered";
            case KEY_NUMBER_NOT_AVAILABLE: return "Key number not available";
            case LICENSE_TAG_NOT_AVAILABLE: return "License tag not available";

            case CANT_UPDATE_ZONE: return "Cant update zone ";
            case CANT_UPDATE_BRAND_ID: return "Cant update brand id";

            default: return "EXCEPTION";
        }

    }
}
/*
            if (checkKeyNumberIsAvailable(car)){

                    if (checkLicenseTagIsAvailable(car)) {

                    if (checkCarIsRegistered(car)){

*/