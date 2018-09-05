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

@WebServlet(name = "CarRegistrationServlet", urlPatterns = {"/CarRegistration"})

public class CarRegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Gson gson = new Gson();
        Car car = gson.fromJson(req.getReader(), Car.class);


        checkKeyNumberIsAvailable(resp, car);
    }

    void checkKeyNumberIsAvailable(HttpServletResponse resp, Car car) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            //TODO:licence tag kontrol edilecek

            if (dao.checkKeyNumberIsAvailable(car)) {

                if (dao.checkLicenseTagIsAvailable(car)){
                    jsonResp.sendTrueResponse();
                }
                else {
                    jsonResp.sendErrorResponse("404");
                }
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
