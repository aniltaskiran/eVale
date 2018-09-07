package handler.ValetLoginAndRegistration;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import manager.DBConnection;

import model.Error;
import model.Response;
import model.Valet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "GetValetInfoFromPhone", urlPatterns = {"/GetValetInfoFromPhone"})
public class GetValetInfoFromPhone extends HttpServlet {
    private HttpServletResponse response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Gson gson = new Gson();
        Valet valet = null;
        response = resp;

        try {

            BufferedReader reader = req.getReader();

            valet = gson.fromJson(reader, Valet.class);

            if (valet.getPhone() == null){
                sendResponse(Error.BAD_REQUEST);
            } else {
                sendResponse(getValetInfo(valet));
            }

            } catch (JsonIOException e) {
            e.printStackTrace();
            sendResponse(Error.BAD_REQUEST);
        } catch (JsonSyntaxException jse){
            sendResponse(Error.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            sendResponse(Error.BAD_REQUEST);
        }
    }

    Valet getValetInfo(Valet valet){
        DBConnection dao = new DBConnection();
        try {
            return dao.getValetInfoWithPhone(valet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void sendResponse(Error error) {
        Response response = new Response(this.response);
        try {
                response.sendErrorResponse(error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendResponse(Valet valet) {
        Response response = new Response(this.response);
        try {
            if (valet != null) {
                if (valet.isAuthorized()){
                    response.sendObject(valet);
                } else {
                    response.sendErrorResponse(Error.UNAUTHORIZED);
                }
            } else {
                response.sendErrorResponse(Error.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
