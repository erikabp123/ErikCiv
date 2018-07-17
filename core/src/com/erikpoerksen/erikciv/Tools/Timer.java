package com.erikpoerksen.erikciv.Tools;

public class Timer {

    private long start;
    private long stop;
    private long time;

    public void start(){
        start = System.currentTimeMillis();
    }

    public void stop(){
        stop = System.currentTimeMillis();
        time = stop - start;
    }

    public long getTime(){
        return time;
    }

    public void clear(){
        start = 0;
        stop = 0;
        time = 0;
    }






}
