# HandlerError

#### You can use this library for rest api, restful api, swap api

```gradle
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
```

```gradle
    implementation 'com.github.dashwood01:HandlerError:0.2'
```

### Use it like this

```java
    // if you have volley error and you know it key value
    ResponseError.errorResponding("KEY",volleyError,context);
    // if you have volley error and you don't know about a key
    ResponseError.errorResponding("",volleyError,context);
    // and if you dont have volley error just object
    ResponseError.errorResponding("",object,context);
```
