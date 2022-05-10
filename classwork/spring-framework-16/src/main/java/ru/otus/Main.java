package ru.otus;


import io.reactivex.Observable;

import io.reactivex.disposables.Disposable;

import java.io.IOException;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class Main {

    public static void main(String[] args) throws IOException {
/*        Observable<Long> values=Observable.interval(1/1000000000, TimeUnit.NANOSECONDS);
         Disposable subscription=values.subscribe(
                v->System.out.println("Recived:"+v),
                e-> System.out.println("Error:"+e),
                ()-> System.out.println("Complited")
        );
        System.in.read();*/


        Observable<Integer> s=Observable.range(20, 5);
        Observable<Integer> d=Observable.range(10, 5);

    }
}
