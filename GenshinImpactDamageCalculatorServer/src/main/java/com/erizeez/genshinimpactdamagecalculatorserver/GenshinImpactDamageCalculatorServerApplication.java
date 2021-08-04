package com.erizeez.genshinimpactdamagecalculatorserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/erizeez/genshinimpactdamagecalculatorserver/repository")
public class GenshinImpactDamageCalculatorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenshinImpactDamageCalculatorServerApplication.class, args);
    }

}
