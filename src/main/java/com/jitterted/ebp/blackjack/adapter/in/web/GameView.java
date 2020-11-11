package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Game;

import java.util.ArrayList;
import java.util.List;

public class GameView {
  private List<String> dealerCards = new ArrayList<>();
  private List<String> playerCards = new ArrayList<>();

  public GameView(Game game) {
    for (Card card : game.dealerHand().cards()) {
      dealerCards.add(card.rank().display() + card.suit().symbol());
    }
    for (Card card : game.playerHand().cards()) {
      playerCards.add(card.rank().display() + card.suit().symbol());
    }
  }

  public List<String> getDealerCards() {
    return dealerCards;
  }

  public void setDealerCards(List<String> dealerCards) {
    this.dealerCards = dealerCards;
  }

  public List<String> getPlayerCards() {
    return playerCards;
  }

  public void setPlayerCards(List<String> playerCards) {
    this.playerCards = playerCards;
  }
}
