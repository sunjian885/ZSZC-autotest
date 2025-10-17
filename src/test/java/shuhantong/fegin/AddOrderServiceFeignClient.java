package shuhantong.fegin;

import com.zszc.finance.gltl.order.api.dto.BusAddBidOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

// 配置服务端地址（非注册中心场景直接指定URL）
@FeignClient(
        name = "gltl-order",  // 服务名称（必填，仅作为标识）
        url = "http://192.168.4.43:8080"  // 服务端实际部署地址
)
public interface AddOrderServiceFeignClient {

    // 对应服务端的创建接口
    @PostMapping("/addOrder/perGuaranteeSubmit")
    boolean perGuaranteeSubmit(BusAddBidOrderDTO busAddBidOrderDTO);

}
