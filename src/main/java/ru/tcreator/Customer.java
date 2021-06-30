package ru.tcreator;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
  protected Car carOwner = null;
  final private AutoShop shop;
  Customer(  AutoShop shop ) {
    this.shop = shop;
  }

  @Override
  public void run() {
    try {
      carOwner = shop.sale();
      if(carOwner != null) {
        return;
      }
      if(Thread.currentThread().isInterrupted()) {
        System.out.println("Куплено: " + Thread.currentThread().getName());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
