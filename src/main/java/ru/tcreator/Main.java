package ru.tcreator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    AutoShop autoShop = new AutoShop();

    executorService.execute(new Manufacture()); // Работает фабрика производитель карет

    executorService.execute(new Customer(autoShop)); // приходят товарищи покупатели и владельцы кэбов
    executorService.execute(new Customer(autoShop));
    executorService.execute(new Customer(autoShop));
    executorService.execute(new Customer(autoShop));
    executorService.execute(new Customer(autoShop));
    executorService.execute(new Customer(autoShop));

    executorService.execute(autoShop);
    executorService.shutdown();
  }
}
