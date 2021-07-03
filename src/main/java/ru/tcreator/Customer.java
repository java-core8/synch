package ru.tcreator;

public class Customer implements Runnable {
  final private AutoShop shop;
  Customer( AutoShop shop ) {
    this.shop = shop;
  }


  @Override
  public void run() {
    if(!Thread.currentThread().isInterrupted()) {
      try {
        shop.sale();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(Thread.currentThread().getName() + " покинул салон");
  }

}
