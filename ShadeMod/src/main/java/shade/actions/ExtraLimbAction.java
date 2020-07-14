package shade.actions;

import java.util.ArrayList;
import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class ExtraLimbAction extends AbstractGameAction {
	
	private AbstractCard c;
  
  public ExtraLimbAction(AbstractCard card) {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_FAST;
    this.c=card;
  }

  
  public void update() {
    if (this.duration == Settings.ACTION_DUR_FAST) {

    	  Iterator var1 = GetAllInBattleInstances.get(this.c.uuid).iterator();
    	  
    	  while (var1.hasNext()) {
    	    AbstractCard ca = (AbstractCard)var1.next();
    	    ca.baseMagicNumber++;
    	    if (ca.baseMagicNumber < 0) {
    	      ca.baseMagicNumber = 0;
    	    }
    	  } 
    } 
    
    tickDuration();
    this.isDone = true;
  }
}