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
    	for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			
    	        AbstractDungeon.player.hand.addToHand(c);
    	        c.unhover();
    	        c.setAngle(0.0F, true);
    	        c.lighten(false);
    	        c.drawScale = 0.12F;
    	        c.targetDrawScale = 0.75F;
    	        c.applyPowers();
    	        AbstractDungeon.player.drawPile.removeCard(c);
    		}
    	}
    	for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			
    	        AbstractDungeon.player.hand.addToHand(c);
    	        c.unhover();
    	        c.setAngle(0.0F, true);
    	        c.lighten(false);
    	        c.drawScale = 0.12F;
    	        c.targetDrawScale = 0.75F;
    	        c.applyPowers();
    	        AbstractDungeon.player.discardPile.removeCard(c);
    		}
    	}
    	for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
    		if(c.cardID=="Shade:Premonition" && nAllowed>0) {
    			nAllowed--;
    			
    	        AbstractDungeon.player.hand.addToHand(c);
    	        c.unhover();
    	        c.setAngle(0.0F, true);
    	        c.lighten(false);
    	        c.drawScale = 0.12F;
    	        c.targetDrawScale = 0.75F;
    	        c.applyPowers();
    	        AbstractDungeon.player.exhaustPile.removeCard(c);
    		}
    	}
      
      AbstractDungeon.player.hand.refreshHandLayout();
      AbstractDungeon.player.hand.glowCheck();
    } 
    
    tickDuration();
    this.isDone = true;
  }
}