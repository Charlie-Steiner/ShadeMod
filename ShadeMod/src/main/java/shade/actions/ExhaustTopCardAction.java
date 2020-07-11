package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
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

public class ExhaustTopCardAction extends AbstractGameAction {
	private AbstractPlayer player;
	private final int numberOfCards;

	public ExhaustTopCardAction(int number) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	    this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
		this.numberOfCards = number;
		this.player = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
	}


	public void update() {
		if (this.duration == this.startDuration) {
		      if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0 || this.numberOfCards==0) {
		          this.isDone = true;
		          
		          return;
		        }else if (AbstractDungeon.player.drawPile.isEmpty()) {
		          AbstractDungeon.actionManager.addToTop(new ExhaustTopCardAction(this.numberOfCards));
		          AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
		        }else{
		          AbstractDungeon.player.drawPile.moveToExhaustPile(AbstractDungeon.player.drawPile.getTopCard());
		          AbstractDungeon.actionManager.addToTop(new ExhaustTopCardAction(this.numberOfCards-1));
		        }
		        this.isDone = true;
		}
	}
}
