package mo.lib.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ author：mo
 * @ data：2019/8/6:15:08
 * @ 功能：自定义观察者
 * @ 作用：接收处理好的数据 扔View里或者其他
 */
public class KReceiveObserver<T> implements Observer<T> {
    /**
     * 当接收到的时候  这时候可以截断
     * Disposable ：可处理的，可以理解为可断开的，disposable.dispose()方法被调用后，断开连接，接收方不再接收事件，但是发送方可以继续
     */
    @Override
    public void onSubscribe(Disposable disposable) {
//        LogUtil.i("接收");
    }

    /**
     * 执行
     */
    @Override
    public void onNext(T t) {
//        LogUtil.i("执行");
    }

    /**
     * 报错了
     */
    @Override
    public void onError(Throwable throwable) {
//        LogUtil.i("报错");
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {
//        LogUtil.i("完成");
    }
}