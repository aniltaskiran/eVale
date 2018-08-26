package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Customer;
import model.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", urlPatterns = {"/registration"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Customer customer = gson.fromJson(req.getReader(), Customer.class);

        String ip = req.getRemoteAddr();

        DBConnection dao = new DBConnection();
        try {
            JsonResponse jsonResp = new JsonResponse(resp);
            jsonResp.sendResponse(dao.registerCustomer(customer));
            System.out.print("sms gönderiliyor.");
            // TODO: SMS GÖNDERİLECEK
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}