package handler;

import com.google.gson.Gson;
import Controller.DBConnection;
import model.JsonResponse;
import model.Zone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GetZoneListServlet", urlPatterns = {"/GetZoneList"})

public class GetZoneListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Zone zone = gson.fromJson(req.getReader(), Zone.class);


        getZoneList(resp, zone.getVenueId());
    }

    void getZoneList(HttpServletResponse resp, String venueId) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            ArrayList<Zone> zone =  dao.getZoneList(venueId);

            if (zone != null) {
                jsonResp.sendZoneObjectResponse(zone);
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}