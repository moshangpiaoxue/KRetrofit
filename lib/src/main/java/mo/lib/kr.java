package mo.lib;

import mo.lib.http.KHttpsManager;
import mo.lib.http.KHttpsManagerImpl;
import okhttp3.OkHttpClient;

/**
 * @ author：mo
 * @ data：2019/2/26:17:28
 * @ 功能：
 */
public class kr {
    public static class Ext {
        private static Boolean isDebug = true;
        private static String TAG = "友情提示";
        private static String BASE_URL = "";
        private static KHttpsManager httpsManager;
        private static OkHttpClient okHttpClient;

        public Ext() {
        }

        public static void setOkHttpClient(OkHttpClient okHttpClient) {
            Ext.okHttpClient = okHttpClient;
        }

        public static void setHttpsManager(KHttpsManager httpsManager) {
            Ext.httpsManager = httpsManager;
        }


        public static void setBaseUrl(String baseUrl) {
            BASE_URL = baseUrl;
        }

        private static void setTAG(String tag) {
            Ext.TAG = TAG;
        }

        public static void setIsDebug(Boolean isDebug) {
            Ext.isDebug = isDebug;
        }
    }

    /**
     * 获取tag
     */
    public static String TAG() {
        return Ext.TAG;
    }

    public static Boolean isDebug() {
        return Ext.isDebug;
    }

    public static String BASE_URL() {
        return Ext.BASE_URL;
    }

    public static KHttpsManager http() {
        if (Ext.httpsManager == null) {
            KHttpsManagerImpl.INSTANCE.regist();
        }
        return Ext.httpsManager;
    }

    public static OkHttpClient getOkHttpClient() {
        return Ext.okHttpClient;
    }
}
