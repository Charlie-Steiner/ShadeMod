package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RavenousHordeAction extends AbstractGameAction {

	
	private int effect;
	private int energyOnUse;
	private boolean upgraded;
	public RavenousHordeAction(int energy, boolean upgraded)
	{
		this.energyOnUse = energy;
		this.effect = this.energyOnUse+1;
		this.upgraded = upgraded;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		int threshold = this.upgraded?2:4;
		
		
		if(AbstractDungeon.player.hasRelic("Chemical X"))
		{
			this.effect += 2;
			AbstractDungeon.player.getRelic("Chemical X").flash();
		}
		
		for(int i = 0; i < effect; i++)
		{
			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
		}
		
		if(effect>=threshold) {
			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton()));
		}

        AbstractDungeon.player.energy.use(this.energyOnUse);
		this.isDone = true;
	}

}
