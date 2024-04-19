package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class SignService {
    public void getKcpSign() throws Exception {
        PrivateKey priKey = loadSplMctPrivateKeyPKCS8("../splPrikeyPKCS8.pem", "changeit"); // (개인키 경로, 개인키 비밀번호)
        String signData = makeSignatureData(priKey, "T0000^20210719000000^PACA");    // 서명데이터 생성
        System.out.println("\n[서명데이터(kcp_sign_data)] : " + signData);
    }

    public static PrivateKey loadSplMctPrivateKeyPKCS8(String filePath, String privateKeyPassword) throws IOException, OperatorCreationException, PKCSException {
        PrivateKey priKey = null;
        Path path = Paths.get(filePath);
        String strPriKeyData = Files.readAllLines(path)
                .stream()
                .filter(line -> !line.startsWith("-----BEGIN") && !line.startsWith("-----END"))
                .collect(Collectors.joining());

        byte[] btArrPriKey = Base64.getDecoder().decode(strPriKeyData);

        ASN1Sequence derSeq = ASN1Sequence.getInstance(btArrPriKey);
        PKCS8EncryptedPrivateKeyInfo encPkcs8PriKeyInfo = new PKCS8EncryptedPrivateKeyInfo(org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(derSeq));
        JcaPEMKeyConverter pemKeyConverter = new JcaPEMKeyConverter();
        InputDecryptorProvider decProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder().build(privateKeyPassword.toCharArray());
        PrivateKeyInfo priKeyInfo = encPkcs8PriKeyInfo.decryptPrivateKeyInfo(decProvider);
        priKey = pemKeyConverter.getPrivateKey(priKeyInfo);

        return priKey;
    }

    public static String makeSignatureData(PrivateKey priKey, String targetData) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        String signData = null;
        byte[] btArrTargetData = targetData.getBytes(StandardCharsets.UTF_8);

        Signature sign = Signature.getInstance("SHA256WithRSA");
        sign.initSign(priKey);
        sign.update(btArrTargetData);

        byte[] btArrSignData = sign.sign();
        signData = Base64.getEncoder().encodeToString(btArrSignData);

        return signData;
    }
}
