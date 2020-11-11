package com.jitterted.ebp.blackjack.domain;

import java.util.Iterator;
import java.util.List;

public class StubDeck extends Deck {
  private final Iterator<Card> iterator;

  public StubDeck(List<Card> cards) {
    this.iterator = cards.listIterator();
  }

  @Override
  public Card draw() {
    return iterator.next();
  }
}
