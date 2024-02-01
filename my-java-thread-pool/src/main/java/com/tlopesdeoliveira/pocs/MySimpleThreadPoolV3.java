package com.tlopesdeoliveira.pocs;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleThreadPoolV3 {
//melhoria colocar elas em sleep
    //pesquisar o melhor metodo dentro do metodo
    //mudar a implementacao de inicio revisar o loop ForkJoinPool
    private MySimpleThread[] threads;
    private Queue<Runnable> queue;

    public MySimpleThreadPoolV3(Integer poolSize){
        queue = new ConcurrentLinkedQueue<>();
        threads = new MySimpleThread[poolSize];
        for (int i=0; i<poolSize; i++){
            threads[i] = new MySimpleThread("Thread-"+i);
            threads[i].start();
        }
    }

    public void addTask(Runnable task){
        /*
        utiliza um algoritmo nao bloqueante para tratar blocos comportilhados baseado em operacoes atomicas
        uma tecnica chamada CaS (compare-and-swap) que faz as operacoes de avaliacao comparacao e atualizacao do valor
        em uma unica instrucao de hardware, o que garante que as demais threads terao acesso ao valor atual do registro,
        este registro precisa ser marcado como volatil para que o valor seja visivel para todos
         */
        queue.offer(task);
    }

    public static void main(String[] args) {
        MySimpleThreadPoolV3 pool = new MySimpleThreadPoolV3(3);
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
                        obtem o topo da pilha de maneira Atomica em unica instrucao
                        garantindo que nenhuma outra thread podera ler este item
                         */
                        task = (Runnable) queue.poll();
                        task.run();
                    }
                    Thread.sleep(100);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
