package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Error;
import model.ResponseType;
import model.Valet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseController {
    HttpServletResponse response;
    HttpServletRequest request;

    public ResponseController(HttpServletResponse response, HttpServletRequest request) {
        this.response = response;
        this.request = request;
    }


    public void sendResponse(ResponseType type) {
            switch (type) {
                case GetValetInfo:
                    new ValetController().getValetInfoFromPhone(request, response);
                    break;
                case UpdateValetInfo:
                    new ValetController().updateValetInfo(request, response);
                    break;
            }
    }
}
