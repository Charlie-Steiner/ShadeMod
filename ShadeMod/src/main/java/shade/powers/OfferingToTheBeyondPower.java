package shade.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;

public class OfferingToTheBeyondPower extends TwoAmountPower {
	
    public static final String POWER_ID = "Shade:OfferingToTheBeyondPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/offering_to_the_beyond.png";

    public static String[] DESCRIPTIONS;
    private final static int CARDS = 3;
    
    
    public OfferingToTheBeyondPower(AbstractCreature owner, int amt)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = amt;
    	this.amount2 = CARDS;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	
    }
    
    public void onExhaust(AbstractCard card) {
      if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        flash();
        this.amount2 -= 1;
        
        if(this.amount2==0) {
        	for(int i=0;i<this.amount;i++) {
        		AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Wraith()));
        	}
        	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else {
        	updateDescription();
        }
      } 
    }
    
    public void atStartOfTurn() {
    	this.amount2=CARDS;
    	updateDescription();
    }
    
	public void updateDescription() {
	    if (this.amount2 == 1) {
	        this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1];
	      } else {
	        this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2];
	     }
	    if(this.amount == 1) {
	    	this.description = this.description + DESCRIPTIONS[3];
	    }else {
	    	this.description = this.description + DESCRIPTIONS[4] + this.amount + DESCRIPTIONS[5];
	    }
	}
}
