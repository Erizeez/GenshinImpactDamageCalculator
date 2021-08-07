package com.erizeez.genshinimpactdamagecalculatorserver;

import com.erizeez.genshinimpactdamagecalculatorserver.util.security.RSAUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/erizeez/genshinimpactdamagecalculatorserver/repository")
public class GenshinImpactDamageCalculatorServerApplication {

    public static void main(String[] args) {
        RSAUtil.generateKeyPair("classpath:keys/public.pem", "classpath:keys/private.pem");
        SpringApplication.run(GenshinImpactDamageCalculatorServerApplication.class, args);
    }

}
