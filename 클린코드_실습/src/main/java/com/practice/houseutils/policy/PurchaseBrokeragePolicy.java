package com.practice.houseutils.policy;

/**
 * @author HwiMin
 *
 * 매매할 때 증개수수료를 계산하는 클래스
 */

public class PurchaseBrokeragePolicy implements BrokeragePolicy{

    public BrokerageRule createBrokerageRule(Long price){
        BrokerageRule rule;
        if(price<50_000_000){
            rule = new BrokerageRule(0.0, 250_000L);
        } else if(price<200_000_000){
            rule = new BrokerageRule(0.5, 800_000L);
        } else if(price<600_000_000){
            rule = new BrokerageRule(0.4, null);
        } else if(price<900_000_000){
            rule = new BrokerageRule(0.5, null);
        } else{
            rule = new BrokerageRule(0.9, 800_000L);
        }
        return rule;
    }
}
