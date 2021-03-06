package shade.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;
import shade.ui.ShadeTipTracker;
import shade.ui.TextureLoader;


public class MinionsPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:MinionsPower";
    public static final String NAME = "Minions";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/minions";
    private static final Texture tex84 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ShadeMod.getResourcePath(IMG+"_32.png"));

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    
    public MinionsPower(AbstractCreature owner, int amt)
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
    

	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
		if(!(info.owner instanceof AbstractPlayer) && AbstractDungeon.player instanceof ShadeCharacter) {
			if(damageAmount > 0)
			{
				damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_ZOMBIE);
				damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_SKELETON);
			}
	
			if(damageAmount>1 && (AbstractPower)AbstractDungeon.player.getPower("Buffer")==null && (AbstractPower)AbstractDungeon.player.getPower("IntangiblePlayer")==null)
			{
				if(!(((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH) instanceof EmptyOrbSlot)) {
					damageAmount = 1;
					AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1), 1));
					((SpawnedUndead) ((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH)).remove(1);
				}
			}
		}
		return damageAmount;
	}
	
	private int minionBlock(DamageInfo info, int damageAmount, int index)
	{
		if(AbstractDungeon.player instanceof ShadeCharacter) {
			//only zombies block
			if(index == ShadeCharacter.INDEX_ZOMBIE && ((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(index) instanceof SpawnedUndead)
			{
				ShadeTipTracker.checkForTip(ShadeTipTracker.TipKey.ZombieTip);
				
				SpawnedUndead u = (SpawnedUndead)((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(index);
				
				if(damageAmount >= (u.passiveAmount+u.passiveBonus)*u.count)
				{	//minions destroyed
					damageAmount -= (u.passiveAmount+u.passiveBonus)*u.count;
					u.remove(u.count);
				}
				else
				{
					int minionsLost = (damageAmount-1)/(u.passiveAmount+u.passiveBonus)+1;
					AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,(u.passiveAmount+u.passiveBonus)*minionsLost-damageAmount));
					damageAmount = 0;
					u.remove(minionsLost);
				}
			}
		}
		return damageAmount;
	}

	
}
