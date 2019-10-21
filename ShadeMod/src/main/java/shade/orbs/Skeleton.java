package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadDecay;
import shade.characters.ShadeCharacter;


public class Skeleton extends SpawnedUndead{

	public static final String ID = "Shade:Skeleton";
	public static final int damage = 5;
	public static int damageBonus;

	public Skeleton() {

		super(ID, -10, new Color(1.0F, 217F / 255F, 70F / 255F, 100F),
				"images/monsters/theBottom/slimeAltM/skeleton.atlas",
				"images/monsters/theBottom/slimeAltM/skeleton.json", "idle", 1.5F, new Color(1F, 150F / 255F, 0F, 2F),
				damage, 0, false, new Color(.63F, .58F, .41F, 1), new Texture("ShadeImages/orbs/bronzeslime.png"),
				"ShadeImages/orbs/bronzeslime.png",ShadeCharacter.INDEX_SKELETON);
		this.extraFontColor = Color.ROYAL;
		this.health = 1;
		this.count = 1;
		this.damageBonus = 0;
		this.healthBonus = 0;
		this.decayConstant = 3;
		updateDescription();
		spawnVFX();
		setSlot(ShadeCharacter.INDEX_SKELETON,3);

	}

    public void updateDescription() {
    	this.applyFocus();
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.health + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,this.passiveAmount*this.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));

        AbstractDungeon.actionManager.addToBottom(new UndeadDecay(ShadeCharacter.INDEX_SKELETON));
    }

    public AbstractOrb makeCopy() {
        return new Skeleton();
    }

}
