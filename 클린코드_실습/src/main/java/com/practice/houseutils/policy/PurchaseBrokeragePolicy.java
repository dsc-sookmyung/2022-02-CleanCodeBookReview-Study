package com.practice.houseutils.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author HwiMin
 *
 * 매매할 때 증개수수료를 계산하는 클래스
 */

@Getter
public class PurchaseBrokeragePolicy implements BrokeragePolicy{

    private final List<BrokerageRule> rules;

    // 객체간의 관계로 바꿈
    public PurchaseBrokeragePolicy(){
        rules = Arrays.asList(
                new BrokerageRule(50_000_000L, 0.0, 250_000L),
                new BrokerageRule(200_000_000L, 0.5, 800_000L),
                new BrokerageRule(600_000_000L, 0.4),
                new BrokerageRule(900_000_000L, 0.5),
                new BrokerageRule(Long.MAX_VALUE, 0.9)
        );

    }

}
