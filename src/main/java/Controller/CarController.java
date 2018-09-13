package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Car;
import model.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CarController {


    public enum ChangeType {
        setZoneToCar, setCarStatus;
    }

    public void registerNewCar(HttpServletRequest request, HttpServletResponse response) {
        Car car = getCarObject(request);
        if (checkIsBadRequestForRegister(car)) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            return;
        }

        try {
            if (registerCarToDb(car)) {
                new Response(response).sendResponse();
            } else {
                new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
        }
    }

    public void checkRegistrationAvailability(HttpServletRequest request, HttpServletResponse response) {
        Car car = getCarObject(request);

        if (checkIsBadRequest(car)) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            return;
        }

        try {
            if (checkKeyNumberIsAvailable(car)){

                if (checkLicenseTagIsAvailable(car)) {

                    if (checkCarIsRegistered(car)){

                        car = getPhoneAndBrandId(car);
                        if (car == null){
                            new Response(response).sendResponseWithCode(Error.CAR_IS_NOT_REGISTERED);
                        } else {
                            new Response(response).sendObject("car", car);
                        }
                    } else {
                        new Response(response).sendResponseWithCode(Error.CAR_IS_NOT_REGISTERED);
                    }

                } else {
                    new Response(response).sendErrorResponse(Error.LICENSE_TAG_NOT_AVAILABLE);
                }

            } else {
                new Response(response).sendErrorResponse(Error.KEY_NUMBER_NOT_AVAILABLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
        }
    }

    public void setZoneToCar(HttpServletRequest request, HttpServletResponse response){
        Car car = getCarObject(request);

        if (car == null || car.getLicenseTag() == null || car.getZone() == null) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            return;
        }

        changeInfoAboutCarOnDB(ChangeType.setZoneToCar, car, response);
    }

    public void setCarStatus(HttpServletRequest request, HttpServletResponse response){
        Car car = getCarObject(request);

        if (car == null || car.getLicenseTag() == null || car.getStatus() == null) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            return;
        }

        changeInfoAboutCarOnDB(ChangeType.setCarStatus, car, response);

    }



    /*

    Private class

     */

    private Car getCarObject(HttpServletRequest request) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(request.getReader(), Car.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Boolean checkKeyNumberIsAvailable(Car car) throws Exception {
        DBConnection dao = new DBConnection();
        try {
            return dao.checkKeyNumberIsAvailable(car);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private Boolean checkLicenseTagIsAvailable(Car car) throws Exception {
        DBConnection dao = new DBConnection();
        try {
            return dao.checkLicenseTagIsAvailable(car);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private Boolean checkCarIsRegistered(Car car) throws Exception {
        DBConnection dao = new DBConnection();
        try {
            return dao.checkCarIsRegistered(car);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private Car getPhoneAndBrandId(Car car) throws Exception {
        DBConnection dao = new DBConnection();
        try {
            return dao.getPhoneAndBrandId(car);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    private Boolean registerCarToDb(Car car) throws Exception {
        DBConnection dao = new DBConnection();
        try {
            return dao.registerCar(car);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private void changeInfoAboutCarOnDB(ChangeType type, Car car, HttpServletResponse response) {
        Boolean isSuccess;

        DBConnection dao = new DBConnection();
        try {
            if (type == ChangeType.setZoneToCar){
                isSuccess = dao.setZoneToCar(car);
            } else if (type == ChangeType.setCarStatus){
                isSuccess = dao.setCarStatus(car);
            } else {
                isSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess){
            new Response(response).sendResponse();
        } else {
            new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
        }
    }

    private Boolean checkIsBadRequest(Car car){
        if (car == null) {
            return true;

        } else if (car.getLicenseTag() == null
                || car.getKeyNumber() == null
                || car.getVenueID() == null) {

            return true;

        } else {
            return false;
        }
    }

    private Boolean checkIsBadRequestForRegister(Car car){
        if (car == null) {
            return true;

        } else if (car.getLicenseTag() == null
                || car.getKeyNumber() == null
                || car.getVenueID() == null
                || car.getPhone() == null
                || car.getValetID() == null) {

            return true;

        } else {
            return false;
        }
    }
}
