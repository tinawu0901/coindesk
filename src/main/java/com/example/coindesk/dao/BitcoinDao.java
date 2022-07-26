package com.example.coindesk.dao;

import com.example.coindesk.entity.Bitcoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface   BitcoinDao extends JpaRepository<Bitcoin,String> {
}
