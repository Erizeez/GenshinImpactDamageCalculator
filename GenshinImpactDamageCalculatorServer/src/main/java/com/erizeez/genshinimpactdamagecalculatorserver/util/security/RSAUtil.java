package com.erizeez.genshinimpactdamagecalculatorserver.util.security;

import org.springframework.util.ResourceUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA使用X509EncodedKeySpec、PKCS8EncodedKeySpec生成公钥和私钥
 * 加密数据大小不能超过127 bytes
 */

public class RSAUtil {
    private final static int KEY_SIZE = 2048;

    @SuppressWarnings("static-access")
    public static void generateKeyPair(String publicKeyLoc, String privateKeyLoc) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = keyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert keyPairGenerator != null;
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyString = Base64.encode(publicKey.getEncoded());
        String privateKeyString = Base64.encode(privateKey.getEncoded());
        try {
            BufferedWriter publicBufferWriter = new BufferedWriter(new FileWriter(ResourceUtils.getFile(publicKeyLoc)));
            BufferedWriter privateBufferWriter = new BufferedWriter(new FileWriter(ResourceUtils.getFile(privateKeyLoc)));
            publicBufferWriter.write(publicKeyString);
            privateBufferWriter.write(privateKeyString);
            publicBufferWriter.flush();
            publicBufferWriter.close();
            privateBufferWriter.flush();
            privateBufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readKeyStringFromFile(File file) {
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String readLine;
            StringBuilder tempBuilder = new StringBuilder();
            while ((readLine = bufferReader.readLine()) != null) {
                tempBuilder.append(readLine);
            }
            bufferReader.close();
            return tempBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPublicKeyString(String publicKeyLoc) throws FileNotFoundException {
        return readKeyStringFromFile(ResourceUtils.getFile(publicKeyLoc));
    }

    public static String getPrivateKeyString(String privateKeyLoc) throws FileNotFoundException {
        return readKeyStringFromFile(ResourceUtils.getFile(privateKeyLoc));
    }

    public static RSAPublicKey getPublicKeyFromString(String publicKeyStr) {
        try {
            byte[] keyBytes = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RSAPrivateKey getPrivateKeyFromString(String privateKeyStr) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String signByPrivateKey(String content, String privateKeyString) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");//MD5withRSA
            signature.initSign(privateKey);
            signature.update(content.getBytes());
            byte[] sign = signature.sign();
            return Base64.encode(sign);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifySignByPublicKey(String content, String publicKeyString, String sign) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");//MD5withRSA
            signature.initVerify(publicKey);
            signature.update(content.getBytes());
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String encryptByPublicKey(String plainText, RSAPublicKey publicKey) throws Exception {
        if (publicKey == null) {
            throw new Exception("Null public key");
        }
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainText.getBytes());
            return Base64.encode(output);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptByPrivateKey(String cipherText, RSAPrivateKey privateKey) throws Exception {
        if (privateKey == null) {
            throw new Exception("Null private key");
        }
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(Base64.decode(cipherText));
            return new String(output);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        RSAUtil.generateKeyPair("classpath:keys/public.pem", "classpath:keys/private.pem");

        String publicKey = RSAUtil.getPublicKeyString("classpath:keys/public.pem");
        String privateKey = RSAUtil.getPrivateKeyString("classpath:keys/private.pem");
        System.out.println("publicKey: " + publicKey);
        System.out.println("privateKey: " + privateKey);

        System.out.println("---------------------------------------------");

        String sign = RSAUtil.signByPrivateKey("测试加签", privateKey);
        System.out.println("sign:" + sign);
        System.out.println("验签:" + RSAUtil.verifySignByPublicKey("测试加签", publicKey, sign));

        System.out.println("---------------------------------------------");

        String cipherText = RSAUtil.encryptByPublicKey("测试加密明文数据", RSAUtil.getPublicKeyFromString(publicKey));
        System.out.println("cipherText:" + cipherText);
        String plainText = RSAUtil.decryptByPrivateKey(cipherText, RSAUtil.getPrivateKeyFromString(privateKey));
        System.out.println("plainText:" + plainText);
    }
}
