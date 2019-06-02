package com.example.splendexhomework.Model;

import android.widget.Button;

public class Card {

  private int x;
  private int y;
  private int number;
  private Button button;
  private int id;

  public Card(int x, int y, int number, Button button, int id) {
    this.x = x;
    this.y = y;
    this.number = number;
    this.button = button;
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
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

//  @Override
//  public boolean equals(Object obj) {
//    return (((Button)obj).getId()) == (button.getId());
//  }
}
