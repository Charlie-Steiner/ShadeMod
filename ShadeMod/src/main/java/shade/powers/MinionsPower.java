package shade.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;


public class MinionsPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:MinionsPower";
    public static final String NAME = "Minions";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    
    public MinionsPower(AbstractCreature owner)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this .amount = 0;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    }
    


	public void updateDescription() {

		this.description = DESCRIPTIONS[0];

	}
	
	public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
		
		
		
		if(damageAmount > 0)
		{

			damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_ZOMBIE);
			damageAmount = minionBlock(info,damageAmount,ShadeCharacter.INDEX_SKELETON);
		}

	
		
		return damageAmount;
	}
	
	private int minionBlock(DamageInfo info, int damageAmount, int index)
	{
		if(!(AbstractDungeon.player.orbs.get(index) instanceof EmptyOrbSlot))
		{
			SpawnedUndead u = (SpawnedUndead)AbstractDungeon.player.orbs.get(index);
			
			if(damageAmount >= u.health*u.count)
			{	//skeletons destroyed
				damageAmount -= u.health*u.count;
				AbstractDungeon.player.orbs.set(index, new EmptyOrbSlot());
			}
			else
			{
				int minionsLost = Math.floorDiv(damageAmount,u.health);
				damageAmount = 0;
				u.count = u.count-minionsLost;
			}
		}
		
		return damageAmount;
	}

}
