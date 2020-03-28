package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import shade.ShadeMod;

import java.util.ArrayList;
import java.util.Iterator;

public class SelfBurialAction extends AbstractGameAction {
	private AbstractPlayer p;
	private static final UIStrings uiStrings = CardCrawlGame.languagePack
			.getUIString("Shade:ReturnExhaustedToDeckAction");
	public static final String[] TEXT = uiStrings.TEXT;

	public SelfBurialAction() {
		this.exhausts=new ArrayList();
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	private ArrayList<AbstractCard> exhausts;

	public void update() {
		AbstractCard derp;
		if(!this.p.exhaustPile.isEmpty()) {
			for(int i=this.p.exhaustPile.size();i>0;i--) {
				derp = this.p.exhaustPile.getTopCard();
				this.exhausts.add(derp);
				this.p.exhaustPile.group.remove(derp);
			}
		}
		
		for(int i=this.p.drawPile.size();i>0;i--) {
			this.p.drawPile.moveToExhaustPile(this.p.drawPile.getTopCard());
		}
		
		for(int i=this.p.discardPile.size();i>0;i--) {
			this.p.discardPile.moveToExhaustPile(this.p.discardPile.getTopCard());
		}
		
		for(int i=this.p.hand.size();i>0;i--) {
			this.p.hand.moveToExhaustPile(this.p.hand.getTopCard());
		}
		
		
		CardCrawlGame.sound.play("CARD_OBTAIN");
		for(int i=this.exhausts.size()-1;i>=0;i--) {
			this.p.drawPile.addToRandomSpot(exhausts.get(i).makeStatEquivalentCopy());
		}
		this.exhausts.clear();
		
		
		this.isDone = true;
	}
}