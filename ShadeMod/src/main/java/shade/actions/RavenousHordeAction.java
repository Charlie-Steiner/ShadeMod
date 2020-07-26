package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RavenousHordeAction extends AbstractGameAction {

	
	private int effect;
	private int energyOnUse;
	private boolean upgraded;
	private boolean freeToPlayOnce;
	public RavenousHordeAction(int energy, boolean upgraded, boolean freeToPlayOnce)
	{
		this.energyOnUse = energy;
		this.effect = this.energyOnUse;
		this.upgraded = upgraded;
		this.freeToPlayOnce=freeToPlayOnce;
	}
	@Override
	public void update() {
		if(AbstractDungeon.player.hasRelic("Chemical X"))
		{
			this.effect += 2;
			AbstractDungeon.player.getRelic("Chemical X").flash();
		}
		
		this.effect *= 2;
		if(upgraded) {
			this.effect+=1;
		}
		
		for(int i = 0; i < effect; i++)
		{
			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
		}

		if(!this.freeToPlayOnce) {
			AbstractDungeon.player.energy.use(this.energyOnUse);
		}
		this.isDone = true;
	}

}
