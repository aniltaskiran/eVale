package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.JsonResponse;
import model.Valet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckValetExistServlet", urlPatterns = {"/CheckValetExist"})
public class CheckValetExistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Valet valet = gson.fromJson(req.getReader(), Valet.class);

        sendValetInfo(resp, valet.getPhone());
    }

    void sendValetInfo(HttpServletResponse resp, String phone){

        DBConnection dao = new DBConnection();

        try {
            JsonResponse jsonResp = new JsonResponse(resp);

            Valet valet =  dao.getValetInfo(phone);

            if (valet != null) {
                jsonResp.sendValetObjectResponse(valet);
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
