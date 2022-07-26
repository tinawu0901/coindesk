package com.example.coindesk.Controller;

import com.example.coindesk.entity.Bitcoin;
import com.example.coindesk.service.BitcoinService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


//一開始會用@RestController Annotation來直接回傳json，並用@RequestMapping("/api") 定義此API的父路
@Api(tags = "BitCoin資料表相關Api")
@RestController
@RequestMapping("/api")
public class BitCoinController {

    @Autowired
    BitcoinService bitcoinService;

    @Autowired
    Gson gson;


    @ApiOperation("取得所有幣別資料庫之幣別")
    @GetMapping("/coin")
    public ResponseEntity getllCoin(){
        Iterable<Bitcoin> allBitcoin = bitcoinService.getAll();
        String s = gson.toJson(allBitcoin);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @ApiOperation("新增幣別")
    @ApiResponses({
            @ApiResponse(code=401,message="沒有權限"),
            @ApiResponse(code=400,message="傳送格式錯誤")
    })
    @PostMapping("/coin")
    public ResponseEntity insetCoin(@Valid @RequestBody  Bitcoin bitcoin, BindingResult result){
        if(result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List errMessage = new ArrayList();
            allErrors.forEach((n)->errMessage.add(n.getDefaultMessage()));
            String s = gson.toJson(errMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(s);
        }
        bitcoinService.insertBitcoin(bitcoin);
        return ResponseEntity.status(HttpStatus.OK).body("sucessful");
    }

    @ApiOperation("編輯幣別")
    @ApiResponses({
            @ApiResponse(code=401,message="沒有權限"),
            @ApiResponse(code=400,message="傳送格式錯誤"),
            @ApiResponse(code=424,message="編輯失敗")
    })
    @PutMapping("/coin/{code}")
    public ResponseEntity updateCoin(@PathVariable String code,@Valid@RequestBody Bitcoin bitcoin,BindingResult result){

        if(result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List errMessage = new ArrayList();
            allErrors.forEach((n)->errMessage.add(n.getDefaultMessage()));
            String s = gson.toJson(errMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(s);
        }
        boolean b = bitcoinService.updateBitoin(bitcoin);
        if(b != true) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("sucessful");
    }

    @ApiOperation("刪除幣別")
    @ApiResponses({
            @ApiResponse(code=401,message="沒有權限"),
            @ApiResponse(code=424,message="刪除失敗")
    })
    @DeleteMapping("/coin/{code}")
    public ResponseEntity deleteCoin(@PathVariable String code){
        boolean b = bitcoinService.deleteBitcoin(code);
        if(b != true) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("failed");
        }
        return    ResponseEntity.status(HttpStatus.OK).body("sucessful");
    }





}
