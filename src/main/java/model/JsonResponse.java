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
*/

                jsonArray.add(gson.toJsonTree(customerList.get(i)));
            }

            complaint.add("customer",jsonArray);
            sendJson(complaint);
        } else {
            sendResponse(false);
        }
    }

    public void sendResponse(Customer customer) throws IOException {
        JsonObject complaint = new JsonObject();

        if (customer != null){
            complaint.addProperty("result", "true");
            complaint.addProperty("zone", customer.getZone());
            complaint.addProperty("phone", customer.getPhone());
            complaint.addProperty("carModel", customer.getCarModel());
            complaint.addProperty("carModelID", customer.getCarModelID());
            sendJson(complaint);
            } else {
            sendResponse(false);
        }
    }

    public void sendResponse(Boolean response) throws IOException {
        JsonObject complaint = new JsonObject();
        complaint.addProperty("result", response);
        sendJson(complaint);
    }

    private void sendJson(JsonObject json) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }
}