package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.CAR_LOG;
import model.Car;
import model.Error;

import model.Valet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ValetController {

    private enum ListType {
        ZoneWaiting, DeliveryWaiting;
    }

    public void updateValetInfo(HttpServletRequest request, HttpServletResponse response) {
        Valet valet = getValetObject(request);

        if (valet != null && valet.isAvailableToUpdateDB()){
            if (setValetInfo(valet)){
                new Response(response).sendResponse();
            } else {
                new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
            }
        } else {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
        }
    }

    public void getValetInfoFromPhone(HttpServletRequest request, HttpServletResponse response) {
        Valet reqValet = getValetObject(request);

        if (reqValet == null || reqValet.getPhone() == null){
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
        } else {
            Valet valet = getValetInfoFromDb(reqValet);
            if (valet == null){
                new Response(response).sendErrorResponse(Error.NOT_FOUND);
            } else if (valet.isAuthorized()){
                new Response(response).sendObject("valet", valet);
            } else {
                new Response(response).sendErrorResponse(Error.UNAUTHORIZED);
            }
        }
    }


    void saveTipAndMoveToLog(HttpServletRequest request, HttpServletResponse response) {
        Valet valet = getValetObject(request);
        if (valet == null && !valet.isAvailableToSaveTip()) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            return;
        }

        CAR_LOG carLog = getTBCurrentCarLog(valet);
        if (carLog == null) {
            new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
            return;
        }

        valet.setCar(carLog);

        if (saveTipAndMoveToLog(valet)){
            if (removeFromTBCurrentCar(valet)){
                new Response(response).sendResponse();
            } else {
                new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
            }
        } else {
            new Response(response).sendErrorResponse(Error.INTERNAL_DB_ERROR);
        }
    }

    public void getZoneWaitingCarList(HttpServletRequest request, HttpServletResponse response) {
        sendCarListResponseFor(ListType.ZoneWaiting, request, response);
    }


    void getDeliveryWaitingList(HttpServletRequest request, HttpServletResponse response) {
        sendCarListResponseFor(ListType.DeliveryWaiting, request, response);
    }


    private void sendCarListResponseFor(ListType type, HttpServletRequest request, HttpServletResponse response) {
        Valet valet = getValetObject(request);

        if (valet == null || valet.getVenueID() == null) {
            new Response(response).sendErrorResponse(Error.BAD_REQUEST);
        } else {
            ArrayList<Car> cars = null;

            if (type == ListType.ZoneWaiting) {
                cars = getZoneWaitingListFromDB(valet);
            } else if (type == ListType.DeliveryWaiting){
                cars = getDeliveryWaitingList(valet);
            }

            if (cars == null) {
                new Response(response).sendErrorResponse(Error.BAD_REQUEST);
            } else {
                new Response(response).sendObject("cars", cars);
            }
        }
    }



    /*
    Private Methods
     */

    private Valet getValetObject(HttpServletRequest request) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(request.getReader(), Valet.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Valet getValetInfoFromDb(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.getValetInfoWithPhone(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private CAR_LOG getTBCurrentCarLog(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.getTBCurrentCarLog(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Boolean setValetInfo(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.setValetInfo(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean saveTipAndMoveToLog(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.saveTipAndMoveCarToLogCar(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean removeFromTBCurrentCar(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.removeFromTBCurrentCar(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Car> getZoneWaitingListFromDB(Valet valet) {
        DBConnection dao = new DBConnection() ;
        return dao.getZoneWaitingList(valet);
    }

    private ArrayList<Car> getDeliveryWaitingList(Valet valet) {
        DBConnection dao = new DBConnection() ;
        return dao.getDeliveryWaitingList(valet);
    }
}
