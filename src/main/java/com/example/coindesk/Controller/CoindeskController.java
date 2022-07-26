package com.example.coindesk.Controller;

import com.example.coindesk.entity.Bitcoin;
import com.example.coindesk.service.BitcoinService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coinapi")
@Api(tags = "Coin相關Api")
public class CoindeskController {


    private   RestTemplate restTemplate;
    private  static final  String url = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private Gson gson;
    @Autowired
    BitcoinService bitcoinService;

    @ApiOperation("取得CoindeskAPI所有幣別")
    @GetMapping("/coindesk")
    public ResponseEntity callCoindeskAPI(){
        String coinapi = callGetCoin();
        return ResponseEntity.status(HttpStatus.OK).body(coinapi);
    }

    @ApiOperation("轉換CoindeskAPI所有幣別")
    @GetMapping("/coinTransfer")
    public ResponseEntity calltransferAPI(){
        String body = callGetCoin();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        JsonElement bpi = jsonObject.get("bpi");
        Gson gson = new Gson();
        Type BitcoinType = new TypeToken<Map<String, Bitcoin>>() {}.getType();
        Map<String,Bitcoin> bitcoins = gson.fromJson(bpi,BitcoinType);

        for(Map.Entry<String,Bitcoin> entry : bitcoins.entrySet()){
            if(entry.getKey().equals("USD")){
                entry.getValue().setChineseName("美元");}
            if(entry.getKey().equals("GBP")){
                entry.getValue().setChineseName("新台幣");}
            if(entry.getKey().equals("EUR")){
                entry.getValue().setChineseName("歐元");}
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String format = sdf.format(System.currentTimeMillis());
            entry.getValue().setUpdatedtime(format);
           // bitcoinService.insertBitcoin(entry.getValue());
        }
        String s = gson.toJson(bitcoins);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    private String   callGetCoin(){
        restTemplate =new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        return gson.toJson(jsonObject);
    }
}
