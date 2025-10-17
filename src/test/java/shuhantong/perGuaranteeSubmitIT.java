package shuhantong;

import com.alibaba.fastjson.JSONObject;
import com.common.database.DBInfo;
import com.common.database.DBOperate;
import com.common.utils.HttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class perGuaranteeSubmitIT {
    static String userAuthorization = "Bearer B84yZI6cxscBPPwHxILwJuBn9KlvLKDOk7eqKwbq4R2WfcM7s6NDERfAyTWNnecjLLuJwhKUmQ8mbs6ZUTmbsaxc-TnHSotLYmw-npNc8buTmuyUSvG35xeP_6h50uJF";
    static String tokenName = "authorization";

    @Test(description = "创建履约保函-过风控")
    public void perGuaranteeSubmitTest01(){

        Random random = new Random();
        int randomNum = random.nextInt(99999999) ;
        // 项目名称
        String projectName = "履约autoTest"+randomNum;
        // 被保证人
        String guarantor = "中数智创科技有限公司";
        // 受益人企业
        String beneficiary = "河北兆智科技有限公司";
        //受益人出函名称
        String beneficiaryLetterName = "河北兆智科技有限公司";


        String postUrl = "http://192.168.4.43/api/order/addOrder/perGuaranteeSubmit";
        String bodyData = "{\"salesmanName\":\"段云涛\",\"userSalesmanId\":\"1821065770222780418\",\"openMode\":1,\"earnestMoneyType\":0,\"guarantor\":\""+guarantor+"\",\"beneficiary\":\""+beneficiary+"\",\"beneficiaryLetterName\":\""+beneficiaryLetterName+"\",\"projectName\":\""+projectName+"\",\"isSheWai\":0,\"guarantorCode\":\"91130108MA0CHUUJ90\",\"bankGuarantorLegalPerson\":\"张世栋\",\"guarantorIsEnterprise\":1,\"beneficiaryCode\":\"91130100MA07RKJ06L\",\"beneficiaryLegalPerson\":\"甄金龙\",\"beneficiaryIsEnterprise\":1,\"salesmanDeptId\":\"1793822566108921858\",\"ifBroker\":0,\"busAppendixs\":[{\"fileName\":\"23.png\",\"sysFileId\":\"1968154069042229249\",\"fileHash\":\"ca6e8a57b0f1dd52b47a9683024ba47f\",\"appendixClass\":1}],\"list\":[{\"annexFile2\":null,\"guaranteeType\":2,\"lineBankName\":\"中山东路\",\"branchId\":\"1811224637518938113\",\"bankNameTran\":\"\",\"isAgainUploadLetterTemplate\":false,\"guaranteeFormats\":\"1\",\"guaranteeFormatName\":\"一般保证\",\"guaranteeAmount\":2000000,\"guaranteeAmountDx\":\"贰佰万元整\",\"guaranteeTermType\":1,\"guaranteeTermValue\":\"2025-09-18\",\"agencyType\":2,\"sendLetterIds\":\"1811226921111945217\",\"sendLetterName\":\"富桥建行\",\"templateType\":2,\"templateId\":\"\",\"busAppendixs\":[{\"fileName\":\"23.png\",\"sysFileId\":\"1968154069042229249\",\"fileHash\":\"ca6e8a57b0f1dd52b47a9683024ba47f\",\"appendixClass\":1}]}],\"contractCurrency\":\"人民币\"}";

        try {
            JSONObject getResult = HttpClientUtil.sendPostBodyToken(postUrl,bodyData,userAuthorization,tokenName);
            System.out.println("============"+getResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //TODO:断言。连接数据库，比对数据
        //配置文件中对应的数据库链接
        String databaseName = "database.url.shu_han_tong";
        //查询语句
        String selectSql = "select * from gltl_guarantee.bus_order_info where project_name = '"+projectName+"';";

        System.out.println(">>>>>>>>>>>"+databaseName);
        System.out.println(">>>>>>>>>>>"+selectSql);
        //断言
        Assert.assertEquals(dbCheck(databaseName,selectSql).size(),1);
        Assert.assertTrue(true);
    }

    public List<Map<String, Object>> dbCheck(String databaseName, String selectSql){
        //数据库查询
        List<Map<String, Object>> selectResult = null;
        try {
            String conn = DBInfo.getConn(databaseName);
            selectResult = DBOperate.select(conn,selectSql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return selectResult;
    }

}
