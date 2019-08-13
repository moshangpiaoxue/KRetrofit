package mo.lib.http;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mo.lib.kr;
import okhttp3.ResponseBody;

/**
 * @ author：mo
 * @ data：2019/2/26:18:16
 * @ 功能：网络请求实现类
 */
public enum  KHttpsManagerImpl implements KHttpsManager {
    /**
     * 枚举单例
     */
    INSTANCE;

    public void regist() {
        kr.Ext.setHttpsManager(this);
    }

    @Override
    public void get(String url, Map<String, String> map, Observer<ResponseBody> observer) {
        RetrofitHelper.INSTANCE.getRetrofitService()
                .GET(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public  void post(String url, Map<String, String> map, Observer<ResponseBody> observer) {
        RetrofitHelper.INSTANCE.getRetrofitService()
                .POST(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
