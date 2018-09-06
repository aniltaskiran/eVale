package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Car;
import model.JsonResponse;
import model.Valet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetCurrentCarListServlet", urlPatterns = {"/GetCurrentCarList"})

public class GetCurrentCarListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Valet valet = gson.fromJson(req.getReader(), Valet.class);

        getCurrentCarList(resp, valet);
    }

    void getCurrentCarList(HttpServletResponse resp, Valet valet) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            ArrayList<Car> cars =  dao.getCurrentCarList(valet);

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