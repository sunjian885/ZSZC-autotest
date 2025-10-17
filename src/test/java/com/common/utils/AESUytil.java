package com.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUytil {
    // 固定AES密钥（16字节，符合AES-128要求）
    private static final String FIXED_KEY = "pigxpigxpigxpigx";
    // 加密算法全称（模式+填充必须与加密端完全一致，否则解密失败）
    private static final String AES_TRANSFORMATION = "AES/CFB/NoPadding";
    // 字符编码（统一UTF-8，避免中英文乱码）
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * AES加密：明文 → Base64编码密文
     * @param plaintext 待加密明文（如接口参数、敏感信息）
     * @return 加密后Base64格式密文（便于传输/存储，避免二进制数据丢失）
     * @throws Exception 加密异常（如算法不支持、密钥非法等）
     */
    public static String encrypt(String plaintext) throws Exception {
        // 1. 密钥转字节数组（对应CryptoJS.enc.Utf8.parse(key)）
        byte[] keyBytes = FIXED_KEY.getBytes(CHARSET);
        // 2. 初始化密钥规格（指定算法为AES）
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        // 3. 初始化IV（初始向量）：与密钥相同（延续原JS逻辑）
        IvParameterSpec iv = new IvParameterSpec(keyBytes);

        // 4. 创建加密器并初始化（加密模式）
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        // 5. 执行加密：明文→字节数组→加密字节→Base64编码（匹配CryptoJS.encrypted.toString()）
        byte[] plaintextBytes = plaintext.getBytes(CHARSET);
        byte[] encryptedBytes = cipher.doFinal(plaintextBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES解密：Base64密文 → 明文
     * @param encryptedBase64 加密后的Base64格式密文
     * @return 解密后的原始明文
     * @throws Exception 解密异常（如密文篡改、密钥不匹配等）
     */
    public static String decrypt(String encryptedBase64) throws Exception {
        // 1. 密钥和IV处理（与加密端完全一致，否则解密失败）
        byte[] keyBytes = FIXED_KEY.getBytes(CHARSET);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(keyBytes);

        // 2. 创建解密器并初始化（解密模式）
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        // 3. 执行解密：Base64解码→加密字节→解密字节→明文
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, CHARSET);
    }

    // 测试方法：验证加密→解密的一致性
    public static void main(String[] args) {
        try {
            // 待加密的测试明文（可替换为实际业务数据，如用户ID、订单信息等）
            String originalText = "Ab123456";
            System.out.println("1. 原始明文：" + originalText);

            // 执行加密
            String encryptedText = encrypt(originalText);
            System.out.println("2. 加密后（Base64）：" + encryptedText);

            // 执行解密
            String decryptedText = decrypt(encryptedText);
            System.out.println("3. 解密后明文：" + decryptedText);

            // 验证加密解密一致性
            if (originalText.equals(decryptedText)) {
                System.out.println("验证通过：加密解密结果完全一致！");
            } else {
                System.out.println("验证失败：加密解密结果不一致！");
            }
        } catch (Exception e) {
            System.err.println("加解密过程异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
