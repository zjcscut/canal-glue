package cn.throwx.canal.gule.example.ch4;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.support.parser.BaseParseResultInterceptor;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

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
public class Client4App {

    public static void main(String[] args) {
        SpringApplication.run(Client4App.class, args);
    }

    @Data
    @CanalModel(database = "db_order_service", table = "t_order", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
    public static class OrderModel {

        private Long id;

        private String orderId;

        private OffsetDateTime createTime;

        private BigDecimal amount;

        private String customerPhone;

        private String customerName;
    }

    public static class OrderProcessor extends BaseCanalBinlogEventProcessor<OrderModel> {

        @Override
        protected void processInsertInternal(CanalBinLogResult<OrderModel> result) {
            OrderModel orderModel = result.getAfterData();
            logger.info("接收到订单保存binlog,主键:{},写入数据:{}", result.getPrimaryKey(), JSON.toJSONString(orderModel));
        }
    }

    @Slf4j
    public static class AesDecryptResultInterceptor extends BaseParseResultInterceptor<OrderModel> {

        @Override
        public void onBeforeInsertProcess(ModelTable modelTable, OrderModel beforeData, OrderModel afterData) {
            decrypt(modelTable, afterData);
        }

        @Override
        public void onBeforeUpdateProcess(ModelTable modelTable, OrderModel beforeData, OrderModel afterData) {
            decrypt(modelTable, afterData);
        }

        private void decrypt(ModelTable modelTable, OrderModel afterData) {
            if (null != afterData) {
                if (StringUtils.hasLength(afterData.getCustomerName())) {
                    String before = afterData.getCustomerName();
                    String after = AESUtils.X.decrypt(before);
                    log.info("通过拦截器对[{}.{}]的customer_name字段进行解密,before:{},after:{}", modelTable.database(),
                            modelTable.table(), before, after);
                    afterData.setCustomerName(after);
                }
                if (StringUtils.hasLength(afterData.getCustomerPhone())) {
                    String before = afterData.getCustomerPhone();
                    String after = AESUtils.X.decrypt(before);
                    log.info("通过拦截器对[{}.{}]的customer_phone字段进行解密,before:{},after:{}", modelTable.database(),
                            modelTable.table(), before, after);
                    afterData.setCustomerPhone(after);
                }
            }
        }
    }

    @Bean
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }

    @Bean
    public AesDecryptResultInterceptor aesDecryptResultInterceptor() {
        return new AesDecryptResultInterceptor();
    }
}
