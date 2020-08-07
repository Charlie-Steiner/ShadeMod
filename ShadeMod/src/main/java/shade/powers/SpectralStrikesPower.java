package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import shade.actions.PlayRandomFromExhaustAction;
import shade.ui.TextureLoader;
import shade.ShadeMod;


public class SpectralStrikesPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:SpectralStrikesPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/SpectralStrikes";
    
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));

    public static String[] DESCRIPTIONS;
    
    public SpectralStrikesPower(AbstractCreature owner)
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
    

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractCard.CardTags.STRIKE) && this.amount > 0) {
          flash();
          card.purgeOnUse=true;
          AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,this.amount));
          AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
        		  new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
        } 
      }
    
	public void updateDescription() {
		if(this.amount==1) {
			this.description = DESCRIPTIONS[0];
		} else {
			this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
		}
	}
	
}
