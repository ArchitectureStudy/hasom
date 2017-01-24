package com.hasom.mvc.base.Observer;

/**
 * Created by leejunho on 2017. 1. 24..
 */

public interface Observable {

    public void addObserver(CustomObserver observer);
    public void deleteObserver(CustomObserver observer);
    public void notifyObservers();

}