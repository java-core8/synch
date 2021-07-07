package ru.tcreator;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Manufacture extends Thread {
  static CopyOnWriteArrayList<Car> storage = new CopyOnWriteArrayList<>();
  final int MAX_VALUE_IN_STORAGE = 5; // количество машигн на складе производства

  @Override
  public void run() {
    while (storage.size() < MAX_VALUE_IN_STORAGE) {
      createCar();
    }
    System.out.println("Завод встал, склад переполнен");
  }

  /**
   * Создаём машины
   */
  protected void createCar() {
    final int DELAY_STAGES = 50;                  // задержка между стадиями производства
    while (storage.size() != MAX_VALUE_IN_STORAGE) {
      System.out.print("Начинаем создание кареты ");
      Car newCar = new Car();
      int carProdStatus = 0;                      // аккумулятор процесса сборки
      while(carProdStatus++ < 100) {              // эмулируем проуесс сборки и отправки на склад
        try {
          newCar.setProcess(carProdStatus);
          System.out.print(".");
          Thread.sleep(DELAY_STAGES);
          if(carProdStatus % 25 == 0) { // эмулируем вывод статуса
            System.out.print(newCar.getStatus());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println();
      storage.add(newCar);
    }
  }

  /**
   * Проверяем есть готовые кареты на складе и возвращаем список
   * @return {@link ArrayList<Car>}
   */
  static public synchronized ArrayList<Car> getCars() {

    ArrayList<Car> createdCars = storage.stream()
            .filter(car -> car.getStatus() == Status.DELIVERED)
            .collect(Collectors.toCollection(ArrayList::new));
    if (createdCars.size() < 1) {           // коли кареты готовые есть - отослать в салон
      return null;
    }

    storage.subList(0, createdCars.size()).clear();

    System.out.println("Со склада изъято " + createdCars.size() + " машин " + "остаток на складе " + storage.size());
    return createdCars;
  }

}
