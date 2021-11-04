# OCBC_Project_Task

# Getting Started
## Technologies Stacks Implemented
- Kotlin
- Model-View-ViewModel (MVVM)
- Reactive (Coroutines)
- Single Activity Architecture
- Multi Module
- Koin Dependency Injection
- View Binding

### Overall Architecture
As the project grows in size and feature set, there is an increasing need to modularise the codebase into separate independent modules. Ultimately, 
the goal for modularising the codebase is 
- (1) to have separation of concern 
- (2) to better allow separate teams to work on modules independently.

! (https://miro.medium.com/max/1400/1*_yMf9QMhYxZmc96lp6KOCg.png)

### Views
    - do observe for data changes in view models;
    - do trigger methods in view models;
    - do manipulate and read data from the UI;
    - do interact with the Android SDK;
    
### View Models
    - do expose LiveData observables;
    - do prepare data for display;
    - do coordinate and execute use cases;
    
### Use Cases
    - Responsible for encapsulating a specific business action, performs integration and orchestration with the data layer, and mapping between entity and domain models.
    - do contain business rule validations;
    - do fetch data using various services;
    - do perform mapping between entity and domain models;
    
### Remote Services and Local Storage
    - Responsible for serving as an access point to external data layers and persistence.
    - do contain data fetching logic;
    - do know about the data source;
    - don't contain any business rule validations.
    
    

## **Network Module:**

### How Injected:

1) okhttp3 in NetworkModule to provide access all over the application & add interceptors for later use:
    - Chucker interceptor to view request and response in notification area instead of logs.
    - Token Service interceptor to store and send to the apis request.
2) All the other modules services (Apis) 

```
val NetworkModule = module {
    single { createGson() }
    single { createTokenService() }
    single { createLoginService(get()) }
    single { createDashboardService(get()) }
    single { createTransferService(get()) }
    single { createRetrofit(get(), get()) }
    single {
        createOkHttpClient(
            createChuckerInterceptor(get()), createCommonHeadersInterceptor(),
            createTokenInjectorInterceptor(get()), createTokenResponseInterceptor(get(), get())
        )
    }
}

fun createOkHttpClient(
    chuckerInterceptor: ChuckerInterceptor,
    addCommonHeaders: CommonHeaderInterceptor,
    injectToken: TokenInjectorInterceptor,
    storeIncomingTokens: TokenResponseInterceptor,
): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val builder = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(addCommonHeaders)
        .addInterceptor(injectToken)
        .addInterceptor(storeIncomingTokens)
        .addInterceptor(chuckerInterceptor)

    return builder.build()
}
```
### **Feature wise Module Injection:**
- Login Module
- Dashboard Module
- Transfer Module



 
