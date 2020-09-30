package cn.throwx.canal.gule.example.ch0;

import cn.throwx.canal.gule.CanalGlue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author throwable
 * @version v1
 * @description 测试一下一般情况下的使用
 * @since 2020/9/24 22:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Client0App.class)
@Configuration
public class Client0 {

    @Autowired
    private CanalGlue canalGlue;

    private static String EVENT;

    @BeforeClass
    public static void before() throws Exception {
        EVENT = StreamUtils.copyToString(new ClassPathResource("ch0.json").getInputStream(), StandardCharsets.UTF_8);
    }

    @Test
    public void testProcessInsertInternal() {
        canalGlue.process(EVENT);
    }
}
