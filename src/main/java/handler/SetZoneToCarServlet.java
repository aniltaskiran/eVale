package handler;

import Controller.ResponseController;
import com.google.gson.Gson;
import Controller.DBConnection;
import model.Car;
import model.JsonResponse;
import model.ResponseType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "SetZoneToCarServlet", urlPatterns = {"/SetZoneToCar"})

public class SetZoneToCarServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        new ResponseController(resp, req).sendResponse(ResponseType.SetZoneToCar);
    }
}