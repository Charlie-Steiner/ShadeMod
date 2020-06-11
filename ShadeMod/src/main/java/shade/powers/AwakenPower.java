package shade.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import shade.ShadeMod;
import shade.actions.ReturnExhaustedToHandAction;
import shade.characters.ShadeCharacter;


public class AwakenPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:AwakenPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";

    public static String[] DESCRIPTIONS;
    
    public AwakenPower(AbstractCreature owner, int newAmount)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = newAmount;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	

        this.isTurnBased = true;
    }
    

    public void atStartOfTurnPostDraw() {
    	AbstractDungeon.actionManager.addToBottom(new ReturnExhaustedToHandAction(false));
    	
    	  if (this.amount == 0) {
        		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
          } else {
        	  	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            	flash();
          }
    }
    
	public void updateDescription() {
	    if (this.amount == 1) {
	        this.description = DESCRIPTIONS[0];
	      } else {
	        this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	      } 
	}
	
}
