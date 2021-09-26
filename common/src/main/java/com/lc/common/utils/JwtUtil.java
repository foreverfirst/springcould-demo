package com.lc.common.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: JwtHelper
 * @Author: zhaoxinguo
 * @Date: 2018/12/10 19:39
 * @Description: 实现Jwt
 */

public class JwtUtil {

    /**
     * 解析token
     * @param jsonWebToken
     * @return
     */
    public static Claims parseToken(String jsonWebToken, String base64Security) throws Exception {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(apiKeySecretBytes)) ;

        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jsonWebToken)
                .getBody();
        return claims;
    }

    /**
     * 新建token
     *
     * @return
     */
    public static String createToken(Map<String, Object> claims, String secret, long expiration) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        KeyFactory factory = KeyFactory.getInstance("RSA");

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        PrivateKey privateKey = factory.generatePrivate(new PKCS8EncodedKeySpec(apiKeySecretBytes)) ;


        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(signatureAlgorithm, privateKey);

        builder.setClaims(claims);
        // 添加Token签发时间
        builder.setIssuedAt(now);
        // 添加Token过期时间
        if (expiration >= 0) {
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成JWT
        return builder.compact();
    }

    /**
     * 刷新令牌
     *
     * @param claims
     * @return
     */
    public static String refreshToken(Claims claims, String base64Security, long expiration) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuer((String) claims.get("iss")).setAudience((String) claims.get("aud"))
                .signWith(signatureAlgorithm, signingKey);

        // 添加Token签发时间
        builder.setIssuedAt(now);
        // 添加Token过期时间
        if (expiration >= 0) {
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成Token
        return builder.compact();
    }
}
