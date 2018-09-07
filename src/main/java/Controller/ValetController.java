package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Error;
import model.Valet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ValetController {

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
                new Response(response).sendObject(valet);
            } else {
                new Response(response).sendErrorResponse(Error.UNAUTHORIZED);
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

    private Boolean setValetInfo(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.setValetInfo(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
