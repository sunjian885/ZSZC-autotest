package com.test.zszc.modelPlatform;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.HttpClientUtil;
import org.testng.annotations.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * 增加企业监控
 */
public class CompanyUpdateTest {

    public static String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAry4hsqqi/MemcJ4UDxZLGXU+0POnOpLTzJUldQmJVVB+H5hUwfr5YqgRnqxIxSK9wLoH0RGU5xmwtSWuBVxBCIoKKIqCTYRxza8CBcleTj//WhjsCo7dyDuq8AY/65Xxu3OM4omgh6PtLKXI6cDp6iM5+RakuB1pIqq8RlNAJRbDj7KgV7GM7KgeK/DfGZmX//8h6W95/NrZhAv1i4xWATW+hl7ZeMguhArB5KDnDCe/XcNOA16FApvGLkkqIgOK+ZB3+fP38hu63JgNU+7bDdpyl20Ta9Rb+EYlTvZl7wLx7m9OnRFmEpvmcNhr6x/OIfdE6bIhYxgbuac4vQ5aqQIDAQAB";
    public static String bodyData = "{\n" +
            "    \"userId\": \"MXPT\",\n" +
            "    \"companies\": [\n" +
            "        {\n" +
            "            \"companyName\": \"中铁十八局集团有限公司\",\n" +
            "            \"enabled\": 1,\n" +
            "            \"startMonitorTime\": 1760518389000,\n" +
            "            \"endMonitorTime\": 1761814389000,\n" +
            "            \"creditCode\": \"9112000010306009X2\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"companyName\": \"中国供销集团有限公司\",\n" +
            "            \"enabled\": 1,\n" +
            "            \"startMonitorTime\": 1760518389000,\n" +
            "            \"endMonitorTime\": 1761814389000,\n" +
            "            \"creditCode\": \"91100000717826328J\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    public static String url = "http://192.168.21.211:31020/data/api/v2/risk/company/update";
    public static String appId = "api418637489251";

    @Test
    public void test() throws Exception{

        // 使用公钥加密请求数据
        String encryptData = encrypt(bodyData, publicKeyBase64);
        TreeMap<String, Object> requestMap = new TreeMap<>();
        requestMap.put("appId", appId);
        requestMap.put("recordId", System.currentTimeMillis());
        requestMap.put("encryptData", encryptData);

        // 数据签名
        String sign = signByMd5(requestMap);
        requestMap.put("sign", sign);
        System.out.println("加密数据: " + encryptData);
        System.out.println("请求签名: " + sign);
        System.out.println("请求报文: " + JSONUtil.toJsonPrettyStr(requestMap));


        JSONObject result  = HttpClientUtil.sendPostBodyToken(url,JSONUtil.toJsonPrettyStr(requestMap),"");
        System.out.println(">>>>>>>>"+result);
    }



    public static String encrypt(String bodyData, String publicKeyBase64) throws Exception {

        //创建密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //使用密钥工厂创建公钥对象
        byte[] publicKeyBytes = getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(spec);

        // 随机生成AES密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // 使用生成的AES密钥, 对数据进行对称加密
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = aesCipher.doFinal(bodyData.getBytes(StandardCharsets.UTF_8));

        // 使用传入的RSA公钥
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 对刚才随机生成的AES密钥, 进行非对称加密
        byte[] encryptedKey = rsaCipher.doFinal(secretKey.getEncoded());

        // 将非对称加密的秘钥数据, 与对称加密的业务数据组合返回
        return getEncoder().encodeToString(encryptedKey) + ";" + getEncoder().encodeToString(encryptedData);
    }

    public static String signByMd5(TreeMap<String, Object> paramsMap) {
        // 拼接签名要素
        StringBuilder sb = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            sb.append(key).append("=").append(paramsMap.get(key)).append("&");
        }
        String paramsStr = sb.toString();
        return SecureUtil.md5(paramsStr);
    }
}
