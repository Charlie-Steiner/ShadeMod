package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import shade.ShadeMod;
import shade.patches.AbstractCardEnum;
import shade.relics.Thing;

import java.util.ArrayList;
import java.util.Iterator;

public class SelfBurialAction extends AbstractGameAction {
	private AbstractPlayer p;
	private static final UIStrings uiStrings = CardCrawlGame.languagePack
			.getUIString("Shade:ReturnExhaustedToDeckAction");
	public static final String[] TEXT = uiStrings.TEXT;
	
	private ArrayList<AbstractCard> spares;
	private ArrayList<AbstractCard> exhausts;
	
	private int topExhaust  = 10;

	public SelfBurialAction() {
		this.exhausts=new ArrayList();
		this.spares=new ArrayList();
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	

	public void update() {
		int countdown = topExhaust;
		
		AbstractCard derp;
		if(!this.p.exhaustPile.isEmpty()) {
			for(int i=this.p.exhaustPile.size();i>0;i--) {
				derp = this.p.exhaustPile.getTopCard();
				if(!(derp.color.equals(AbstractCard.CardColor.COLORLESS) || derp.color.equals(AbstractCard.CardColor.CURSE))) {
					this.exhausts.add(derp);
				}else {
					this.spares.add(derp);
				}
				this.p.exhaustPile.group.remove(derp);
			}
		}
		
		for(int i=this.p.hand.size();i>0;i--) {
			this.p.hand.moveToExhaustPile(this.p.hand.getTopCard());
		}
		
		int tempCount;
		if(this.p.drawPile.size() > countdown) {
			tempCount=countdown;
			countdown=0;
		}else {
			tempCount = this.p.drawPile.size();
			countdown -= this.p.drawPile.size();
		}
		for(int i=0;i<tempCount;i++) {
			this.p.drawPile.moveToExhaustPile(this.p.drawPile.getTopCard());
		}
		
		if(this.p.discardPile.size() > countdown) {
			tempCount=countdown;
			countdown=0;
		}else {
			tempCount = this.p.discardPile.size();
			countdown -= this.p.discardPile.size();
		}
		for(int i=0;i<tempCount;i++) {
			this.p.discardPile.moveToExhaustPile(this.p.discardPile.getTopCard());
		}
		
		for(int i=this.spares.size()-1;i>=0;i--) {
			this.p.exhaustPile.addToRandomSpot(spares.get(i).makeStatEquivalentCopy());
		}
		this.spares.clear();
		
		
		CardCrawlGame.sound.play("CARD_OBTAIN");
		for(int i=this.exhausts.size()-1;i>=0;i--) {
			this.p.drawPile.addToRandomSpot(exhausts.get(i).makeStatEquivalentCopy());
			
			if(AbstractDungeon.player.hasRelic(Thing.ID))
			{
			   Thing t = (Thing)AbstractDungeon.player.getRelic(Thing.ID);
			   t.onExhume();
			}
		}
		this.exhausts.clear();
		
		AbstractDungeon.actionManager.addToTop(new ShuffleAction(AbstractDungeon.player.drawPile, false));
		if(p.discardPile.size()>0) {
			AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
		}
		
		this.isDone = true;
	}
}
