package shade.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;

public class LichFormPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:LichFormPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/lich.png";

    public static String[] DESCRIPTIONS;
    
    
    public LichFormPower(AbstractCreature owner, int amt)
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
    
    public void atStartOfTurn() {
    	flash();
    	for(int i=0;i<this.amount;i++) {
			//AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton()));
    	}
    }
    
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
