package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class OneIsEnoughAction2 extends AbstractGameAction {

	private int effect;
	private int count;
	private boolean freeToPlayOnce;
	private int energyOnUse;
	private AbstractPlayer p;
	private int block;
	  
	public OneIsEnoughAction2(int energy, int block, AbstractPlayer p, int count, boolean freeToPlayOnce)
	{
		this.energyOnUse = energy;
		this.effect=energy;
		this.count=count;
		this.block=block;
		this.p=p;
		this.freeToPlayOnce=freeToPlayOnce;
	}
	@Override
	public void update() {
		
		if(AbstractDungeon.player.hasRelic("Chemical X"))
		{
			effect += 2;
			AbstractDungeon.player.getRelic("Chemical X").flash();
		}
		
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, count*block));

		
		if(!this.freeToPlayOnce) {
			AbstractDungeon.player.energy.use(energyOnUse);
		}
		
		this.isDone = true;
	}

}
