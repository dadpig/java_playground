package com.tlopesdeoliveira.pocs;

import java.util.Deque;
import java.util.LinkedList;

public class MySimpleThreadPool {

    private MySimpleThread[] threads;
    private Deque<Runnable> queue;

    public MySimpleThreadPool(Integer poolSize){
        queue = new LinkedList<>();
        threads = new MySimpleThread[poolSize];
        for (int i=0; i<poolSize; i++){
            threads[i] = new MySimpleThread("Thread-"+i);
            threads[i].start();
        }
    }

    public void addTask(Runnable task){
        synchronized (queue){
            queue.addLast(task);
            queue.notify();
        }
    }

    public static void main(String[] args) {
        MySimpleThreadPool pool = new MySimpleThreadPool(3);
        int queueSize = 100;

        for(int i=0; i<queueSize; i++){
            int ind = i;
            pool.addTask(() -> {
                int taskHeight = 1000000;
                if(ind>0){taskHeight += taskHeight*ind;}
                //work cost
                for(int k = 0; k<taskHeight; k++){}
                String message =
                        Thread.currentThread().getName()
                                + ": Task "+ind
                                + ": Height "+taskHeight;
                System.out.println(message);
            });
        }

        pool.addTask(() -> {
            int taskHeight = 1000000;
            //work cost
            for(int k = 0; k<taskHeight; k++){}
            String message =
                    Thread.currentThread().getName()
                            + ": Task "+100
                            + ": Height "+taskHeight;
            System.out.println(message);
        });
    }
    class MySimpleThread extends Thread{
        private String name;

        public MySimpleThread(String name){this.name = name;}
        public void run(){
            Runnable task;
            try {
                while (true) {
                    synchronized (queue) {
                        while ((queue.isEmpty())) {
                            queue.wait(1000);
                        }
                        task = (Runnable) queue.removeFirst();
                    }
                    task.run();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
