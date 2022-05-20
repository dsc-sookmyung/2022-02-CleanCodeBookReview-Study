# 실습 Week1

눈여겨볼 것들!

- 분기처리하여 객체를 생성하고, 그 객체의 메소드를 호출해야 한다.
    - 객체들을 포괄할 수 있는 인터페이스를 작성한다. 인터페이스 안에서 공통적인 메소드는 구현을 하고, 클래스마다 다르게 구현해야 하는 함수는 추상 메소드로 남기고 구현의 책임을 넘긴다.
    - 분기처리하여 객체를 생성하는 책임은 Factory 클래스에 맡긴다. 팩토리 클래스는 객체를 생성하는 책임만을 가진다.
    - 최종적으로 각 객체의 메소드를 호출하는 부분에서는, 팩토리로 객체를 생성하여 인터페이스 타입으로 받고, 그 인터페이스의 공통 메소드를 호출한다. 그렇게 되면 호출 부분의 코드는 객체 생성과 메소드 호출만이 남는다.
- Enum에 Description 필드를 추가하는 것
- IntelliJ에서 Http로 내부 테스트, 재컴파일 필요 없음
- 자바 주석 : /** @author */, // TODO :

- 도메인 확인
    
    ![image](https://user-images.githubusercontent.com/53958188/169536238-e7bad8bb-5912-47a3-a5f2-28a1e50b83e0.png)
    
    - 거래 종류와 거래 금액을 알려주면 중개수수료가 얼마인지 알려주는 서비스
- Health Check API
    - 배포 시스템에서 서버가 떴는지 확인하기 위한 api
    
    ```Java
    @RestController
    public class HealthCheckController {
        @GetMapping("/api/ping")
        public String ping(){
            return "ok";
        }
    }
    ```
    
    - 테스트 : intellij Http 툴 (utlimate에서만 지원)
        
        [IntelliJ의 .http를 사용해 Postman 대체하기](https://jojoldu.tistory.com/266)
        
        ![image](https://user-images.githubusercontent.com/53958188/169536324-fd1ef1f9-6caf-4632-91f4-57422c7e13af.png)
        
        http 테스트
        
        ![image](https://user-images.githubusercontent.com/53958188/169536388-145ab6bb-0102-443c-b931-c7b08f7d6308.png)
        
        다양한 환경을 테스트할 때 서버 주소를 변수로 지정해둔다
        
    
- 거래 타입과 가격을 받아서 중개 수수료를 계산하는 로직
    
    
    주목할 것
    
    - 이름
    - 실무에서 객체 지향적인 코드의 작성
    
    - Controller : `BrokerageQueryController`
        - Brokerage(중개수수료)를 질의하는 컨트롤러이다.
        
        ```Java
        		@GetMapping("/api/calc/brokerage")
            public Long caclBrokerage(){
                // 중개 수수료를 계산하는 로직
                return null;
            }
        ```
        
    - Enum : `ActionType`
        - 거래 타입(Action Type)을 나타낸다.
        - 실무에서는 이렇게 Enum 타입에 설명을 추가해서 작성한다고 한다.
        
        ```Java
        @AllArgsConstructor
        public enum ActionType {
            PURCHASE("매매"), RENT("임대차");
            private String description;
        }
        ```
        
        ```Java
        @RestController
        public class BrokerageQueryController {
            @GetMapping("/api/calc/brokerage")
            public Long caclBrokerage(@**RequestParam ActionType actionType,**
                                      @RequestParam Long price){
                // 중개 수수료를 계산하는 로직
                return null;
            }
        }
        ```
        
    - `BrokerageRule`
        - 가격이 특정 범위일 때 상한효율과 상환금액을 가지는 클래스
        
        ```Java
        /**
         * @author HwiMin
         *
         * 가격이 특정 범위일 때 상한효율과 상한금액을 가지는 클래스
         */
        
        @AllArgsConstructor
        public class BrokerageRule {
            private Double brokeragePercent;
            private Long limitAmount;
        
            public Long calcMaxBrokerage(Long price){
                if(limitAmount==null){
                    return multiplyPercent(price);
                }
                return Math.min(multiplyPercent(price), limitAmount);
            }
        
            private Long multiplyPercent(Long price){
                Double amount = Math.floor(brokeragePercent / 100 * price);
                return amount.longValue();
            }
        }
        ```
        
    - Policy : `PurchaseBrokeragePolicy`
        - **매매**할 때 중개수수료를 계산하는 클래스
        - 받은 가격에 따라서, 상한효율과 상환금액을 가지는 클래스를 생성한다.
        - 생성한 클래스로부터 최대 중계수수료를 계산한다.
        - 이 두 개의 책임을 두개의 함수로 나누어 작성한다.
        
        ```Java
        /**
         * @author HwiMin
         *
         * 매매할 때 증개수수료를 계산하는 클래스
         */
        
        public class PurchaseBrokeragePolicy {
            public Long calculate(Long price){
                // TODO : 가격을 받아서 중개수수료를 계산한다.
                BrokerageRule rule = createBrokerageRule(price);
                return rule.calcMaxBrokerage(price);
            }
        
            private BrokerageRule createBrokerageRule(Long price){
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
        ```
        
    - Policy : `RentBrokeragePolicy`
        - 임대차일때 증개수수료를 계산하는 클래스
        - 어 근데 매매일때 중개수수료를 계산하는 클래스와 코드의 틀은 똑같다!
            
            ⇒ 인터페이스로 묶자
            
        
        ```Java
        /**
         * @author HwiMin
         *
         * 임대차일때 증개수수료를 계산하는 클래스
         */
        
        public class RentBrokeragePolicy {
            public Long calculate(Long price){
                // TODO : 가격을 받아서 중개수수료를 계산한다.
                BrokerageRule rule = createBrokerageRule(price);
                return rule.calcMaxBrokerage(price);
            }
        
            private BrokerageRule createBrokerageRule(Long price){
                BrokerageRule rule;
                if(price<50_000_000){
                    rule = new BrokerageRule(0.5, 200_000L);
                } else if(price<100_000_000){
                    rule = new BrokerageRule(0.4, 300_000L);
                } else if(price<300_000_000){
                    rule = new BrokerageRule(0.3, null);
                } else if(price<600_000_000){
                    rule = new BrokerageRule(0.4, null);
                } else{
                    rule = new BrokerageRule(0.8, 800_000L);
                }
                return rule;
            }
        
        }
        ```
        
    - 중개수수료 계산의 인터페이스 : `BrokeragePolicy`
        - 공통 메소드 : calculate
        - 다르게 구현하는 메소드 : **createBrokerageRule**
        - 구현 클래스에서 **createBrokerageRule**만 남길 수 있게 된다.
        
        ```Java
        public interface BrokeragePolicy {
        
            BrokerageRule **createBrokerageRule**(Long price);
        
            default Long calculate(Long price){
                BrokerageRule rule = createBrokerageRule(price);
                return rule.calcMaxBrokerage(price);
            }
        
        }
        ```
        
        ```Java
        public class RentBrokeragePolicy implements BrokeragePolicy{
            
            public BrokerageRule createBrokerageRule(Long price){
                BrokerageRule rule;
                if(price<50_000_000){
                    rule = new BrokerageRule(0.5, 200_000L);
                } else if(price<100_000_000){
                    rule = new BrokerageRule(0.4, 300_000L);
                } else if(price<300_000_000){
                    rule = new BrokerageRule(0.3, null);
                } else if(price<600_000_000){
                    rule = new BrokerageRule(0.4, null);
                } else{
                    rule = new BrokerageRule(0.8, 800_000L);
                }
                return rule;
            }
        
        }
        
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
        ```
        
    - `BrokerageQueryController` 1차 작성
        - 아쉬움 : 다형성을 통해 PurchaseBrokeragePolicy와 RentBrokeragePolicy를 묶어줄 수 있지 않을까? (DIP원칙 위반) ⇒ 객체를 생성하는 Factory를 만들자.
        
        ```Java
        @RestController
        public class BrokerageQueryController {
        
            @GetMapping("/api/calc/brokerage")
            public Long caclBrokerage(@RequestParam ActionType actionType,
                                      @RequestParam Long price){
                // 중개 수수료를 계산하는 로직
                if(actionType == ActionType.PURCHASE){
                    PurchaseBrokeragePolicy policy = new PurchaseBrokeragePolicy();
                    return policy.calculate(price);
                }
                if(actionType == ActionType.RENT){
                    RentBrokeragePolicy policy = new RentBrokeragePolicy();
                    return policy.calculate(price);
                }
                return null;
            }
        }
        ```
        
    - 객체 생성의 분기 처리 : `BrokeragePolicyFactory`
        - 전달받은 타입에 따라 분기 처리하여 객체를 생성한다.
        - BrokeragePolicy 객체 생성의 책임을 가진다.
        
        ```Java
        public class BrokeragePolicyFactory {
            public static BrokeragePolicy of(ActionType actionType){
                switch (actionType){
                    case RENT:
                        return new RentBrokeragePolicy();
                    case PURCHASE:
                        return new PurchaseBrokeragePolicy();
                    default:
                        throw new IllegalArgumentException("해당 액션에 대한 정책이 존재하지 않습니다.");
                }
            }
        }
        ```
        
    - `BrokerageQueryController` 2차 작성
        - 와우~ 전에 비하면 코드가 매우 깔끔해졌다
        - 객체 생성은 팩토리가 전담하도록 하고, 인터페이스를 통해 calculate 함수만 호출해도 된다. 전에 비하면 코드의 의존도가 떨어졌다.
        
        ```Java
        @RestController
        public class BrokerageQueryController {
        
            @GetMapping("/api/calc/brokerage")
            public Long caclBrokerage(@RequestParam ActionType actionType,
                                      @RequestParam Long price){
                // 중개 수수료를 계산하는 로직
                BrokeragePolicy policy = BrokeragePolicyFactory.of(actionType);
                return policy.calculate(price);
        
            }
        }
        ```
