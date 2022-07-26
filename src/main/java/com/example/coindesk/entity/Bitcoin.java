package com.example.coindesk.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@ToString
public class Bitcoin {


    @NotEmpty(message = "幣別不得為空")
    @Id
    private String code;

    @NotEmpty(message = "幣別符號不得為空")
    private String symbol;
    @NotEmpty(message = "匯率不得為空白")
    private String rate;
    @NotEmpty(message = "說明不得為空")
    private String description;

    @NotNull(message = "匯率不得為空")
    private float rate_float;


    @NotNull(message = "幣別名稱不得為空")
    private String chineseName;


    @Column(name="updatedtime")
    private String updatedtime ;

}
