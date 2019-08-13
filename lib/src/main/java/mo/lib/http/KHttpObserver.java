package mo.lib.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import mo.lib.kr;
import mo.lib.rxjava.KReceiveObserver;
import okhttp3.ResponseBody;

/**
 * @ author：mo
 * @ data：2019/2/26:18:25
 * @ 功能：请求回调，观察者
 */
public abstract class KHttpObserver<T> extends KReceiveObserver<ResponseBody> {


    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String body = responseBody.string();
            kOnNext("".getClass().equals(getTClass()) ? (T) body : (T) (new Gson()).fromJson(body, getTClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    protected abstract void kOnNext(T body);

    @Override
    public void onError(Throwable e) {
        Log.i(kr.TAG(), e.toString());
    }
    /**
     * 在回调类拿到泛型类，Gson解析用
     */
    private Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
