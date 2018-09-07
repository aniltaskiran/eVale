package handler.ValetLoginAndRegistration;

import com.google.gson.Gson;
import manager.DBConnection;
import model.JsonResponse;
import model.Valet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateValetValues", urlPatterns = {"/UpdateValetValues"})

public class UpdateValetValues extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Valet valet = gson.fromJson(req.getReader(), Valet.class);

        setValetInfo(resp, valet);
    }

    void setValetInfo(HttpServletResponse resp, Valet valet){

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {

            if (dao.setValetInfo(valet)) {
                jsonResp.sendTrueResponse();
            } else {
                jsonResp.sendErrorResponse("404");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jsonResp.sendErrorResponse("404");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}