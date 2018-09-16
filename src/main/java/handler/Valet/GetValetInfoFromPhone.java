package handler.Valet;

import Controller.ResponseController;
import Controller.SmsController;
import model.ResponseType;
import model.Sms;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetValetInfoFromPhone", urlPatterns = {"/GetValetInfoFromPhone"})
public class GetValetInfoFromPhone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        new ResponseController(resp, req).sendResponse(ResponseType.GetValetInfo);
    }
}
