package ru.tcreator;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AutoShop implements Runnable {
  private ArrayList<Car> autoShop_storage = new ArrayList<>();
  private Map<Long, String> storeQueue = new HashMap<>();

  public Car sale() throws InterruptedException {
    synchronized(this) {

      while (true) {
        TimeUnit.MILLISECONDS.sleep(5000);
        int size_shop = autoShop_storage.size();
        String currentThreadName = Thread.currentThread().getName();
        try {
          if (size_shop > 0) {
            notify();
            System.out.println("Авто куплено покупателем: " + currentThreadName);
            Car buyingCar = autoShop_storage.get(size_shop - 1);
            autoShop_storage.remove(size_shop - 1);
            printQue();
            return buyingCar;
          } else {
            System.out.println("Ожидает : " + currentThreadName);
            storeQueue.put(Thread.currentThread().getId(), currentThreadName);
            printQue();
            wait();
          }

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void printQue() {
    StringBuilder builder = new StringBuilder("В очереди: ").append("\n");
    int countQue = 0;
    for (Map.Entry el : storeQueue.entrySet()) {
      builder.append(++countQue)
              .append(") ")
              .append(el.getValue())
              .append("\n");
    }
    System.out.println(builder);
  }

  @Override
  public void run() {
    while(true) {
      final int WAITING_CHECK = 15000;
      try {
        int size_park = autoShop_storage.size();
        if(size_park < 5) {
          ArrayList<Car> tmpStorage = Manufacture.getCars();
          autoShop_storage.addAll(tmpStorage);
          System.out.println("На стоянке магазина " + autoShop_storage.size() + " карет");
        }
        TimeUnit.MILLISECONDS.sleep(WAITING_CHECK);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
