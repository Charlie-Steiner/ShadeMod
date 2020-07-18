package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AsphyxiateAction extends AbstractGameAction {

	
	private DamageInfo info;
	private DamageType type;
	private AbstractCreature t;

	public AsphyxiateAction(AbstractCreature target, DamageType type) {
		this.t=target;
		this.type= type;
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = 0.1F;
	}
	
	@Override
	public void update() {
		int pStacks=0;
		
		if(t.hasPower("Poison")) {
			pStacks=t.getPower("Poison").amount;
		}
		this.info = new DamageInfo(t,pStacks,this.type);
		setValues(t,info);
		

		
		if(t != null) {
			t.damage(this.info);
			
			
	    	if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
	          AbstractDungeon.actionManager.clearPostCombatActions();
	    	}
		}
		this.isDone = true;
	}

}
