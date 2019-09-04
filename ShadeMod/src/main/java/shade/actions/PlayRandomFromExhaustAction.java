package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import shade.actions.CleanUpCardAction;

import shade.ShadeMod;

import java.util.ArrayList;
import java.util.Iterator;



public class PlayRandomFromExhaustAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private final boolean toDiscard;
	private final AbstractCard.CardType type;
	
	public PlayRandomFromExhaustAction(AbstractCard.CardType type, boolean toDiscard) {
		this.misfits = new ArrayList();
		this.toDiscard = toDiscard;
		this.p = AbstractDungeon.player;
		this.type=type;
		setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	private ArrayList<AbstractCard> misfits;
  
	public void update() {
		if (this.p.exhaustPile.isEmpty()) {
		    this.isDone = true;
		    return;
		}
		
		for (Iterator<AbstractCard> ex = this.p.exhaustPile.group.iterator(); ex.hasNext(); ) {
			AbstractCard derp = (AbstractCard)ex.next();
			if (!derp.type.equals(this.type)) {
				this.misfits.add(derp);
				ex.remove();
			} 
		}
		
		if (this.p.exhaustPile.isEmpty()) {
			this.p.exhaustPile.group.addAll(misfits);
			this.misfits.clear();
		    this.isDone = true;
		    return;
		}
		
		AbstractCard c = this.p.exhaustPile.getRandomCard(true);
		AbstractMonster t = AbstractDungeon.getMonsters().getRandomMonster(true);
		
		
		c.freeToPlayOnce = true;
		c.purgeOnUse=true;
		

		ShadeMod.logger.info("Targeting a monster with a random " + c.type.toString());
		c.applyPowers();
		c.freeToPlayOnce=true;
		c.purgeOnUse=true;
		AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c));
		AbstractDungeon.actionManager.addToTop(new QueueCardAction(c, t));
		if(this.toDiscard) {
			AbstractDungeon.actionManager.addToTop(new CleanUpCardAction(c, "exhaust", "discard", -1));
		}else {
			AbstractDungeon.actionManager.addToTop(new CleanUpCardAction(c, "exhaust", "nowhere", -1));
		}
		if (!Settings.FAST_MODE) {
			AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
		} else {
			AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
		}

		this.p.exhaustPile.group.addAll(misfits);
		this.misfits.clear();
		this.isDone = true;
	    
	    tickDuration();
	}
}