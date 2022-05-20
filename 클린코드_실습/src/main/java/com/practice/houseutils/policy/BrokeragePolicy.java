package com.practice.houseutils.policy;

public interface BrokeragePolicy {

    BrokerageRule createBrokerageRule(Long price);

    default Long calculate(Long price){
        BrokerageRule rule = createBrokerageRule(price);
        return rule.calcMaxBrokerage(price);
    }

}
