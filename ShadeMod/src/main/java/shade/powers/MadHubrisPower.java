package shade.powers;

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


public class MadHubrisPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:MadHubrisPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/mad_hubris.png";

    public static String[] DESCRIPTIONS;
    
    public MadHubrisPower(AbstractCreature owner, int newAmount)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = newAmount;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	
    }
    

    public void atStartOfTurnPostDraw() {
    	flash();
    	for(int i=0;i<this.amount;i++) {
    		if(AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead) {
    			Skeleton s = (Skeleton)AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_SKELETON);
    			int damage = s.passiveAmount+s.passiveBonus;
    			for(int j=0;j<s.count;j++) {
			    	AbstractDungeon.actionManager.addToTop(new DamageAction(owner,
			                new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    			}
    		}
    	}
    	
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
	}
    
	public void updateDescription() {
		if(this.amount==1) {
			this.description = DESCRIPTIONS[0];
		}else {
			this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
		}
	}
	
}
