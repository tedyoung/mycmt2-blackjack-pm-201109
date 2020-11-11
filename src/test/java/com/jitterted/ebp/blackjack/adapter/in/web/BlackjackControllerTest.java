package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

  @Test
  public void startGameDealsInitialCardsToPlayerAndDealer() throws Exception {
    Game game = new Game();
    BlackjackController blackjackController = new BlackjackController(game);

    blackjackController.startGame();

    assertThat(game.playerHand().cards())
        .hasSize(2);
    assertThat(game.dealerHand().cards())
        .hasSize(2);
  }

  @Test
  public void gamePopulatesView() throws Exception {
    Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                         new Card(Suit.HEARTS, Rank.TWO),
                                         new Card(Suit.DIAMONDS, Rank.KING),
                                         new Card(Suit.CLUBS, Rank.THREE)));
    Game game = new Game(stubDeck);
    BlackjackController blackjackController = new BlackjackController(game);
    blackjackController.startGame();

    Model model = new ConcurrentModel();
    blackjackController.game(model);

    GameView hands = (GameView) model.getAttribute("gameView");
    assertThat(hands.getPlayerCards())
        .containsExactly("10♦", "K♦");
    assertThat(hands.getDealerCards())
        .containsExactly("2♥", "3♣");
  }

  @Test
  public void hitCommandDealsCardToPlayer() throws Exception {
    Game game = new Game();
    BlackjackController blackjackController = new BlackjackController(game);
    blackjackController.startGame();

    blackjackController.hitCommand();

    assertThat(game.playerHand().cards())
        .hasSize(3);
  }

  @Test
  public void hitAndPlayerGoesBustRedirectsToOutcomePage() throws Exception {
    Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                         new Card(Suit.HEARTS, Rank.TWO),
                                         new Card(Suit.DIAMONDS, Rank.KING),
                                         new Card(Suit.CLUBS, Rank.THREE),
                                         new Card(Suit.DIAMONDS, Rank.SEVEN)));

    Game game = new Game(stubDeck);
    BlackjackController blackjackController = new BlackjackController(game);
    blackjackController.startGame();

    String redirect = blackjackController.hitCommand();

    assertThat(redirect)
        .isEqualTo("redirect:/done");
  }

  @Test
  public void donePageShowsFinalGameView() throws Exception {
    Game game = new Game();
    BlackjackController blackjackController = new BlackjackController(game);
    blackjackController.startGame();

    Model model = new ConcurrentModel();
    blackjackController.done(model);

    assertThat(model.getAttribute("gameView"))
        .isInstanceOf(GameView.class);
    String outcome = (String) model.getAttribute("outcome");
    assertThat(outcome)
        .isNotBlank();
  }

  @Test
  public void standCommandResultsInGamePlayerIsDone() throws Exception {
    Game game = new Game();
    BlackjackController blackjackController = new BlackjackController(game);
    blackjackController.startGame();

    String redirect = blackjackController.standCommand();

    assertThat(redirect)
        .isEqualTo("redirect:/done");

    assertThat(game.isPlayerDone())
        .isTrue();
  }

}