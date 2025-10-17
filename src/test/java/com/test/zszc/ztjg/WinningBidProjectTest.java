package com.test.zszc.ztjg;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.ConfigProperty;
import com.common.utils.ExcelDataUtil;
import com.common.utils.HttpClientUtil;
import com.common.utils.RSAUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URLEncoder;
import java.util.HashMap;

public class WinningBidProjectTest {
    static String userAuthorization;
    static String tokenName = "authorization";
    @BeforeTest
    public void getLoginToken() throws Exception{
        String userName = ConfigProperty.get("ZTJGUserName");
        String password = ConfigProperty.get("ZTJGPassword");
        String encryptPassword = RSAUtil.encrypt(password);
        //中通建工登录-招标人
        String loginUrl = "http://website.ztzc-test.zszc.jianshicha.cn/api/etbuser/ztkLogin";
        String bodyData = "{\"username\":\""+userName+"\",\"password\":\""+encryptPassword+"\",\"identity\":\"1\"}";
        JSONObject result  = HttpClientUtil.sendPostBodyToken(loginUrl,bodyData,"");
        userAuthorization = "Bearer "+JSONObject.parseObject(result.get("data").toString()).get("access_token").toString();
    }

    @DataProvider(name = "testExcelData")
    public Object[][] data() throws Exception{
        String pathFile = "target/test-classes/data/testData.xlsx";
        ExcelDataUtil testcase = new ExcelDataUtil();
        return testcase.testData(pathFile);
    }
    @Test(dataProvider = "testExcelData",description = "根据项目名称、审核状态，搜索中标项目")
    public void
    WinningBidProjectTest01(HashMap<String, String> data) throws Exception{
        String projectNameEncode = URLEncoder.encode(data.get("name"), "UTF-8");
        String url = "http://trade.ztzc-test.zszc.jianshicha.cn/api/etbtrade-source/winningBidProject/page?size=10&current=1&projectName="+projectNameEncode+"&auditStatus=";
        JSONObject getResult = HttpClientUtil.sendGetPairToken(url,userAuthorization,tokenName);
        System.out.println(">>>>>>>>>=getResult "+getResult);
        //TODO 查询数据断言
    }

}
