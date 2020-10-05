package cn.throwx.canal.gule.example.ch2;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import cn.throwx.canal.gule.support.processor.ExceptionHandler;
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
public class Client2App {

    public static void main(String[] args) {
        SpringApplication.run(Client2App.class, args);
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
            logger.info("接收到订单保存binlog,主键:{},模拟抛出异常...", orderModel.getId());
            throw new RuntimeException(String.format("[id:%d]", orderModel.getId()));
        }

        @Override
        protected ExceptionHandler exceptionHandler() {
            return EXCEPTION_HANDLER;
        }

        /**
         * 覆盖默认的ExceptionHandler.NO_OP
         */
        private static final ExceptionHandler EXCEPTION_HANDLER = (event, throwable)
                -> log.error("解析binlog事件出现异常,事件内容:{}", JSON.toJSONString(event), throwable);
    }

    @Bean
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }
}
