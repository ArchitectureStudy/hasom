package com.hasom.mvc.base;

import android.app.Application;

import com.hasom.mvc.base.Observer.CustomObserver;
import com.hasom.mvc.base.Observer.Observable;

import java.util.ArrayList;

/**
 * Created by leejunho on 2017. 1. 24..
 */

public class GithubApplication extends Application implements Observable {

    private ArrayList<CustomObserver> observers;            // 등록할 옵저버 리스트
    private Object obj;
    private static GithubApplication githubApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        githubApplication = this;
        observers = new ArrayList<>();
    }


    public void addObserver(CustomObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(CustomObserver observer) {
        int i = observers.indexOf(observer);

        if (i >= 0){
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        synchronized (obj) {
            for(int i=0; i < observers.size(); i++){
                CustomObserver observer = observers.get(i);
                observer.update(obj);
            }
        }
    }

    public  void changeModel(Object model) {
        this.obj = model;
        notifyObservers();
    }

    public static GithubApplication getGithubApplication() {
        return githubApplication;
    }

}
