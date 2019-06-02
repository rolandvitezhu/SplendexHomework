package com.example.splendexhomework;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.splendexhomework.Model.Card;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

  private ArrayList<Integer> numbers;
  private ArrayList<Card> cards;
  private ArrayList<Card> flippedCards;

  private int numberOfPairs = 10;
  private int tryCount = 0;
  private int topScore = 0;

  private GridLayout gridLayout;
  private TextView tvTryCount;
  private TextView tvTopScore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    gridLayout = findViewById(R.id.glDeck);
    tvTryCount = findViewById(R.id.tvTryCount);
    tvTopScore = findViewById(R.id.tvTopScore);

    init();
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
        init();
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
    flipCard(v);

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
        } else {
          incrementTryCount();
        }
      } else {
        flip((Button) v, index);
      }
    }
  }

  private void flipCard(View v) {
    AnimatorSet set = (AnimatorSet) AnimatorInflater.
        loadAnimator(this, R.animator.card_flip_left_in);
    set.setTarget(v);
    set.start();
  }

  private void removeFlippedCards() {
    for (Card card : flippedCards) {
      cards.remove(card);
      gridLayout.removeView(card.getButton());
    }

    flippedCards.clear();

    if (cards.size() < 1)
      updateTopScore();
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
      Button btCard = new Button(this);

      btCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onClickCard(v);
        }
      });

      cards.add(new Card(numbers.get(numberToGetIndex), btCard, i));

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

  private void init() {
    clearDeck();
    addCardNumbers();
    addCards();
    setTryCount();
    setTopScore();
  }

  private void init(int numberOfPairs) {
    this.numberOfPairs = numberOfPairs;
    init();
  }

  private void clearDeck() {
    if (numbers != null)
      numbers.clear();
    if (cards != null)
      cards.clear();
    if (flippedCards != null)
      flippedCards.clear();
    tryCount = 0;
    setTryCount();
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
            init(3);
            break;
          case 1:
            init(4);
            break;
          case 2:
            init(5);
            break;
          case 3:
            init(6);
            break;
          case 4:
            init(7);
            break;
          case 5:
            init(8);
            break;
          case 6:
            init(9);
            break;
          case 7:
            init(10);
            break;
        }
      }

    });

    b.show();
  }

  private void setTryCount() {
    if (tvTryCount != null)
      tvTryCount.setText(getNumberText(R.string.count_of_tries, tryCount));
  }

  private void setTopScore() {
    if (tvTopScore != null)
      tvTopScore.setText(getNumberText(R.string.top_score, topScore));
  }

  private void incrementTryCount() {
    tryCount++;
    setTryCount();
  }

  private String getNumberText(int prefixId, int number) {
    String prefix = getString(prefixId);

    return prefix.concat(" ").concat(Integer.toString(number));
  }

  private void updateTopScore() {
    if (topScore == 0 || tryCount < topScore) {
      topScore = tryCount;
      setTopScore();
    }
  }
}
