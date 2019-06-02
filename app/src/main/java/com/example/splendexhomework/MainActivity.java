package com.example.splendexhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final GridLayout gridLayout = findViewById(R.id.glDeck);

    if (numbers == null)
      numbers = new ArrayList<>();

    // TODO:
    // 1. Gen model
    // 2. Add elements from model
    // 3. Show/hide text as needed
    for (int i = 1; i < 11; i++) {
      numbers.add(i);
    }

    if (cards == null)
      cards = new ArrayList<>();

    int numberToGetIndex = 0;

    for (int i = 0; i < numbers.size()*2; i++) {
      int number = numbers.get(numberToGetIndex);
      Button btCard = new Button(this);

      btCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // Check match
          if (flippedCards == null)
            flippedCards = new ArrayList<>();

          int index = getCardIndex((Button)v);

          if (flippedCards.size() > 1)
          {
            // Do nothing
          } else if (flippedCards.size() < 1) {

            // Add card
            flippedCards.add(cards.get(index));

            // Flip
            if (((Button)v).getText().equals("")) {
              String text = Integer.toString(cards.get(index).getNumber());

              ((Button)v).setText(text);
            } else
              ((Button)v).setText("");
          } else if (flippedCards.size() == 1) {
            // Not the same card
            if ((flippedCards.get(0).getId() == cards.get(index).getId())) {
              // Add card and check match
              flippedCards.add(cards.get(index));

              // Flip
              if (((Button)v).getText().equals("")) {
                String text = Integer.toString(cards.get(index).getNumber());

                ((Button)v).setText(text);
              } else
                ((Button)v).setText("");

              // Remove cards if match
              if (flippedCards.get(0).getNumber() == flippedCards.get(1).getNumber()) {
                for (Card item : flippedCards) {
                  gridLayout.removeView(item.getButton());
                }

                flippedCards.clear();
              } else {
                // Flip
                for (Card card : flippedCards) {
                  String text = Integer.toString(card.getNumber());

                  card.getButton().setText(text);
                }
              }
            } else {
              // Flip
              if (((Button)v).getText().equals("")) {
                String text = Integer.toString(cards.get(index).getNumber());

                ((Button)v).setText(text);
              } else
                ((Button)v).setText("");

//              flippedCards.clear();
            }
          }

          //          v.setBackgroundColor(Color.RED);
        }
      });

      btCard.setText(Integer.toString(number));

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

  private int getCardIndex(Button button) {
    for (int i = 0; i < cards.size(); i++) {
      boolean isMatch = button.equals(cards.get(i).getButton());

      if (isMatch)
        return i;
    }

    return -1;
  }
}
