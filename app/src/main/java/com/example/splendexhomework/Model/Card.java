package com.example.splendexhomework.Model;

import android.widget.Button;

public class Card {

  private int number;
  private Button button;
  private int id;

  public Card(int number, Button button, int id) {
    this.number = number;
    this.button = button;
    this.id = id;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Button getButton() {
    return button;
  }

  public void setButton(Button button) {
    this.button = button;
  }

  public int getId() {
    return id;
  }
}
