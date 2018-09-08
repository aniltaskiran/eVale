package handler.Car;

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

@WebServlet(name = "CheckCarIsAvailable", urlPatterns = {"/CheckCarIsAvailable"})

public class CheckCarIsAvailable extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        new ResponseController(resp, req).sendResponse(ResponseType.CheckCarIsAvailable);
    }
}
