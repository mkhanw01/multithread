package com.waseem.multithread;

import java.util.Random;

/**
 * @author waseem.khan since 27/10/19.
 */
public class DemoThreadUnsafe {

  static Random random = new Random(System.currentTimeMillis());

  public static void main(String[] args) throws InterruptedException {
    final ThreadUnsafeCounter threadUnsafeCounter = new ThreadUnsafeCounter();
    Thread thread = new Thread(new Runnable() {
      public void run() {
        for (int i = 0; i < 100; i++) {
          threadUnsafeCounter.increment();
          DemoThreadUnsafe.sleepRandomlyForLessThan10Secs();
        }
      }
    });

    Thread thread1 = new Thread(new Runnable() {
      public void run() {
        for (int i = 0; i < 100; i++) {
          threadUnsafeCounter.decrement();
          DemoThreadUnsafe.sleepRandomlyForLessThan10Secs();
        }
      }
    });

    thread.start();
    thread1.start();
    //thread1.wait();


    thread.join();
    thread1.join();
    threadUnsafeCounter.printFinalCountValue();
  }

  public static void sleepRandomlyForLessThan10Secs() {
    try {
      Thread.sleep(random.nextInt(10));
    } catch (InterruptedException e) {
      System.out.println(" interrupt happen");
    }
  }
}

class ThreadUnsafeCounter {

  int count = 0;

  public void increment() {
    count++;
  }

  public void decrement() {
    count--;
  }

  void printFinalCountValue() {
    System.out.println("counter value is :" + count);
  }
}
