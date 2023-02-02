package com.example.propertyencryptionwithjasypt.service;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JasyptService {
    private final StringEncryptor stringEncryptor;

    public String decryptText(String text, String password){
        if (!password.equals("")){
            var customEncryptor = getCustomEncryptor(password);
            return customEncryptor.decrypt(text);
        }

        return stringEncryptor.decrypt(text);
    }

    public String encryptText(String text, String password){
        if (!password.equals("")){
            var customEncryptor = getCustomEncryptor(password);
            return new StringBuilder().append("ENC(").append(customEncryptor.encrypt(text)).append(")").toString();
        }

        return new StringBuilder().append("ENC(").append(stringEncryptor.encrypt(text)).append(")").toString();
    }

    private PooledPBEStringEncryptor getCustomEncryptor(String password) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
