package mo.lib.rxjava.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @ author：mo
 * @ data：2019/8/6:10:31
 * @ 功能：没有压背处理的Rxbus
 */
public class RxBus2 {
    private Subject<Object> bus;

    private RxBus2() {
        //把非线程安全的PublishSubject包装成线程安全的SerializedSubject
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus2 getDefault() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        public static volatile RxBus2 INSTANCE = new RxBus2();
    }

    /**
     * 发送事件
     *
     * @param event 事件对象
     */
    public void post(Object event) {
        if (bus.hasObservers()) {
            bus.onNext(event);
        }
    }

    /**
     * 监听事件
     *
     * @return 特定类型的Observable
     */
    public Observable<Object> observe() {
        return bus;
    }

    /**
     * 监听事件
     *
     * @param event 事件对象
     * @param <T>       事件类型
     * @return 特定类型的Observable
     */
    public <T> Observable<T> observe(Class<T> event) {
        return bus.ofType(event);
    }
}
