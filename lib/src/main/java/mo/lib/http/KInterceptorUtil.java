package mo.lib.http;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mo.lib.KLog;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ author：mo
 * @ data：2019/1/23
 * @ 功能：拦截器工具类!
 */
public class KInterceptorUtil {
    public static String token;
    public static String token2;

    /**
     * 日志拦截器--打印返回结果
     */
    public static HttpLoggingInterceptor ResultInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                String s = message.substring(0, 1);
                //如果收到想响应是json才打印
                if ("{".equals(s) || "[".equals(s)) {
                    KLog.i("收到响应: \n" + message);
                }
            }
            //设置打印数据的级别
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 拦截器--打印请求信息
     */
    public static Interceptor RequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                KLog.i("发送请求\n 完整请求：" + request.url() + "\n请求方法：" + request.method() + "\n请求headers：" + request.headers());
                return chain.proceed(request);
            }
        };
    }

    /**
     * 拦截器--获取Header
     */
    public static Interceptor HeaderInterceptorGet() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                //set-cookie可能为多个
                if (!response.headers("set-cookie").isEmpty()) {
                    List<String> cookies = response.headers("set-cookie");
                    String cookie = encodeCookie(cookies);
                    String[] split = cookie.split("=");
                    int length = split.length;
                    if (length > 1) {
                        saveCookie(request.url().toString(), request.url().host(), split[1]);
                    }
                }
                return response;
            }

            //整合cookie为唯一字符串
            private String encodeCookie(List<String> cookies) {
                StringBuilder sb = new StringBuilder();
                Set<String> set = new HashSet<>();
                for (String cookie : cookies) {
                    String[] arr = cookie.split(";");
                    for (String s : arr) {
                        if (set.contains(s)) {
                            continue;
                        }
                        set.add(s);
                    }
                }
                Iterator<String> ite = set.iterator();
                while (ite.hasNext()) {
                    String cookie = ite.next();
                    sb.append(cookie).append(";");
                }
                int last = sb.lastIndexOf(";");
                if (sb.length() - 1 == last) {
                    sb.deleteCharAt(last);
                }
                return sb.toString();
            }

            //保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
            //这样能使得该cookie的应用范围更广
            private void saveCookie(String url, String domain, String cookies) {
//                KLog.i("cookies1==" + cookies);
                if (TextUtils.isEmpty(url)) {
                    throw new NullPointerException("url is null.");
                } else {
                    token = cookies;
                }
                if (!TextUtils.isEmpty(domain)) {
                    token2 = cookies;
                }
            }
        };
    }

    /**
     * 拦截器--添加Header  plant
     */
    public static Interceptor HeaderInterceptorAdd() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("plant", "1")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };
    }

    /**
     * 拦截器--添加Header  token
     */
    public static Interceptor HeaderInterceptorPut() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                String cookie = getCookie(request.url().toString(), request.url().host());
//               下面这个header是为了解决 报错： java.net.ProtocolException: unexpected end of stream
                builder.addHeader("Connection", "close");
//                LogUtil.i("cookies2=="+cookie);
                if (!TextUtils.isEmpty(cookie)) {
                    //只是添加请求头
                    builder.addHeader("token", cookie);
                }
                return chain.proceed(builder.build());
            }
        };
    }

    private static String getCookie(String url, String domain) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(token)) {
            return token;
        }
        if (!TextUtils.isEmpty(domain) && !TextUtils.isEmpty(token2)) {
            return token2;
        }

        return null;
    }
//    /**
//     * 拦截器--是否走缓存
//     */
//    public static Interceptor CacheInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        //有网络时从网络获取,没网时走缓存
//                        .cacheControl(KConnectivityManager.INSTANCE.isConnected() ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
//                        .build();
//                Response response = chain.proceed(request);
////                        if (KConnectivityManager.INSTANCE.isConnected()) {
////                            response = response.newBuilder()
////                                    .removeHeader("Pragma") //清除头信息，因为服务器如果不支持，会返回一些干扰信息
////                                    .header("Cache-Control", "public, max-age=" + maxAge)
////                                    .build();
////                        } else {
////                            response = response.newBuilder()
////                                    .removeHeader("Pragma")
////                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
////                                    .build();
////                        }
//                return response;
//            }
//        };
//    }
}
