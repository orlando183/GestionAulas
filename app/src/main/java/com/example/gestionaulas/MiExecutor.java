package com.example.gestionaulas;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class MiExecutor {
    private static Executor instance;
    private static final Object LOCK=new Object();

    public static Executor getInstance(){
        if(instance==null){
            synchronized (LOCK){
                instance= Executors.newSingleThreadExecutor();
            }
        }
        return instance;
    }

}
