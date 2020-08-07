package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.ui.TextureLoader;

public class OpenGravesPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:OpenGravesPower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/open_graves";
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));

    public static String[] DESCRIPTIONS;
    
    private boolean zombieTurn;
    
    
    public OpenGravesPower(AbstractCreature owner, int amt)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = amt;
        this.type = POWER_TYPE;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	
    	this.zombieTurn=true;
    	updateDescription();
    }
    
    public void atStartOfTurn() {
    	flash();
    	if(this.zombieTurn) {
    		AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie(),this.amount));
    	}else {
    		AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton(),this.amount));
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
