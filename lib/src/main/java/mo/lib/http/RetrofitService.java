package mo.lib.http;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @ author：mo
 * @ data：2019/2/26：18:11
 * @ 功能：请求服务
 */
public interface RetrofitService {
    /**
     * 此处的接口是用来定义项目中的所有的业务方法
     * Observable为回调对象 <泛型>为Bean类  xxx是方法名(自己任意取)
     */


    @POST
    Observable<ResponseBody> POST(@Url String url, @QueryMap Map<String, String> map);
    @GET
    Observable<ResponseBody> GET(@Url String url, @QueryMap Map<String, String> map);


}
