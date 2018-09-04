package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.JsonResponse;
import model.Valet;
import model.Zone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;


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
            JsonResponse jsonResp = new JsonResponse(resp);

            Valet valet =  dao.getValetInfoFromPhone(phone);

            if (valet != null) {
                jsonResp.sendValetObjectResponse(valet);
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}