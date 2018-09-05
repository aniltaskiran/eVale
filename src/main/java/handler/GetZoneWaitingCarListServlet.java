package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Car;
import model.JsonResponse;
import model.Valet;
import model.Zone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


@WebServlet(name = "GetZoneWaitingCarListServlet", urlPatterns = {"/GetZoneWaitingCarList"})

public class GetZoneWaitingCarListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Valet valet = gson.fromJson(req.getReader(), Valet.class);

        getZoneList(resp, valet.getVenueId());
    }

    void getZoneList(HttpServletResponse resp, String venueId) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            // TODO: get zone waiting list

         /*   ArrayList<Car> cars =  dao.getZoneList(venueId);

            if (cars != null) {
                jsonResp.sendZoneObjectResponse(cars);
            } else {
                jsonResp.sendErrorResponse("404");
            }
*/
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}