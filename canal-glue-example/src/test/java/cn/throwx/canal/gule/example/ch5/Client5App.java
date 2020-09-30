package cn.throwx.canal.gule.example.ch5;

import cn.throwx.canal.gule.DefaultCanalGlue;
import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.parser.*;
import cn.throwx.canal.gule.support.parser.converter.CanalFieldConverterFactory;
import cn.throwx.canal.gule.support.parser.converter.InMemoryCanalFieldConverterFactory;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import cn.throwx.canal.gule.support.processor.CanalBinlogEventProcessorFactory;
import cn.throwx.canal.gule.support.processor.InMemoryCanalBinlogEventProcessorFactory;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/28 23:16
 */
public class Client5App {

    public static void main(String[] args) throws Exception {
        String event = StreamUtils.copyToString(new ClassPathResource("ch5.json").getInputStream(), StandardCharsets.UTF_8);
        CanalBinlogEventProcessorFactory processorFactory = InMemoryCanalBinlogEventProcessorFactory.of();
        DefaultCanalGlue canalGlue = DefaultCanalGlue.of(processorFactory);
        CanalFieldConverterFactory converterFactory = InMemoryCanalFieldConverterFactory.of();
        CanalBinLogEventParser binLogEventParser = DefaultCanalBinLogEventParser.of();
        ModelTableMetadataManager modelTableMetadataManager = InMemoryModelTableMetadataManager.of(converterFactory);
        ParseResultInterceptorManager interceptorManager = InMemoryParseResultInterceptorManager.of(modelTableMetadataManager);
        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.init(
                binLogEventParser,
                modelTableMetadataManager,
                processorFactory,
                interceptorManager
        );
        canalGlue.process(event);
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
