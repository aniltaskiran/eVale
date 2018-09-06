package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GetCurrentCarListWithDate", urlPatterns = {"/GetCurrentCarListWithDate"})

public class GetCurrentCarListWithDate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Admin admin = gson.fromJson(req.getReader(), Admin.class);

        getCurrentCarList(resp, admin);
    }

    void getCurrentCarList(HttpServletResponse resp, Admin admin) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            ArrayList<CAR_LOG> cars =  dao.getCurrentCarListWithDate(admin);

            if (cars != null) {
                jsonResp.sendCarListJson(new Gson().toJsonTree(cars), cars.size());
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}