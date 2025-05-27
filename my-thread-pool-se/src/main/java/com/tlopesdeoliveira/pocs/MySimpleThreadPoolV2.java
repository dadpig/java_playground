package com.tlopesdeoliveira.pocs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MySimpleThreadPoolV2 {
//concurrentLinkedQueue best way
//LinkedBlockedQueue
    //ConcurrentLinkedQueue
       // LinkedBlockingQueue
    private MySimpleThread[] threads;
    private BlockingQueue<Runnable> queue;

    public MySimpleThreadPoolV2(Integer poolSize){
        queue = new LinkedBlockingQueue<>();
        threads = new MySimpleThread[poolSize];
        for (int i=0; i<poolSize; i++){
            threads[i] = new MySimpleThread("Thread-"+i);
            threads[i].start();
        }
    }

    public void addTask(Runnable task){
        /*diferente do metodo sincronizado que bloqueia o metodo
        inteiro e faz com que as threads fiquem em wait, o ReentrantLock
        apenas faz o lock da condicao desejada ou da operacao critica
        oferece operacao para liberar as threads em wait e consegue obter o lock sem ficar aguardando
         */
       queue.offer(task);
    }

    public static void main(String[] args) {

        MySimpleThreadPoolV2 pool = new MySimpleThreadPoolV2(3);
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
                    if (!queue.isEmpty()) {
                        /*
                        obtem o lock sem interromper a thread imediatamente,
                        se a trhead estiver aguardando atualiza o contador
                         */
                        task = (Runnable) queue.take();
                        task.run();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
