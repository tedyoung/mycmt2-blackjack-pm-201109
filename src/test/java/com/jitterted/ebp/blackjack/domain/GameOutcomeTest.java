package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

  private static final Suit DUMMY_SUIT = Suit.DIAMONDS;

  @Test
  public void playerBeatsDealer() throws Exception {
    Deck stubDeck = new StubDeck(List.of(new Card(DUMMY_SUIT, Rank.TEN),
                                         new Card(DUMMY_SUIT, Rank.TWO),
                                         new Card(DUMMY_SUIT, Rank.KING),
                                         new Card(DUMMY_SUIT, Rank.THREE)));
    Game game = new Game(stubDeck);
    game.initialDeal();

    assertThat(game.determineOutcome())
        .isEqualTo("You beat the Dealer! 💵");
  }

  @Test
  public void playerWinsWithBlackJack() throws Exception {
    Deck stubDeck = new StubDeck(List.of(new Card(DUMMY_SUIT, Rank.ACE),
                                         new Card(DUMMY_SUIT, Rank.TWO),
                                         new Card(DUMMY_SUIT, Rank.JACK),
                                         new Card(DUMMY_SUIT, Rank.THREE)));
    Game game = new Game(stubDeck);
    game.initialDeal();

    assertThat(game.determineOutcome())
        .isEqualTo("You won Blackjack! 💵");

  }

}