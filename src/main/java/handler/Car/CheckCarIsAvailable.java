package handler.Car;

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

@WebServlet(name = "CheckCarIsAvailable", urlPatterns = {"/CheckCarIsAvailable"})

public class CheckCarIsAvailable extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Gson gson = new Gson();
        Car car = gson.fromJson(req.getReader(), Car.class);


        checkRegistrationAvailability(resp, car);
    }

    void checkRegistrationAvailability(HttpServletResponse resp, Car car) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {

            // Checks current car table for key number availability.
            if (dao.checkKeyNumberIsAvailable(car)) {

                // Checks current car table for license tag availability.

                if (dao.checkLicenseTagIsAvailable(car)){

                    // Checks registered car table for license tag availability.
                    if (dao.checkCarIsRegistered(car)){

                        Car respCar = dao.getPhoneAndBrandId(car);
                        jsonResp.sendCarObject(respCar);
                    } else {
                        jsonResp.sendErrorResponse("104");
                    }

                } else {
                    jsonResp.sendErrorResponse("204");
                }
            } else {
                jsonResp.sendErrorResponse("304");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
