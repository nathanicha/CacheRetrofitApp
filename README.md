# Cache Retrofit Android PlayGround

Learning how to cahing on local storage with Retrofit 

## How to implement? 
- Implement Cache, Online&Offline Interceptor and then adding to OkHttpClient.Builder()
- Only @GET method work with cache

## How it works?

ApiClient

<img width="540" alt="addConverterFactory(GsonConverterFactory create())" src="https://user-images.githubusercontent.com/35954605/136907984-1b029cbe-090c-473d-967a-3d5c051fdcea.png">

.cache(cache: Cache?) : Define directory to saving cache and cache size
.addInterceptor() : access interceptor when online and offline connection
.addNetworkInterceptor() : access interceptor only online connection

OnlineCachingInterceptor

<img width="593" alt="Screen Shot 2564-10-12 at 14 14 04" src="https://user-images.githubusercontent.com/35954605/136909222-dbba8003-279f-4db6-acc5-419ff496f08e.png">

.maxAge(maxAge: Int, timeUnit: TimeUnit) : Keep the cache for [maxAge], for example maxAge is "5 minutes" so if the time between call api multiple times less than 5 minutes, it will receiving response from cache

OfflineCachingInterceptor

<img width="511" alt="Screen Shot 2564-10-12 at 14 19 42" src="https://user-images.githubusercontent.com/35954605/136909937-d1ed8144-4d53-4547-8818-82c861f1e2d2.png">

.maxStale(maxStale: Int, timeUnit: TimeUnit) : If local cache it is younger than the maxStale, It will use local cache, But If it's older then it won't use it

## Some of code Refference 
https://www.journaldev.com/13639/retrofit-android-example-tutorial
