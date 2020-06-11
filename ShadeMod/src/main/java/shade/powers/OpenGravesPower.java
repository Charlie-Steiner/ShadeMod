package shade.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;

public class OpenGravesPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:OpenGravesPower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";

    public static String[] DESCRIPTIONS;
    
    private boolean zombieTurn;
    
    
    public OpenGravesPower(AbstractCreature owner, int amt)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = amt;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	
    	this.zombieTurn=true;
    	updateDescription();
    }
    
    public void atStartOfTurn() {
    	flash();
    	if(this.zombieTurn) {
    		for(int i=0;i<this.amount;i++) {
    			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
    		}
    	}else {
    		for(int i=0;i<this.amount;i++) {
    			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton()));
    		}
    	}
    	this.zombieTurn = !this.zombieTurn;
    	
    	this.updateDescription();
    }
    
	public void updateDescription() {
		//1 if zombieTurn is false
		int skeleInt = zombieTurn ? 0 : 1;
		if(this.amount==1) {
			this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3+skeleInt] + DESCRIPTIONS[5];
		}else {
			this.description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3+skeleInt] + DESCRIPTIONS[6];
		}
	}
}
