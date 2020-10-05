package cn.throwx.canal.gule.example.bm;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/10/3 19:32
 */
@Data
public class Model {

    private Long a;
    private Integer b;
    private String c;
    private BigDecimal d;
    private java.sql.Date e;
    private java.sql.Date f;
    private LocalDateTime g;
    private OffsetDateTime h;
}
