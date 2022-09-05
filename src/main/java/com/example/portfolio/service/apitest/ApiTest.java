package com.example.portfolio.service.apitest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ApiTest {

    public void resultAPI(){
        String result = readAPI();
        Gson resultGson = new GsonBuilder().setPrettyPrinting().create();
        String element = resultGson.toJson(result);
    }

    public String readAPI(){

        String key = "%2Fk49W4UhNTuGlvyhZ6NCaHVhV1%" +
                "2BBp0wbhWy0YjmvKgHQSFbVPwQqzw4ppSYg8O9ubHyLPYi8N%2F0e4yGvEQKGug%3D%3D";

        String brResult = "";
        StringBuilder sb = new StringBuilder();

        try{
            URL url = new URL("http://apis.data.go.kr/B551177/BusInformation/getBusInfo?serviceKey="
                    + key + "&numOfRows=10&pageNo=1&area=1&type=json");


            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            BufferedReader br=
                    new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while ((brResult = br.readLine()) != null){
                sb.append(brResult);
                System.out.println(brResult);
            }
            br.close();
            con.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}

