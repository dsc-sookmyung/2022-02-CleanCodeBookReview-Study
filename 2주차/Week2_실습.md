# 실습 Week2

### JavaDocs 주석

```java
**/**
 * @author HwiMin
 * 
 * 서비스 헬스 체크를 위한 컨트롤러
 * 
 */**

@RestController
public class HealthCheckController {
    @GetMapping("/api/ping")
    public String ping(){
        return "ok";
    }

}
```

### @TODO

TODO 주석은 해야할 것이나 개선 점을 적는다. TODO를 포함해 커밋하기도 한다.

나중에 IDE로 TODO만 따로 볼 수 있다. 

껍데기를 개발할 때 작성한 TODO 주석은 개발을 끝낸 후 삭제한다.

### @Nullable

Null을 반환할 수도 있는 객체에 적는다. 

[@Nullable and @NotNull | IntelliJ IDEA](https://www.jetbrains.com/help/idea/nullable-and-notnull-annotations.html#nullable)
