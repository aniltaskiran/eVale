package handler;

import com.google.gson.Gson;
import Controller.DBConnection;
import model.JsonResponse;
import model.Valet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GetValetPerformanceServlet", urlPatterns = {"/GetValetPerformance"})

public class GetValetPerformanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Valet valet = gson.fromJson(req.getReader(), Valet.class);


        getZoneList(resp,valet);
    }

    void getZoneList(HttpServletResponse resp, Valet valet) {

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {
            ArrayList<Valet> valetList =  dao.getValetPerformance(valet);

            if (valetList != null) {
                jsonResp.sendValetListObjectResponse(valetList);
            } else {
                jsonResp.sendErrorResponse("404");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}