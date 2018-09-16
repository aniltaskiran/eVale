package Controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import model.JsonResponse;
import model.Sms;

import javax.servlet.http.HttpServletResponse;


public class SmsController {

    private enum smsProperties{
        islem, user, pass, raporId, mesaj, numaralar, baslik
    }

    public void smsSend(Sms sms, HttpServletResponse response ){
        JsonResponse json = new JsonResponse(response);
        Gson gson = new Gson();

        try{
            com.mashape.unirest.http.HttpResponse<String> uniResponse = Unirest.post(sms.getApiUrl())
                    .field(smsProperties.islem.toString(), sms.getProcess())
                    .field(smsProperties.user.toString(), sms.getUsername())
                    .field(smsProperties.pass.toString(), sms.getPassword()).asString();

            System.out.printf(uniResponse.getBody());
        }catch (Exception e){

            e.printStackTrace();
        }

    }

}
