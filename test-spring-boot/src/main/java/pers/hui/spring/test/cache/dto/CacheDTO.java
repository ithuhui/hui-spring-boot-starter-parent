package pers.hui.spring.test.cache.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <code>CacheDto</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/21 21:31.
 *
 * @author _Ken.Hu
 */
@Data
public class CacheDTO {
    private String userId;
    private String orderId;
    private Amount amount;

    @Data
    public static class Amount{
        private String unit;
        private BigDecimal val;
    }
}
