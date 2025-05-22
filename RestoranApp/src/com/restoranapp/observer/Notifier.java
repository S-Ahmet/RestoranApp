package com.restoranapp.observer;

import java.util.ArrayList;
import java.util.List;

public class Notifier {
    private static final List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void notifyAllObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
