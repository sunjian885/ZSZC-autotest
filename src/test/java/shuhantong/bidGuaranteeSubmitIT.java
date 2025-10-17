package shuhantong;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.HttpClientUtil;
import org.testng.annotations.Test;

import java.util.Random;

public class bidGuaranteeSubmitIT {
    static String userAuthorization = "Bearer kRoSVEaKSm4oBPh-cdT3rrAZmk78Lcew22ifqMpk5RayX5piLIcuBo3JIXFEBGV4Hh7-_ts34kDT8t7vF2uF7bS2kaUeFg4KqIkSSc65HyMuzZHuCEh5aO8koe0r36vh";
    static String tokenName = "authorization";

    @Test(description = "创建投标保函-不过风控")
    public void bidGuaranteeSubmitTest01(){

        Random random = new Random();
        int randomNum = random.nextInt(99999999) ;
        // 项目名称
        String projectName = "投标autoTest"+randomNum;
        // 被保证人
        String guarantor = "武汉长江新媒体有限公司";
        // 受益人企业
        String beneficiary = "中数智创科技有限公司";
        //受益人出函名称
        String beneficiaryLetterName = "中数智创科技有限公司";

        String postUrl = "http://192.168.4.43/api/order/addOrder/bidGuaranteeSubmit";
        String bodyData = "{\"fn\":1,\"salesmanName\":\"张开轩\",\"userSalesmanId\":\"1821062541745356801\",\"templateType\":2,\"guaranteeFormatName\":\"一般保证\",\"guaranteeFormats\":\"1\",\"agencyType\":2,\"openMode\":1,\"sendLetterName\":\"富桥建行\",\"sendLetterIds\":\"1811226921111945217\",\"branchId\":\"\",\"guarantor\":\""+guarantor+"\",\"beneficiary\":\""+beneficiary+"\",\"beneficiaryLetterName\":\""+beneficiaryLetterName+"\",\"projectName\":\""+projectName+"\",\"guaranteeAmount\":2000000,\"guaranteeTermType\":1,\"makeGuaranteeSure\":0,\"guaranteeCount\":\"1\",\"documenter\":\"冯琼\",\"userDocumenterId\":\"1821069838211121154\",\"isFkAudit\":0,\"isSheWai\":0,\"guarantorCode\":\"91420102578252263N\",\"bankGuarantorLegalPerson\":\"杨文平\",\"guarantorIsEnterprise\":1,\"beneficiaryCode\":\"91130108MA0CHUUJ90\",\"beneficiaryLegalPerson\":\"张世栋\",\"beneficiaryIsEnterprise\":1,\"provinceCode\":\"370000\",\"cityCode\":\"370700\",\"province\":\"山东省\",\"city\":\"潍坊市\",\"salesmanDeptId\":\"1793822566108921858\",\"ifBroker\":0,\"userDocumenterDeptId\":\"1793823254327099393\",\"guaranteeAmountDx\":\"贰佰万元整\",\"busAppendixs\":[{\"fileName\":\"23.png\",\"sysFileId\":\"1968188388406226946\",\"fileHash\":\"ca6e8a57b0f1dd52b47a9683024ba47f\",\"appendixClass\":12},{\"fileName\":\"IMG_1761.HEIC.JPG.JPG\",\"sysFileId\":\"1968188414486409218\",\"fileHash\":\"280b7003d2fa378ab1834c682e1504e2\",\"appendixClass\":1}],\"guaranteeType\":1,\"guaranteeTermValue\":\"2025-09-25\"}";
        try {
            JSONObject getResult = HttpClientUtil.sendPostBodyToken(postUrl,bodyData,userAuthorization,tokenName);
            System.out.println("============"+getResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test(description = "创建投标保函-过风控审核")
    public void bidGuaranteeSubmitTest02(){

        Random random = new Random();
        int randomNum = random.nextInt(99999999) ;
        // 项目名称
        String projectName = "投标autoTest"+randomNum;
        // 被保证人
        String guarantor = "武汉长江新媒体有限公司";
        // 受益人企业
        String beneficiary = "中数智创科技有限公司";
        //受益人出函名称
        String beneficiaryLetterName = "中数智创科技有限公司";

        String postUrl = "http://192.168.4.43/api/order/addOrder/bidGuaranteeSubmit";
        String bodyData = "{\"fn\":1,\"salesmanName\":\"张开轩\",\"userSalesmanId\":\"1821062541745356801\",\"templateType\":2,\"guaranteeFormatName\":\"一般保证\",\"guaranteeFormats\":\"1\",\"agencyType\":2,\"openMode\":1,\"sendLetterName\":\"富桥建行\",\"sendLetterIds\":\"1811226921111945217\",\"branchId\":\"\",\"guarantor\":\""+guarantor+"\",\"beneficiary\":\""+beneficiary+"\",\"beneficiaryLetterName\":\""+beneficiaryLetterName+"\",\"projectName\":\""+projectName+"\",\"guaranteeAmount\":2000000,\"guaranteeTermType\":1,\"makeGuaranteeSure\":0,\"guaranteeCount\":\"1\",\"documenter\":\"冯琼\",\"userDocumenterId\":\"1821069838211121154\",\"isFkAudit\":0,\"isSheWai\":0,\"guarantorCode\":\"91420102578252263N\",\"bankGuarantorLegalPerson\":\"杨文平\",\"guarantorIsEnterprise\":1,\"beneficiaryCode\":\"91130108MA0CHUUJ90\",\"beneficiaryLegalPerson\":\"张世栋\",\"beneficiaryIsEnterprise\":1,\"provinceCode\":\"370000\",\"cityCode\":\"370700\",\"province\":\"山东省\",\"city\":\"潍坊市\",\"salesmanDeptId\":\"1793822566108921858\",\"ifBroker\":0,\"userDocumenterDeptId\":\"1793823254327099393\",\"guaranteeAmountDx\":\"贰佰万元整\",\"busAppendixs\":[{\"fileName\":\"23.png\",\"sysFileId\":\"1968188388406226946\",\"fileHash\":\"ca6e8a57b0f1dd52b47a9683024ba47f\",\"appendixClass\":12},{\"fileName\":\"IMG_1761.HEIC.JPG.JPG\",\"sysFileId\":\"1968188414486409218\",\"fileHash\":\"280b7003d2fa378ab1834c682e1504e2\",\"appendixClass\":1}],\"guaranteeType\":1,\"guaranteeTermValue\":\"2025-09-25\",\"isFkAudit\":\"1\"}";
        try {
            JSONObject getResult = HttpClientUtil.sendPostBodyToken(postUrl,bodyData,userAuthorization,tokenName);
            System.out.println("============"+getResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
