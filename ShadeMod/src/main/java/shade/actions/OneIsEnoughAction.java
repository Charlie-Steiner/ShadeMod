package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
	private int block;
	private int count;
	  
	public OneIsEnoughAction(int energy, AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int block, int count)
	{
		this.energyOnUse = energy;
		this.effect=energy;
		this.p=p;
		this.m=m;
		this.damageTypeForTurn=damageTypeForTurn;
		this.damage=damage;
		this.freeToPlayOnce=freeToPlayOnce;
		this.count=count;
		this.block=block;
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
			AbstractDungeon.actionManager.addToTop(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		}
		
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, count*block));
		
		if(!this.freeToPlayOnce) {
			AbstractDungeon.player.energy.use(this.energyOnUse);
		}
		
		this.isDone = true;
	}

}
