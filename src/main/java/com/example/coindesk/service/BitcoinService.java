package com.example.coindesk.service;


import com.example.coindesk.dao.BitcoinDaoImp;
import com.example.coindesk.entity.Bitcoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BitcoinService {

        @Autowired
        BitcoinDaoImp bitcoinDaoImp;


        public Iterable<Bitcoin> getAll(){
            return bitcoinDaoImp.getAllBitcoin();
        }


        public void insertBitcoin(Bitcoin bitcoin){
            bitcoinDaoImp.insertBitcoin(bitcoin);
        }

        public boolean updateBitoin(Bitcoin bitcoin){
            return bitcoinDaoImp.updateBitcoin(bitcoin);
        }

        public boolean deleteBitcoin(String code){
            return  bitcoinDaoImp.deleteBitcoin(code);
        }

        public Optional<Bitcoin> findOne(String code){
            return bitcoinDaoImp.findByOne(code);
        }
}
