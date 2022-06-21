package com.practice.houseutils.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * @author HwiMin
 *
 * 가격이 특정 범위일 때 상한효율과 상한금액을 가지는 클래스
 */

@AllArgsConstructor
@Getter
public class BrokerageRule {
    private Long lessThan;
    private Double brokeragePercent;

    @Nullable
    private Long limitAmount;

    public BrokerageRule(Long lessThan, Double brokeragePercent){
        this(lessThan, brokeragePercent, Long.MAX_VALUE);
    }

    public Long calcMaxBrokerage(Long price){
        return Math.min(multiplyPercent(price), limitAmount);
    }

    private Long multiplyPercent(Long price){
        Double amount = Math.floor(brokeragePercent / 100 * price);
        return amount.longValue();
    }
}
