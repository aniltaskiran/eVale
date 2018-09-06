package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Car;
import model.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateCurrentValuesServlet", urlPatterns = {"/UpdateCurrentValues"})

public class UpdateCurrentValuesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Car car = gson.fromJson(req.getReader(), Car.class);

        updateCurrentCar(resp, car);
    }

    void updateCurrentCar(HttpServletResponse resp, Car car){

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            if (dao.updateCurrentCar(car)) {
                jsonResp.sendTrueResponse();
            } else {
                jsonResp.sendErrorResponse("303");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jsonResp.sendErrorResponse("404");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}