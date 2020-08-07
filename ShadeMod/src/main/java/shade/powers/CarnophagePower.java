package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import shade.ui.TextureLoader;

public class CarnophagePower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:CarnophagePower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/carnophage";
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));


    public static String[] DESCRIPTIONS;
    
    
    public CarnophagePower(AbstractCreature owner, int amt)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = amt;
        this.type = POWER_TYPE;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	
    }
    
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
          flash();
          AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie(),this.amount));
        } 
      }
    
	public void updateDescription() {
		if(this.amount==1) {
	      this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}else {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
		}
		
	}
}
