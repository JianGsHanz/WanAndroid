package com.common.util;

import com.common.app.HttpResponse;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程 统一处理线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<T, T> rxSchedulerHelpe() {    //compose简化线程 统一处理线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

        };
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<List<T>> createData(final List<T> t) {
        return Flowable.create(new FlowableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(FlowableEmitter<List<T>> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleResult() {
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<HttpResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(HttpResponse<T> httpResponse) throws Exception {
                        if (httpResponse.code == 0) {
                            if (httpResponse.data != null)
                                return createData(httpResponse.data);
                            if (httpResponse.result != null)
                                return createData(httpResponse.result);
                            return Flowable.error(new Exception("服务器返回error"));
                        } else {
                            return Flowable.error(new Exception("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<List<T>>, List<T>> handleListResult() {
        return new FlowableTransformer<HttpResponse<List<T>>, List<T>>() {
            @Override
            public Publisher<List<T>> apply(Flowable<HttpResponse<List<T>>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<HttpResponse<List<T>>, Flowable<List<T>>>() {
                    @Override
                    public Flowable<List<T>> apply(HttpResponse<List<T>> httpResponse) throws Exception {
                        if (httpResponse.code == 0) {
                            if (httpResponse.data != null)
                                return createData(httpResponse.data);
                            if (httpResponse.result != null)
                                return createData(httpResponse.result);
                            return Flowable.error(new Exception("服务器返回error"));
                        } else {
                            return Flowable.error(new Exception("服务器返回error"));
                        }
                    }
                });
            }
        };
    }
}
