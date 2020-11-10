package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Hand;
import com.jitterted.ebp.blackjack.domain.Rank;
import org.fusesource.jansi.Ansi;

import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleCard {

  public static String display(Card card) {
    String[] lines = new String[7];
    lines[0] = "┌─────────┐";
    lines[1] = String.format("│%s%s       │", card.rank().display(), card.rank() == Rank.TEN ? "" : " ");
    lines[2] = "│         │";
    lines[3] = String.format("│    %s    │", card.suit().symbol());
    lines[4] = "│         │";
    lines[5] = String.format("│       %s%s│", card.rank() == Rank.TEN ? "" : " ", card.rank().display());
    lines[6] = "└─────────┘";

    Ansi.Color cardColor = card.suit().isRed() ? Ansi.Color.RED : Ansi.Color.BLACK;
    return ansi()
        .fg(cardColor).toString()
        + String.join(ansi().cursorDown(1)
                            .cursorLeft(11)
                            .toString(), lines);
  }

  public static String displayFirstCard(Hand hand) {
    return display(hand.cards().get(0));
  }

  public static String cardsAsString(Hand hand) {
    return hand.cards().stream()
               .map(ConsoleCard::display)
               .collect(Collectors.joining(
                      ansi().cursorUp(6).cursorRight(1).toString()));
  }
}
