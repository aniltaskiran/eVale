package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Response {

    private String errorCode;
    private String message;
    private boolean result;
    HttpServletResponse response;


    public Response (HttpServletResponse response){
        this.result = true;
        this.response = response;
    }

    public < E > void sendObject(E object) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("result", result);
        jsonObject.add(object.getClass().getSimpleName(), new Gson().toJsonTree(object));

        sendJson(jsonObject);
    }

    public < E > void sendObject(ArrayList<E> arrayList) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("result", result);
        jsonObject.add(arrayList.getClass().toString(), new Gson().toJsonTree(arrayList));
        jsonObject.addProperty("count", arrayList.size());

        sendJson(jsonObject);
    }


    public void sendErrorResponse(Error error) throws IOException {
        this.result = false;
        this.errorCode = error.getErrorCode();
        this.message = error.getErrorMessage();
        sendResponse();
    }

    public void sendResponse() throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("errorCode", errorCode);
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("result", result);
        sendJson(jsonObject);
    }



    private void sendJson(JsonObject json) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }
}

