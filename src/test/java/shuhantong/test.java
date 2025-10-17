package shuhantong;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.ConfigProperty;
import com.common.utils.HttpClientUtil;
import com.common.utils.RSAUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class test {
    // 常量定义，对应前端环境变量
    private static final String BASE_URL = "http://192.168.4.43/api"; // 替换为实际基础URL
    private static final String OAUTH2_PASSWORD_CLIENT = "pig:pig"; // 替换为实际值
    private static final String PWD_ENC_KEY = "pigxpigxpigxpigx"; // 密码加密密钥
    private static final String FORM_CONTENT_TYPE = "application/json;charset=UTF-8";

    public static void main(String[] args) {
        try {
            // 准备登录数据
            Map<String, String> loginData = new HashMap<>();
            loginData.put("username", "admin");
            loginData.put("password", "Ab123456");
            loginData.put("randomStr", "blockPuzzle");
            loginData.put("code", "KCH05aFbAZk6jrBWj64BNdIET6FWkVKwdn3kJDjDk%2FkK26CCB0koAk6nKF%2BJmTa0Vg3hEshglaNW8rpeI3nvG8TkmSTYmmXzyOkOcZJ70Mg%3D");
            loginData.put("grant_type", "password");
            loginData.put("scope", "server");
            loginData.put("","000000");

            // 执行登录
            String result = login(loginData);
            System.out.println(">>>>>>"+result);
            System.out.println("登录响应: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录方法
     */
    public static String login(Map<String, String> data) throws Exception {

        // 1. 生成Basic Auth
        String basicAuth = "Basic " + base64Encode(OAUTH2_PASSWORD_CLIENT.getBytes(StandardCharsets.UTF_8));

        // 2. 密码加密
        String encPassword = encryption(data.get("password"), PWD_ENC_KEY);

        // 3. 创建HTTP客户端
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 4. 构建请求URL和参数
            URIBuilder uriBuilder = new URIBuilder(BASE_URL + "/auth/oauth2/token");
            // 添加查询参数
            uriBuilder.addParameter("username", data.get("username"));
            uriBuilder.addParameter("randomStr", data.get("randomStr"));
            uriBuilder.addParameter("code", data.get("code"));
            uriBuilder.addParameter("grant_type", data.get("grant_type"));
            uriBuilder.addParameter("scope", data.get("scope"));

            URI uri = uriBuilder.build();
            HttpPost httpPost = new HttpPost(uri);

            // 5. 设置请求头
            httpPost.setHeader("skipToken", "true");
            httpPost.setHeader("Authorization", basicAuth);
            httpPost.setHeader("Content-Type", FORM_CONTENT_TYPE);

            // 6. 设置请求体数据
            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair("password", encPassword));
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));

            // 7. 发送请求并获取响应
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
                return null;
            }
        }
    }

    /**
     * 密码加密，与前端encryption方法对应
     */
    public static String encryption(String src, String keyWord) throws Exception {
        byte[] keyBytes = keyWord.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(keyBytes);

        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encrypted = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Base64编码，对应前端window.btoa
     */
    private static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

}
