# HandlerError

Jitpack and maven center have problem right now, so download library and copy it in lib folder then implementation

####You can use this library for rest api, restful api, swap api

```gradle
    implementation project(':ResponseError')
```

### use it like this

```java
    // if you have volley error and you know it key value
    ResponseError.errorResponding("KEY",volleyError,context);
    // if you have volley error and you don't know about a key
    ResponseError.errorResponding("",volleyError,context);
    // and if you dont have volley error just object
    ResponseError.errorResponding("",object,context);
```
