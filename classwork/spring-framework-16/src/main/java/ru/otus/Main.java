package ru.otus;


import io.reactivex.Observable;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Main {

    public static void main(String[] args) {
        Observable.range(20, 5)
                // TODO: add code here
                .subscribe(System.out::println);
    }
}
