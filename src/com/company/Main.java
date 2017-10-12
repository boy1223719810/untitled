package com.company;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.company.Producer.queuesize;

public class Main {
    public static void main(String[] args) {
        Setup a0 = new Setup();
        a0.main();
    }
}
class Producer implements Runnable {
    private final BlockingQueue queue;
    public static int queuesize = 50;  //写int 是原子操作 不需要加锁;
    private int Pnumber;
    Producer(BlockingQueue q) { queue = q; }
    public void run() {
        try {
            while (true) {
              if (queuesize<50){

                queue.put(produce());
                System.out.println("生产"+(Pnumber+1)+"个");
                java.util.Random r=new java.util.Random();
                int Ptime=r.nextInt();
                if(Ptime<0){Ptime=-Ptime;}
                Thread.sleep(Ptime%300);
                queuesize+=1;
                Pnumber++;
              }
                else{
                  System.out.println("队列已满，等待中");
                  Thread.sleep(1000);
              }
              }
        } catch (InterruptedException ex) { }
    }
    Object produce() {
        return 0;
    }
}
class Consumer implements Runnable {
    private final BlockingQueue queue;
    private int Cnumber;
    Consumer(BlockingQueue q) { queue = q; }
    public void run() {
        try {
            while (true) {
                /*while*/if(!(queue.size()==0)){
                    consume(queue.take());
                System.out.println("消费"+(Cnumber+1)+"个");
                    queuesize-=1;
                    Cnumber++;
                }
                java.util.Random r=new java.util.Random();
                int Ctime=r.nextInt();
                if(Ctime<0){Ctime=-Ctime;}
                queuesize-=1;
                Cnumber++;
                Thread.sleep(Ctime%3000);
            }
        } catch (InterruptedException ex) {   }
    }
    void consume(Object x) {
    }
}
class Setup {
    void main() {
        BlockingQueue q = new LinkedBlockingQueue();
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}