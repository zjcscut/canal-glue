package cn.throwx.canal.gule.example.ch0;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 23:02
 */
@SpringBootApplication
public class Client0App {

    public static void main(String[] args) {
        SpringApplication.run(Client0App.class, args);
    }

    @Data
    @CanalModel(database = "db_order_service", table = "t_order", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
    public static class OrderModel {

        private Long id;

        private String orderId;

        private OffsetDateTime createTime;

        private BigDecimal amount;
    }

    public static class OrderProcessor extends BaseCanalBinlogEventProcessor<OrderModel> {

        @Override
        protected void processInsertInternal(CanalBinLogResult<OrderModel> result) {
            OrderModel orderModel = result.getAfterData();
            logger.info("接收到订单保存binlog,主键:{},写入数据:{}", result.getPrimaryKey(), JSON.toJSONString(orderModel));
        }
    }

    @Bean
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }
}
