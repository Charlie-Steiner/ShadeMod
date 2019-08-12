package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.actions.UndeadAutoAttack;


public class Skeleton extends SpawnedUndead{

	public static final String ID = "Shade:Skeleton";

	public Skeleton() {

		super(ID, -10, new Color(1.0F, 217F / 255F, 70F / 255F, 100F),
				"images/monsters/theBottom/slimeAltM/skeleton.atlas",
				"images/monsters/theBottom/slimeAltM/skeleton.json", "idle", 1.5F, new Color(1F, 150F / 255F, 0F, 2F),
				6, 0, false, new Color(.63F, .58F, .41F, 1), new Texture("ShadeImages/orbs/attackDefend.png"),
				"ShadeImages/orbs/bronzeslime.png");
		this.extraFontColor = Color.ROYAL;
		this.debuffAmount = 6;
		spawnVFX();

	}

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.debuffAmount + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,this.debuffAmount));

    }

    public AbstractOrb makeCopy() {
        return new Skeleton();
    }
}