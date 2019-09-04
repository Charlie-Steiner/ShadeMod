package shade.powers;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shade.actions.PlayRandomFromExhaustAction;

import shade.ShadeMod;


public class TouchOfTheGravePowerUpgraded extends AbstractPower {
	
    public static final String POWER_ID = "Shade:TouchOfTheGravePowerUpgraded";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";

    public static String[] DESCRIPTIONS;
    
    public TouchOfTheGravePowerUpgraded(AbstractCreature owner)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = 1;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    }
    

    public void atStartOfTurn() {
    	flash();
    	for(int i=0;i<this.amount;i++) {
    		AbstractDungeon.actionManager.addToBottom(new WaitAction(Settings.ACTION_DUR_FAST));
    		AbstractDungeon.actionManager.addToBottom(new PlayRandomFromExhaustAction(AbstractCard.CardType.ATTACK, true));
    	}
    }
    
	public void updateDescription() {
		if(this.amount==1) {
			this.description = DESCRIPTIONS[0];
		} else {
			this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
		}
	}
	
}
