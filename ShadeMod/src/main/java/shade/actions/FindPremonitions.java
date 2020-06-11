package shade.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FindPremonitions extends AbstractGameAction {
  
  public FindPremonitions() {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_FAST;
  }

  
  public void update() {
    if (this.duration == Settings.ACTION_DUR_FAST) {
    	int nAllowed=10-AbstractDungeon.player.hand.size();
    	ArrayList<AbstractCard> temp = new ArrayList<AbstractCard>();
    	for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			temp.add(c);
    		}
    	}
    	for(AbstractCard t : temp) {
	        AbstractDungeon.player.hand.addToHand(t);
	        t.unhover();
	        t.setAngle(0.0F, true);
	        t.lighten(false);
	        t.drawScale = 0.12F;
	        t.targetDrawScale = 0.75F;
	        t.applyPowers();
	        AbstractDungeon.player.drawPile.removeCard(t);
    	}
    	
    	temp = new ArrayList<AbstractCard>();
    	for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			temp.add(c);
    		}
    	}
    	for(AbstractCard t : temp) {
	        AbstractDungeon.player.hand.addToHand(t);
	        t.unhover();
	        t.setAngle(0.0F, true);
	        t.lighten(false);
	        t.drawScale = 0.12F;
	        t.targetDrawScale = 0.75F;
	        t.applyPowers();
	        AbstractDungeon.player.discardPile.removeCard(t);
    	}
    	
    	temp = new ArrayList<AbstractCard>();
    	for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			temp.add(c);
    		}
    	}
    	for(AbstractCard t : temp) {
	        t.unfadeOut();
	        AbstractDungeon.player.hand.addToHand(t);
	        t.fadingOut = false;
	        t.stopGlowing();
	        t.unhover();
	        t.setAngle(0.0F, true);
	        t.lighten(false);
	        t.drawScale = 0.12F;
	        t.targetDrawScale = 0.75F;
	        t.applyPowers();
	        AbstractDungeon.player.exhaustPile.removeCard(t);
    	}
      
      AbstractDungeon.player.hand.refreshHandLayout();
      AbstractDungeon.player.hand.glowCheck();
    } 
    
    tickDuration();
    this.isDone = true;
  }
}