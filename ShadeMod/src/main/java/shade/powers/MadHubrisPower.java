package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import shade.ShadeMod;
import shade.actions.RefreshUndeadPower;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;
import shade.ui.TextureLoader;


public class MadHubrisPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:MadHubrisPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/mad_hubris";
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));

    public static String[] DESCRIPTIONS;
    
    public MadHubrisPower(AbstractCreature owner, int newAmount)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = newAmount;
        this.type = POWER_TYPE;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	
    }
    

    public void atStartOfTurn() {
    	flash();
    	if(AbstractDungeon.player instanceof ShadeCharacter) {
	    	for(int i=0;i<this.amount;i++) {
	    		if(((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead) {
	    			Skeleton s = (Skeleton)((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON);
	    			int damage = s.passiveAmount+s.passiveBonus;
	    			for(int j=0;j<s.count;j++) {
				    	AbstractDungeon.actionManager.addToTop(new DamageAction(owner,
				                new DamageInfo(AbstractDungeon.getRandomMonster(), damage, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	    			}
	    		}
	    	}
    	}
    	
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
	}
    
	public void updateDescription() {
		if(this.amount==1) {
			this.description = DESCRIPTIONS[0];
		}else {
			this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
		}
	}
	
}
