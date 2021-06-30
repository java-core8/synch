package ru.tcreator;

public class Car {
  protected int process;
  protected Status status = null;

  public Status getStatus() {
    return status;
  }

  private void setStatus() {
    if(process < 25) {
      status = Status.DEVELOPING;
    } else if (process < 50) {
      status = Status.ASSEMBLY;
    } else if (process < 75) {
      status = Status.CREATED;
    } else if (process <= 99) {
      status = Status.SENDING;
    } else if (process == 100) {
      status = Status.DELIVERED;
    }
  }

  public void setProcess(int process) {
    if(process > 0 && process <= 100) {
      this.process = process;
      setStatus();
    }
  }


}
