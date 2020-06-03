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
	public static final int damage = 0;
	public static int damageBonus;
	
	
	public Zombie()
	{

		super(ID, new Color(1.0F, 1.0F, 1.0F, 100F), 0, damage, false, 
				"ShadeImages/orbs/zombie.png",ShadeCharacter.INDEX_ZOMBIE);
		this.extraFontColor = Color.ROYAL;
		this.health = 5;
		this.count = 1;
		this.damageBonus = 0;
		this.healthBonus = 0;
		this.decayConstant = 5;
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
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
	}

	
    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,this.passiveAmount*this.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));

        AbstractDungeon.actionManager.addToBottom(new UndeadDecay(ShadeCharacter.INDEX_ZOMBIE));
    }

}
