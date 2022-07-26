package com.example.coindesk.dao;

import com.example.coindesk.entity.Bitcoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Repository
public class BitcoinDaoImp {
    @Autowired
    BitcoinDao bitcoinDao;

    public Iterable<Bitcoin> getAllBitcoin(){
        return bitcoinDao.findAll();
    }

    public void insertBitcoin(Bitcoin bitcoin){
        bitcoinDao.save(bitcoin);
    }

    public Optional<Bitcoin> findByOne(String code){
        Optional<Bitcoin> bitcoin = bitcoinDao.findById(code);
        return  bitcoin;
    }
    public boolean updateBitcoin(Bitcoin bitcoin){
        Optional<Bitcoin> isExist = findByOne(bitcoin.getCode());
        if(!isExist.isPresent()){
            return false;}
        Bitcoin bitcoin1 = isExist.get();
        bitcoin1.setSymbol(bitcoin.getSymbol());
        bitcoin1.setRate(bitcoin1.getRate());
        bitcoin1.setChineseName(bitcoin.getChineseName());
        bitcoin1.setDescription(bitcoin.getDescription());
        bitcoin1.setRate_float(bitcoin.getRate_float());
        //updateTime
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String format = sdf.format(System.currentTimeMillis());
        bitcoin1.setUpdatedtime(format);
        bitcoinDao.save(bitcoin1);
        return true;
    }
    public boolean deleteBitcoin(String code){
        Optional<Bitcoin> isExist = findByOne(code);
        if(!isExist.isPresent()){
            return false;}
        bitcoinDao.deleteById(code);
        return true;
    }



}
