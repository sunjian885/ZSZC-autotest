package shuhantong.fegin;

import com.zszc.finance.gltl.order.api.dto.BusAddBidOrderDTO;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zszc.finance.gltl.order.api.enums.GuaranteeAgencyTypeEnum.COMPANY_GUARANTEE;

// 1. 定义Feign客户端接口（映射被测服务的HTTP接口）
@FeignClient(
        name = "gltl-order",
        url = "http://192.168.4.43:8080"  // 被测服务的实际地址
)
interface OrderServiceFeignClient {

    @PostMapping("/addOrder/perGuaranteeSubmit")
    boolean perGuaranteeSubmit(@RequestBody BusAddBidOrderDTO busAddBidOrderDTO);
}

// 2. TestNG测试类
@SpringBootTest()
public class bidTest {

    @Autowired
    private OrderServiceFeignClient orderClient;

    @Test
    public void test(){
        BusAddBidOrderDTO busAddBidOrderDTO = new BusAddBidOrderDTO();
        busAddBidOrderDTO.setAgencyType(COMPANY_GUARANTEE);
        busAddBidOrderDTO.setGuarantor("中数智创科技");

        System.out.println(">>>>>>>>"+orderClient.perGuaranteeSubmit(busAddBidOrderDTO));


    }

}


