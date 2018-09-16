package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.Unirest;
import model.JsonResponse;
import model.Sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SmsController {

    private enum smsProperties{
        islem, user, pass, raporId, mesaj, numaralar, baslik
    }

    public void smsSend(HttpServletRequest request, HttpServletResponse response ){
        Sms sms = getSmsObject(request);

        try{
            com.mashape.unirest.http.HttpResponse<String> uniResponse = Unirest.post(sms.getApiUrl())
                    .field(smsProperties.islem.toString(), sms.getProcess())
                    .field(smsProperties.user.toString(), sms.getUsername())
                    .field(smsProperties.pass.toString(), sms.getPassword())
                    .field(smsProperties.mesaj.toString(), sms.getMessage())
                    .field(smsProperties.numaralar.toString(), sms.getNumber()).asString();

            System.out.printf(uniResponse.getBody());

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    private Sms getSmsObject(HttpServletRequest request) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(request.getReader(), Sms.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private Sms setMessage(HttpServletRequest request){
//
//    }


}
