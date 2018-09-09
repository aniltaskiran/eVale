package handler.Lists;

import Controller.ResponseController;
import com.google.gson.Gson;
import Controller.DBConnection;
import model.Car;
import model.JsonResponse;
import model.ResponseType;
import model.Valet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GetDeliveryWaitingListServlet", urlPatterns = {"/GetDeliveryWaitingList"})

public class GetDeliveryWaitingListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        new ResponseController(resp, req).sendResponse(ResponseType.DeliveryWaitingList);
    }
}