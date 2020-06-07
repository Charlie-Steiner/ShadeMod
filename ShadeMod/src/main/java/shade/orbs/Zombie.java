package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadDecay;
import shade.characters.ShadeCharacter;

public class Zombie extends SpawnedUndead {


	public static final String ID = "Shade:Zombie";
	public static final int baseHealth = 5;
	
	
	public Zombie()
	{

		super(ID, new Color(1.0F, 1.0F, 1.0F, 100F), 0, baseHealth, false, 
				"ShadeImages/orbs/zombie.png",ShadeCharacter.INDEX_ZOMBIE);
		this.extraFontColor = Color.ROYAL;
		this.count = 1;
		this.passiveBonus = 0;
		updateDescription();
		spawnVFX();
		setSlot(ShadeCharacter.INDEX_ZOMBIE,3);
	}
	
	
	@Override
	public AbstractOrb makeCopy() {
		return new Zombie();
	}

	@Override
	public void updateDescription() {

		this.applyFocus();
		int damagePer = this.passiveAmount+this.passiveBonus;
		int decayAmt = UndeadDecay.getDecay(this.count);
		
		String firstPlural;
		String secondPlural;
		String thirdPlural;
		if(count==1) {
			firstPlural = this.descriptions[4];
			secondPlural = this.descriptions[5];
		}else {
			firstPlural = this.descriptions[1];
			secondPlural = this.descriptions[2];
		}
		if(decayAmt==1) {
			thirdPlural = this.descriptions[6];
		}else {
			thirdPlural = this.descriptions[3];
		}
			
		
        this.description = this.descriptions[0] + this.count + firstPlural + damagePer + secondPlural + decayAmt + thirdPlural;
	}
	
    public void onStartOfTurn() {
    	AbstractDungeon.actionManager.addToTop(new UndeadDecay(ShadeCharacter.INDEX_ZOMBIE));
    }
	
    public void activateEffectUnique() {
    	if(this.debuffAmount>0) {
    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,this.passiveAmount*this.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));
    	}
    }

}
