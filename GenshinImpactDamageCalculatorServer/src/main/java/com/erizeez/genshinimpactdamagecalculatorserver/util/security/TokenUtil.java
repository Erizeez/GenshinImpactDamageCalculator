package com.erizeez.genshinimpactdamagecalculatorserver.util.security;


import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import com.erizeez.genshinimpactdamagecalculatorserver.service.UserService;
import org.apache.commons.io.FileUtils;
import org.jose4j.base64url.Base64;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Component
public class TokenUtil {
    private static UserService userService;
    private static final String privateKeyLoc = "classpath:keys/private.pem";
    private static final String publicKeyLoc = "classpath:keys/public.pem";
    private static final String issuer = "org.erizeez";

    // 过期时间: 12h
    public static final int EXPIRE_TIME_MIN = 12 * 60;

    @Autowired
    public void setUserService(UserService userService) {
        TokenUtil.userService = userService;
    }

    public static String getPrivateKeyString() throws IOException {
        File file = ResourceUtils.getFile(privateKeyLoc);
        return FileUtils.readFileToString(file, "utf-8");
    }

    public static String getPublicKeyString() throws IOException {
        File file = ResourceUtils.getFile(publicKeyLoc);
        return FileUtils.readFileToString(file, "utf-8");
    }

    private static PublicKey getPublicKey(String publicKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pem = publicKeyBase64
                .replaceAll("-*BEGIN.*KEY-*", "")
                .replaceAll("-*END.*KEY-*", "");
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(pem));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(pubKeySpec);
    }

    private static PrivateKey getPrivateKey(String privateKeyData) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyData));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String makeToken(User user) throws IOException {
        PrivateKey privateKey = getPrivateKey(getPrivateKeyString());
        JwtClaims claims = new JwtClaims();
        // issuer
        claims.setIssuer(issuer);
        // sign at
        claims.setIssuedAtToNow();
        // expiration time
        claims.setExpirationTimeMinutesInTheFuture(EXPIRE_TIME_MIN);
        claims.setClaim("uID", user.getuID().toString());
        claims.setClaim("userName", user.getUserName());
        claims.setClaim("nickName", user.getNickName());
        claims.setClaim("loginTime", user.getLoginTime().toString().hashCode());
        claims.setNotBefore(NumericDate.now());

        // Generate the payload
        JsonWebSignature jws = new JsonWebSignature();
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setPayload(claims.toJson());
        jws.setKeyIdHeaderValue(UUID.randomUUID().toString());

        // Sign using the private key
        jws.setKey(privateKey);
        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyToken(String token) {
        try {
            PublicKey publicKey = getPublicKey(getPublicKeyString());

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setExpectedIssuer(issuer)
                    .setRequireExpirationTime()
                    .setMaxFutureValidityInMinutes(EXPIRE_TIME_MIN)
                    .setRequireIssuedAt()
                    .setVerificationKey(publicKey)
                    .build();
            JwtClaims claims = jwtConsumer.processToClaims(token);
            User nowUser = userService.selectUserByUID(
                    Integer.parseInt(claims.getClaimValue("uID").toString()));
            return nowUser.getLoginTime().toString().hashCode() == Integer.parseInt(claims.getClaimValue("loginTime")
                    .toString());
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<String, Object> getHeader(String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            PublicKey publicKey = getPublicKey(getPublicKeyString());

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setExpectedIssuer(issuer)
                    .setRequireExpirationTime()
                    .setMaxFutureValidityInMinutes(EXPIRE_TIME_MIN)
                    .setRequireIssuedAt()
                    .setVerificationKey(publicKey)
                    .build();
            JwtClaims claims = jwtConsumer.processToClaims(token);
            User nowUser = userService.selectUserByUID(
                    Integer.parseInt(claims.getClaimValue("uID").toString()));
            if (nowUser.getLoginTime().toString().hashCode() !=
                    Integer.parseInt(claims.getClaimValue("loginTime")
                            .toString())) {
                return new HashMap<>();
            }
            map.put("uID", Integer.parseInt(claims.getClaimValue("uID").toString()));
            map.put("userName", claims.getClaimValue("userName").toString());
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
