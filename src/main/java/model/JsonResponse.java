package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class JsonResponse {
    HttpServletResponse response;

    public JsonResponse(HttpServletResponse resp){
        this.response = resp;
    }
/*
    public void sendResponse(ArrayList<Customer> customerList) throws IOException {
        JsonObject complaint = new JsonObject();

        JsonArray jsonArray = new JsonArray();

        if (customerList != null){
            complaint.addProperty("result", "true");
            for (int i = 0; i < customerList.size(); i++) {
              //  JsonObject newJsonObject = new JsonObject();
                Gson gson = new Gson();
/*
                newJsonObject.addProperty("zone", customerList.get(i).getZone());
                newJsonObject.addProperty("phone", customerList.get(i).getPhone());
                newJsonObject.addProperty("carModel", customerList.get(i).getCarModel());
                newJsonObject.addProperty("carModelID", customerList.get(i).getCarModelID());

                newJsonObject.addProperty("date", customerList.get(i).getDate());
***
                jsonArray.add(gson.toJsonTree(customerList.get(i)));
            }

            complaint.add("customer",jsonArray);
            sendJson(complaint);
        } else {
            sendResponse(false);
        }
    }

    */

    public void sendZoneObjectResponse(ArrayList<Zone> zonelist) throws IOException {

        Gson gson = new Gson();
        String json = gson.toJson(zonelist);

        sendJson(json);

    }

    public void sendValetObjectResponse(Valet valet) throws IOException {
        JsonObject complaint = new JsonObject();

        complaint.addProperty("result", "true");
        complaint.addProperty("phone", valet.getPhone());
        complaint.addProperty("firstName", valet.getFirstName());
        complaint.addProperty("surname", valet.getSurname());
        complaint.addProperty("isAuthorized", valet.isAuthorized());
        complaint.addProperty("isAdmin", valet.isAdmin());

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