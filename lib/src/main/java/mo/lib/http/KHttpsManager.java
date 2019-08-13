package mo.lib.http;

import java.util.Map;

import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * @ author：mo
 * @ data：2019/2/26:17:49
 * @ 功能：
 */
public interface KHttpsManager {
     void get(String url, Map<String, String> map, Observer<ResponseBody> observer);

     void post(String url, Map<String, String> map, Observer<ResponseBody> observer);
}
