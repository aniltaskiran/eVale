package handler;

import com.google.gson.Gson;
import Controller.DBConnection;
import model.Car;
import model.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "SetZoneToCarServlet", urlPatterns = {"/SetZoneToCar"})

public class SetZoneToCarServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Car car = gson.fromJson(req.getReader(), Car.class);

        setZoneToCar(resp, car);
    }

    void setZoneToCar(HttpServletResponse resp, Car car){

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);


        try {
            if (dao.setZoneToCar(car)) {
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