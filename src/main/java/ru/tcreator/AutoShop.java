package ru.tcreator;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AutoShop implements Runnable {
  private final ArrayList<Car> autoShop_storage = new ArrayList<>();
  private final Lock lock = new ReentrantLock(true);
  private final Condition condition = lock.newCondition();

  public void sale() throws InterruptedException {
    try {
      String currentThreadName = Thread.currentThread().getName();
      lock.lock();
      try {

        if(autoShop_storage.size() == 0) {
          System.out.println("Ожидает : " + currentThreadName);
          condition.await();
        }
        System.out.println("Авто куплено покупателем: " + currentThreadName);
        autoShop_storage.remove(autoShop_storage.size() - 1);
          Thread.currentThread().isInterrupted();

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void run() {
    while (autoShop_storage.size() < 5) {
      final int WAITING_CHECK = 1000;
//      try {
      int size_park = autoShop_storage.size();
      if (size_park < 5) {
        ArrayList<Car> tmpStorage = Manufacture.getCars();
        if (tmpStorage != null) {
          lock.lock();
          try {
            autoShop_storage.addAll(tmpStorage);
            System.out.println("На стоянке магазина " + autoShop_storage.size() + " карет");
            condition.signal();
          } finally {
            lock.unlock();
          }
        }
      }
//        TimeUnit.MILLISECONDS.sleep(WAITING_CHECK);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }

    }
    System.out.println("Стоянка автосалона заполнена. ");
  }

}
