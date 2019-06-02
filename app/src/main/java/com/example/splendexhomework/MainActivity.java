package com.example.splendexhomework;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.splendexhomework.Model.Card;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

  private ArrayList<Integer> numbers;
  private ArrayList<Card> cards;
  private ArrayList<Card> flippedCards;

  private int numberOfPairs = 10;

  private GridLayout gridLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    gridLayout = findViewById(R.id.glDeck);

    addCardNumbers();
    addCards();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int menuItemId = item.getItemId();

    switch (menuItemId) {
      case R.id.menuitem_restart:
        reinit();
        break;
      case R.id.menuitem_set_deck_size:
        setDeckSize();
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  private int getCardIndex(Button button) {
    for (int i = 0; i < cards.size(); i++) {
      boolean isMatch = button.equals(cards.get(i).getButton());

      if (isMatch)
        return i;
    }

    return -1;
  }

  private void onClickCard(View v) {
    if (flippedCards == null)
      flippedCards = new ArrayList<>();

    int index = getCardIndex((Button)v);

    if (flippedCards.size() > 1)
    {
      flipDown((Button) v, index);
    } else if (flippedCards.size() < 1) {
      flip((Button) v, index);
    } else if (flippedCards.size() == 1) {
      // Not the same card
      if (!(flippedCards.get(0).getId() == cards.get(index).getId())) {
        flip((Button) v, index);

        // Remove cards if match
        if (flippedCards.size() > 0 && flippedCards.get(0).getNumber() == flippedCards.get(1).getNumber()) {
          removeFlippedCards();
        }
      } else {
        flip((Button) v, index);
      }
    }
  }

  private void removeFlippedCards() {
    for (Card item : flippedCards) {
      gridLayout.removeView(item.getButton());
    }

    flippedCards.clear();
  }

  private void flipDown(Button v, int index) {
    if (!v.getText().equals("")) {
      v.setText("");
      flippedCards.remove(cards.get(index));
    }
  }

  private void flip(Button v, int index) {
    if (v.getText().equals("")) {
      String text = Integer.toString(cards.get(index).getNumber());

      v.setText(text);
      flippedCards.add(cards.get(index));
    } else {
      v.setText("");
      flippedCards.remove(cards.get(index));
    }
  }

  private void addCardNumbers() {
    if (numbers == null)
      numbers = new ArrayList<>();

    for (int i = 1; i < numberOfPairs+1; i++) {
      numbers.add(i);
    }
  }

  private void addCards() {
    if (cards == null)
      cards = new ArrayList<>();

    int numberToGetIndex = 0;

    for (int i = 0; i < numbers.size()*2; i++) {
      int number = numbers.get(numberToGetIndex);
      Button btCard = new Button(this);

      btCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onClickCard(v);
        }
      });

      cards.add(new Card(0, 0, numbers.get(numberToGetIndex), btCard, i));

      if (numberToGetIndex == numbers.size()-1)
        numberToGetIndex = 0;
      else
        numberToGetIndex++;

      Collections.shuffle(cards);
    }

    for (Card card : cards) {
      gridLayout.addView(card.getButton());
    }
  }

  private void reinit() {
    clearDeck();
    addCardNumbers();
    addCards();
  }

  private void reinit(int numberOfPairs) {
    this.numberOfPairs = numberOfPairs;
    reinit();
  }

  private void clearDeck() {
    if (numbers != null)
      numbers.clear();
    if (cards != null)
      cards.clear();
    if (flippedCards != null)
      flippedCards.clear();
    if (gridLayout != null)
      gridLayout.removeAllViews();
  }

  private void setDeckSize() {
    AlertDialog.Builder b = new AlertDialog.Builder(this);
    b.setTitle(R.string.set_deck_size);
    String[] options = {"3 x 2", "4 x 2", "5 x 2", "6 x 2", "7 x 2", "8 x 2", "9 x 2", "10 x 2"};
    b.setItems(options, new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int index) {

        dialog.dismiss();
        switch(index){
          case 0:
            reinit(3);
            break;
          case 1:
            reinit(4);
            break;
          case 2:
            reinit(5);
            break;
          case 3:
            reinit(6);
            break;
          case 4:
            reinit(7);
            break;
          case 5:
            reinit(8);
            break;
          case 6:
            reinit(9);
            break;
          case 7:
            reinit(10);
            break;
        }
      }

    });

    b.show();
  }
}
