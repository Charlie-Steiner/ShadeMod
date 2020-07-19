package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FingerOfDeathAction extends AbstractGameAction {

	
	private DamageInfo info;

	public FingerOfDeathAction(AbstractCreature target, DamageInfo info) {
		this.info = info;
		setValues(target,info);
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = 0.1F;
	}
	
	@Override
	public void update() {
		if(this.target != null) {
			if(this.target != null)
			{
			    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, false));
				this.target.damage(this.info);
				
				if((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
					AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
					AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
				}
			}
	    	if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
	          AbstractDungeon.actionManager.clearPostCombatActions();
	    	}
		}
		this.isDone = true;
	}

}
