package mo.lib;

import android.util.Log;

import mo.lib.kr;

/**
 * @ author：mo
 * @ data：2019/2/27:16:01
 * @ 功能：
 */
public class KLog {

    public static void i(String string) {
        if (kr.isDebug()) {
            Log.i(kr.TAG(), string);
        }
    }
}
