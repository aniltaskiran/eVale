package handler.Car;

import Controller.ResponseController;
import model.ResponseType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "WrongEntityServlet", urlPatterns = {"/WrongEntityServlet"})

public class WrongEntityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        new ResponseController(resp, req).sendResponse(ResponseType.WrongEntity);
    }
}