package com.nky.sdk_flutter.nkysdk.protocal.src.main.java.com.nky.protocal.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-CBC数据加解密工具类
 */
public class AESCBCUtil {

    private static final String SECRET_KEY = "sxd_aiot_key_001";//
    private static final String iv = "sxd_aiot_2022_01";//偏移量字符串16位 当模式是CBC的时候必须设置偏移量
    private static final String Algorithm = "AES";
    private static final String AlgorithmProvider = "AES/CBC/NoPadding"; //算法/模式/补码方式

    /**
     * 加密
     */
    public static byte[] encode(byte[] content) {
        byte[] bytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return encode(content, bytes);
    }

    /**
     * 解密
     */
    public static byte[] decode(byte[] content) {
        byte[] bytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return decode(content, bytes);
    }

    private static byte[] encode(byte[] src, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, Algorithm);
            IvParameterSpec ivParameterSpec = getIv();
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return cipher.doFinal(src);
        } catch (Exception e) {
            return null;
        }

    }

    private static byte[] decode(byte[] src, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, Algorithm);
            IvParameterSpec ivParameterSpec = getIv();
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return cipher.doFinal(src);
        } catch (Exception e) {
            return null;
        }
    }

    private static IvParameterSpec getIv() {
        return new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

}
