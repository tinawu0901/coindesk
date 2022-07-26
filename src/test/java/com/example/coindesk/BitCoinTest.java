package com.example.coindesk;

import com.example.coindesk.entity.Bitcoin;
import com.example.coindesk.service.BitcoinService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class BitCoinTest {
    @Autowired
    BitcoinService bitcoinService;
   private RestTemplate restTemplate;

    @Test
    public void InsetBitCoinTest(){
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setCode("USD");
        bitcoin.setSymbol("&#36;");
        bitcoin.setRate("23,844.2138");
        bitcoin.setDescription("United States Dollar");
        bitcoin.setChineseName("美元");
        bitcoin.setRate_float(23844.2138f);
        bitcoinService.insertBitcoin(bitcoin);

    }
    @Test
    public void SelectAllBitCoidTest(){
        Iterable<Bitcoin> allBitcoin = bitcoinService.getAll();
        allBitcoin.forEach(n ->  System.out.println(n));
    }
    @Test
    public void UpdateVitCoindTest(){
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setCode("USD");
        bitcoin.setSymbol("111");
        bitcoin.setRate("222333");
        bitcoin.setDescription("422454");
        bitcoin.setChineseName("美美元");
        bitcoin.setRate_float(23844.2138f);
     //   bitcoin.setUpdatedtime(new Timestamp(System.currentTimeMillis()));

        boolean usd = bitcoinService.updateBitoin(bitcoin);
        if (usd == true) {
            Optional<Bitcoin> usd1 = bitcoinService.findOne("USD");
            Bitcoin bitcoin1 = usd1.get();
            System.out.println(bitcoin1);
        }
    }
    @Test
    public void deleteBitcoinTest(){
        bitcoinService.deleteBitcoin("USD");
    }

    @Test
    public void callApi(){

        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
       // Object forObject = restTemplate.getForObject(url, Bitcoin.class);
        restTemplate = new RestTemplate();
       ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        //先轉JsonObject
      JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        JsonElement bpi = jsonObject.get("bpi");//{"USD":{"code":"USD","symbol":"&#36;","rate":"22,590.4336","description":"United States Dollar","
        Gson gson = new Gson();
        Type BitcoinType = new TypeToken<Map<String, Bitcoin>>() {}.getType();
        Map<String,Bitcoin> bitcoins = gson.fromJson(bpi,BitcoinType);
        System.out.println(bitcoins.size());
        List bitcoin = new ArrayList<Bitcoin>();
        for(Map.Entry<String,Bitcoin> entry : bitcoins.entrySet()){
            if(entry.getKey().equals("USD"))
                entry.getValue().setChineseName("美元");
            if(entry.getKey().equals("GBP"))
                entry.getValue().setChineseName("新台幣");
            if(entry.getKey().equals("EUR"))
                entry.getValue().setChineseName("歐元");
            bitcoinService.insertBitcoin(entry.getValue());
            System.err.println(entry.getKey());
            System.err.println(entry.getValue());
        }
        System.err.println("新增完成");

    }
}
