package handler.Car;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "GetMyCarServlet", urlPatterns = {"/getMyCar"})

public class GetMyCarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*        String phoneHash = req.getParameter(Hash.md5("customer"));
        String ip = req.getRemoteAddr();

        DBConnection dao = new DBConnection();
        try {
            JsonResponse jsonResp = new JsonResponse(resp);
            Customer customer = dao.getCustomerInfoFromPhoneHash(phoneHash);
            jsonResp.sendResponse(customer);

            if (customer != null) {
                customer.setStatus(CustomerStatus.WAITING.getValue());
                dao.registerCustomerToStandBy(customer);
            }



            System.out.print("sms gönderiliyor.");
            // TODO: SMS GÖNDERİLECEK
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

}