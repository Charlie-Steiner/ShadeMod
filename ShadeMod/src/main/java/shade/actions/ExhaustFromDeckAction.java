package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustFromDeckAction extends AbstractGameAction {
	private AbstractPlayer player;
	private final int numberOfCards;
	private static final UIStrings uiStrings = CardCrawlGame.languagePack
			.getUIString("Shade:ExhaustFromDeckAction");
	public static final String[] TEXT = uiStrings.TEXT;

	public ExhaustFromDeckAction(int number) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	    this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
		this.numberOfCards = number;
		this.player = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
	}


	public void update() {
		if (this.duration == this.startDuration) {
			if (this.player.drawPile.isEmpty() || this.numberOfCards <= 0) {
				this.isDone = true;
				return;
			}
			CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : this.player.drawPile.group) {
				temp.addToTop(c);
			}
			temp.sortAlphabetically(true);
			temp.sortByRarityPlusStatusCardType(false);
			if (this.numberOfCards == 1) {
				AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
			}else{
				AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
			}

			tickDuration();

			return;
		}
		if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				this.player.drawPile.moveToExhaustPile(c);
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}
}
