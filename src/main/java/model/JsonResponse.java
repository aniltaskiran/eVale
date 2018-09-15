package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class JsonResponse {
    HttpServletResponse response;

    public JsonResponse(HttpServletResponse resp){
        this.response = resp;
    }

    public void sendZoneListJson(JsonElement jsonElement, int size) throws IOException{
        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", true);

        complaint.add("zoneList", jsonElement);
        complaint.addProperty("count", size);


        sendJson(complaint);
    }

    public void sendCarListJson(JsonElement jsonElement, int size) throws IOException{
        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", true);

        complaint.add("cars", jsonElement);
        complaint.addProperty("count", size);


        sendJson(complaint);
    }

    public void sendValetListObjectResponse(ArrayList<Valet> valetList) throws IOException {

        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", true);
        Gson gson = new Gson();
        String json = gson.toJson(valetList);

        complaint.add("valetList", gson.toJsonTree(valetList));
        complaint.addProperty("count", valetList.size());

        sendJson(complaint);

    }

    public void sendValetObjectResponse(Valet valet) throws IOException {
        JsonObject complaint = new JsonObject();
        Gson gson = new Gson();

        complaint.addProperty("result", "true");
        complaint.add("valet", gson.toJsonTree(valet));
        sendJson(complaint);

    }

    public void sendCarObject(Car car) throws IOException {
        JsonObject complaint = new JsonObject();
        Gson gson = new Gson();

        complaint.addProperty("result", "true");
        complaint.add("Car", gson.toJsonTree(car));
        sendJson(complaint);
    }


    public void sendErrorResponse(String errorCode) throws IOException {

        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", false);
        complaint.addProperty("errorCode", errorCode);

        sendJson(complaint);
    }

    public void sendTrueResponse() throws IOException {
        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", true);

        sendJson(complaint);
    }


    private void sendJson(JsonObject json) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }

    private void sendJson(String json) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }
}