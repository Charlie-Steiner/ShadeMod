package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.actions.UndeadAutoAttack;
import shade.characters.ShadeCharacter;

public class Zombie extends SpawnedUndead {


	public static final String ID = "Shade:Zombie";
	public static final int damage = 0;
	public static int damageBonus;
	
	
	public Zombie()
	{

		super(ID, -10, new Color(1.0F, 217F / 255F, 70F / 255F, 100F),
				"images/monsters/theBottom/slimeAltM/skeleton.atlas",
				"images/monsters/theBottom/slimeAltM/skeleton.json", "idle", 1.5F, new Color(1F, 150F / 255F, 0F, 2F),
				damage, 0, false, new Color(.63F, .58F, .41F, 1), new Texture("ShadeImages/orbs/cultist.png"),
				"ShadeImages/orbs/cultist.png");
		this.extraFontColor = Color.ROYAL;
		this.health = 5;
		this.count = 1;
		this.damageBonus = 0;
		this.healthBonus = 0;
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
		
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.health + this.descriptions[2];
	}

	
    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,(this.passiveAmount+this.damageBonus)*this.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));

    }

}
