package Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Error;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Response {

    private String resultCode;
    private String message;
    private boolean result;
    HttpServletResponse response;


    public Response (HttpServletResponse response){
        this.result = true;
        this.response = response;
    }

    public < E > void sendObject(String key, E object){
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("result", result);
        jsonObject.add(key, new Gson().toJsonTree(object));

        sendJson(jsonObject);
    }

    public < E > void sendObject(String key, ArrayList<E> arrayList){
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("result", result);
        jsonObject.add(key, new Gson().toJsonTree(arrayList));
        jsonObject.addProperty("count", arrayList.size());

        sendJson(jsonObject);
    }


    public void sendErrorResponse(Error error) {
        this.result = false;
        this.resultCode = error.getErrorCode();
        this.message = error.getErrorMessage();
        sendResponse();
    }

    public void sendResponse() {
        JsonObject jsonObject = new JsonObject();
        if (resultCode != null) {
            jsonObject.addProperty("resultCode", resultCode);
        }
        if (message != null) {
            jsonObject.addProperty("message", message);
        }
        jsonObject.addProperty("result", result);
        sendJson(jsonObject);
    }

    public void sendResponseWithCode(Error error) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("resultCode", error.getErrorCode());
        jsonObject.addProperty("message", error.getErrorMessage());
        jsonObject.addProperty("result", result);
        sendJson(jsonObject);
    }



    private void sendJson(JsonObject json) {
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(json);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

