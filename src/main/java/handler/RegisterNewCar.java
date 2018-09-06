package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Car;
import model.JsonResponse;
import model.Valet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "RegisterNewCar", urlPatterns = {"/RegisterNewCar"})

public class RegisterNewCar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Car car = gson.fromJson(req.getReader(), Car.class);

        saveTipForValet(resp, car);
    }

    void saveTipForValet(HttpServletResponse resp, Car car){

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            if (dao.registerCar(car)) {
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