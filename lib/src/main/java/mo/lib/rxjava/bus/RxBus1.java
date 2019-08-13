package mo.lib.rxjava.bus;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @ author：mo
 * @ data：2019/8/6:10:31
 * @ 功能：有压背处理的Rxbus
 */
public class RxBus1 {
    private FlowableProcessor<Object> bus;

    private RxBus1() {
        //把非线程安全的PublishSubject包装成线程安全的SerializedSubject
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus1 getDefault() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        public static volatile RxBus1 INSTANCE = new RxBus1();
    }

    /**
     * 发送事件
     *
     * @param event 事件对象
     */
    public void post(Object event) {
        if (hasSubscribers()) {
            bus.onNext(event);
        }
    }

    /**
     * 监听事件
     *
     * @return 特定类型的Observable
     */
    public Flowable<Object> observe() {
        return bus;
    }

    /**
     * 监听事件
     *
     * @param event 事件对象
     * @param <T>   事件类型
     * @return 特定类型的Observable
     */
    public <T> Flowable<T> observe(Class<T> event) {
        return bus.ofType(event);
    }

    public <T> Disposable observe(Class<T> event,Consumer<T> consumer) {
        return observe(event).subscribe(consumer);
    }

    /**
     * 解除注册
     */
    public void unregisterAll() {
        if (hasSubscribers()) {
            bus.onComplete();
        }

    }

    /**
     * 是否注册了
     */
    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }

}