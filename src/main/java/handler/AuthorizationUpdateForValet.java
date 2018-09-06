package handler;

import com.google.gson.Gson;
import manager.DBConnection;
import model.Admin;
import model.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AuthorizationUpdateForValet", urlPatterns = {"/AuthorizationUpdateForValet"})

public class AuthorizationUpdateForValet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Admin admin = gson.fromJson(req.getReader(), Admin.class);

        giveAuthorizationToValet(resp, admin);
    }

    void giveAuthorizationToValet(HttpServletResponse resp, Admin admin){

        DBConnection dao = new DBConnection();
        JsonResponse jsonResp = new JsonResponse(resp);

        try {

            if (dao.updateAuthorizationForValet(admin)) {
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