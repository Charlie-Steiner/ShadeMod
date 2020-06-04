package shade.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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


public class MinionsPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:MinionsPower";
    public static final String NAME = "Minions";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";
    public int decayConstant = 3;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    
    public MinionsPower(AbstractCreature owner)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = decayConstant;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    }
    


	public void updateDescription() {

		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

	}
	
	public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
		if(damageAmount > 0)
		{
			damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_ZOMBIE);
			damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_SKELETON);
		}

		if(damageAmount>1 && (AbstractPower)AbstractDungeon.player.getPower("IntangiblePlayer")==null)
		{
			if(!(AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_WRAITH) instanceof EmptyOrbSlot)) {
				damageAmount = 1;
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1), 1)); 
			}
		}
		
		return damageAmount;
	}
	
	private int minionBlock(DamageInfo info, int damageAmount, int index)
	{
		//only zombies block
		if(index == ShadeCharacter.INDEX_ZOMBIE && !(AbstractDungeon.player.orbs.get(index) instanceof EmptyOrbSlot))
		{
			SpawnedUndead u = (SpawnedUndead)AbstractDungeon.player.orbs.get(index);
			
			if(damageAmount >= (u.passiveAmount+u.passiveBonus)*u.count)
			{	//minions destroyed
				damageAmount -= (u.passiveAmount+u.passiveBonus)*u.count;
				u.remove(u.count);
			}
			else
			{
				int minionsLost = Math.floorDiv(damageAmount,(u.passiveAmount+u.passiveBonus))+1;
				damageAmount = 0;
				u.remove(minionsLost);
			}
		}
		
		return damageAmount;
	}

}
