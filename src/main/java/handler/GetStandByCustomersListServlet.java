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
import java.util.ArrayList;

@WebServlet(name = "GetStandByCustomersListServlet", urlPatterns = {"/getStandByCustomers"})

public class GetStandByCustomersListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Customer customer = gson.fromJson(req.getReader(), Customer.class);
        String ip = req.getRemoteAddr();

        sendCustomerList(resp);

        System.out.print("sms gönderiliyor.");
        // TODO: SMS GÖNDERİLECEK
    }

    void sendCustomerList(HttpServletResponse resp){

        DBConnection dao = new DBConnection();
        try {
            JsonResponse jsonResp = new JsonResponse(resp);
            ArrayList<Customer> customerList = dao.getStandByCustomerList();
            if (customerList.size() > 0) {
                jsonResp.sendResponse(customerList);
            } else {
                jsonResp.sendResponse(false);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}