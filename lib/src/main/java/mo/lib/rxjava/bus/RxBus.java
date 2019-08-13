package mo.lib.rxjava.bus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * @ author：mo
 * @ data：2019/8/13:16:10
 * @ 功能：网上扒的，据说能处理异常，只是为了能使，
 * 注意：1、不需要注册、解除注册，尤其是不能解除注册，会收不到消息
 *      2、eventbus能注册、解除，貌似是在注解的时候又判断注册了一遍，并且把接收的行为重新激活了
 */
public class RxBus {
    private static volatile RxBus instance;
    private final Relay<Object> mBus;

    public RxBus() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }
    public void post(Object obj) {
        mBus.accept(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return  mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
