package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RavenousHordeAction extends AbstractGameAction {

	
	private int effect;
	private int energyOnUse;
	public RavenousHordeAction(int energy)
	{
		this.energyOnUse = energy;
		this.effect = this.energyOnUse;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(AbstractDungeon.player.hasRelic("Chemical X"))
		{
			this.effect += 2;
			AbstractDungeon.player.getRelic("Chemical X").flash();
		}
		
		for(int i = 0; i < effect; i++)
		{
			AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
		}
		

        AbstractDungeon.player.energy.use(this.energyOnUse);
		this.isDone = true;
	}

}
