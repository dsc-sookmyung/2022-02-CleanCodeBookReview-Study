package com.practice.houseutils.policy;

import com.practice.houseutils.constants.ActionType;
import com.practice.houseutils.exception.ErrorCode;
import com.practice.houseutils.exception.HouseUtilsException;

/**
 * @author HwiMin
 *
 */

public class BrokeragePolicyFactory {
    public static BrokeragePolicy of(ActionType actionType){
        switch (actionType){
            case RENT:
                return new RentBrokeragePolicy();
            case PURCHASE:
                return new PurchaseBrokeragePolicy();
            default:
                throw new HouseUtilsException(ErrorCode.INVALID_REQUEST);
        }
    }
}
