package Controller;

import model.Sms;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

public class SmsController {

    private enum smsProperties{
        islem, user, pass, raporId, mesaj, numaralar, baslik
    }

    public void smsSend(Sms sms){

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(sms.getApiUrl());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody(smsProperties.islem.toString(),sms.getProcess(), ContentType.TEXT_PLAIN);
        builder.addTextBody(smsProperties.user.toString(),sms.getUsername(), ContentType.TEXT_PLAIN);
        builder.addTextBody(smsProperties.pass.toString(),sms.getPassword(), ContentType.TEXT_PLAIN);

        httpPost.setEntity(builder);


        //ADD MAVEN DEPENDENCIES for HTTPCLIENT

    }

}
