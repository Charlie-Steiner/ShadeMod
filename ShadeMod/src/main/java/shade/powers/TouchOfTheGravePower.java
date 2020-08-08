package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shade.actions.PlayRandomFromExhaustAction;
import shade.ui.TextureLoader;
import shade.ShadeMod;


public class TouchOfTheGravePower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:TouchOfTheGravePower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/vengefulDead";
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));


    public static String[] DESCRIPTIONS;
    
    public TouchOfTheGravePower(AbstractCreature owner)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = 1;
        this.type = POWER_TYPE;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    }
    

    public void atStartOfTurnPostDraw() {
    	flash();
    	for(int i=0;i<this.amount;i++) {
    		AbstractDungeon.actionManager.addToBottom(new WaitAction(Settings.ACTION_DUR_FAST));
    		AbstractDungeon.actionManager.addToBottom(new PlayRandomFromExhaustAction(AbstractCard.CardType.ATTACK, false));
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
