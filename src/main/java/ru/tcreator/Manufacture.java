package ru.tcreator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Manufacture extends Thread {
  static ArrayList<Car> storage = new ArrayList<>();
  final int MAX_VALUE_IN_STORAGE = 5;
  @Override
  public void run() {
    final int DELAY_CHECK_STORAGE = 200;      // задержка проверки склада на укомплектованность

    while (true) {
      if (isInterrupted()) {
        break;
      }
      try {
        Thread.sleep(DELAY_CHECK_STORAGE);
        if(storage.size() < MAX_VALUE_IN_STORAGE ) {
          createCar();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
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
  static public ArrayList<Car> getCars() {

    ArrayList<Car> createdCars = storage.stream()
            .filter(car -> car.getStatus() == Status.DELIVERED)
            .collect(Collectors.toCollection(ArrayList::new));
    if (createdCars.size() < 1) { // коли кареты готовые есть - отослать покупателю, а коли нет, отослать покупателя
      System.out.println("на складе пусто");
      return new ArrayList<>();
    }

    for (int i = createdCars.size() - 1; i >= 0; i--) {
      storage.remove(i);
    }
    System.out.println("Со склада изъято " + createdCars.size() + " машин " + "остаток на складе " + storage.size());
    return createdCars;
  }

}
