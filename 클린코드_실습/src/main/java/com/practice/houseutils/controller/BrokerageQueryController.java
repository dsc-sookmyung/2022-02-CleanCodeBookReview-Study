package com.practice.houseutils.controller;

import com.practice.houseutils.constants.ActionType;
import com.practice.houseutils.policy.BrokeragePolicy;
import com.practice.houseutils.policy.BrokeragePolicyFactory;
import com.practice.houseutils.service.ApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HwiMin
 *
 * 중개수수료가 얼마인지 조회하는 컨트롤러
 */

@RestController
@AllArgsConstructor
public class BrokerageQueryController {

    private final ApartmentService apartmentService;

    @GetMapping("/api/calc/brokerage")
    public Long caclBrokerage(@RequestParam ActionType actionType,
                              @RequestParam Long price){
        BrokeragePolicy policy = BrokeragePolicyFactory.of(actionType);
        return policy.calculate(price);
    }

    @GetMapping("/api/calc/apartment/{apartmentId}")
    public Long calcBrokerageByApartmentId(
            @PathVariable Long apartmentId,
            @RequestParam ActionType actionType
    ){
        BrokeragePolicy policy = BrokeragePolicyFactory.of(actionType);
        return policy.calculate(apartmentService.getPriceOrThrow(apartmentId));
    }
}
