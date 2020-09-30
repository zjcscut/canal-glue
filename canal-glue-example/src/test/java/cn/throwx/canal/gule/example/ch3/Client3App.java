package cn.throwx.canal.gule.example.ch3;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootApplication
public class Client3App {

    public static void main(String[] args) {
        SpringApplication.run(Client3App.class, args);
    }

    @Data
    @CanalModel(database = "db_order_service", table = "t_order", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
    public static class OrderModel {

        private Long id;

        private String orderId;

        private OffsetDateTime createTime;

        private BigDecimal amount;
    }

    public static class FirstOrderProcessor extends BaseCanalBinlogEventProcessor<OrderModel> {

        @Override
        protected void processInsertInternal(CanalBinLogResult<OrderModel> result) {
            OrderModel orderModel = result.getAfterData();
            logger.info("FirstOrderProcessor接收到订单保存binlog,主键:{},写入数据:{}", result.getPrimaryKey(), JSON.toJSONString(orderModel));
        }
    }

    public static class SecondOrderProcessor extends BaseCanalBinlogEventProcessor<OrderModel> {

        @Override
        protected void processInsertInternal(CanalBinLogResult<OrderModel> result) {
            OrderModel orderModel = result.getAfterData();
            logger.info("SecondOrderProcessor接收到订单保存binlog,主键:{},写入数据:{}", result.getPrimaryKey(), JSON.toJSONString(orderModel));
        }
    }

    @Bean
    public FirstOrderProcessor firstOrderProcessor() {
        return new FirstOrderProcessor();
    }

    @Bean
    public SecondOrderProcessor secondOrderProcessor() {
        return new SecondOrderProcessor();
    }
}
