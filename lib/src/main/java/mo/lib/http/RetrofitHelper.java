package mo.lib.http;

import java.util.concurrent.TimeUnit;

import mo.lib.kr;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @ author：mo
 * @ data：2019/2/26：17:46
 * @ 功能：Retrofit帮助类
 */
enum RetrofitHelper {
    /**
     * 枚举单例
     */
    INSTANCE;

    public RetrofitService getRetrofitService() {
        /**
         *1.创建Regrofit的实例对象
         *2.设置服务器主机,注意：BASE_URL必须以/结尾，否则报错
         *3.配置gson解析器,内部会使用Gson库来解析javabean
         *4.添加rxjava2适配器
         *5.添加Http拦截器,拦截后台返回的数据
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(kr.BASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(kr.getOkHttpClient() == null ? getOkHttpClient() : kr.getOkHttpClient())
                .build();

        /**
         * 6.获取NetService业务接口的实现类对象,其中内部是通过动态代理来创建接口实现类对象的
         */
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }


    private OkHttpClient getOkHttpClient() {
        //缓存路径
//        File cacheFile = new File(kr.app().getCacheDir().getAbsolutePath(), "HttpCache");
        //缓存文件为10MB
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(3, TimeUnit.MINUTES)
                //设置连接超时时间
                .connectTimeout(3, TimeUnit.MINUTES)
                //设置写入超时时间
                .writeTimeout(3, TimeUnit.MINUTES)
                //添加拦截器保存token
                .addInterceptor(KInterceptorUtil.HeaderInterceptorGet())
                //添加拦截器添加token
                .addInterceptor(KInterceptorUtil.HeaderInterceptorPut())
                //添加拦截器添平台类型 Android==1 iOS==2
                .addInterceptor(KInterceptorUtil.HeaderInterceptorAdd())
                //添加拦截器添是否走缓存
//                .addInterceptor(KInterceptorUtil.CacheInterceptor())
                //添加拦截器添打印请求接口log
                .addInterceptor(KInterceptorUtil.RequestInterceptor())
                //添加拦截器添打印请求结果接口log
                .addInterceptor(KInterceptorUtil.ResultInterceptor())
                //设置缓存
//                .cache(cache)
                ;

        return builder.build();
    }

}
