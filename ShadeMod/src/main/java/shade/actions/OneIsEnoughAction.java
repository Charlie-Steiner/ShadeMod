package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class OneIsEnoughAction extends AbstractGameAction {

	private int effect;
	private int damage;
	private boolean freeToPlayOnce;
	private int energyOnUse;
	private AbstractPlayer p;
	private AbstractMonster m;
	private DamageInfo.DamageType damageTypeForTurn;
	  
	public OneIsEnoughAction(int energy, boolean upgraded, AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce)
	{
		this.energyOnUse = energy;
		this.effect=upgraded?(energy+1):energy;
		this.p=p;
		this.m=m;
		this.damageTypeForTurn=damageTypeForTurn;
		this.damage=damage;
		this.freeToPlayOnce=freeToPlayOnce;
	}
	@Override
	public void update() {
		
		if(AbstractDungeon.player.hasRelic("Chemical X"))
		{
			this.effect += 2;
			AbstractDungeon.player.getRelic("Chemical X").flash();
		}
		
		for(int i = 0; i < effect; i++)
		{
			AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		}
		
		
		if(!this.freeToPlayOnce) {
			AbstractDungeon.player.energy.use(this.energyOnUse);
		}
		
		this.isDone = true;
	}

}
