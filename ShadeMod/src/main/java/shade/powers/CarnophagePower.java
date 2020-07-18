package shade.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;

public class CarnophagePower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:CarnophagePower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/carnophage.png";

    public static String[] DESCRIPTIONS;
    
    
    public CarnophagePower(AbstractCreature owner, int amt)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = amt;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	
    }
    
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
          flash();
          AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        } 
      }
    
	public void updateDescription() {
	      this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
