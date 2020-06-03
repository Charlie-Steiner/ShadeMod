package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import shade.orbs.SpawnedUndead;



public class UndeadDecay extends AbstractGameAction {

	
	int orbIndex;
	
	public UndeadDecay(int index)
	{
		this.duration = Settings.ACTION_DUR_FAST;
		this.orbIndex = index;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		SpawnedUndead u = (SpawnedUndead)AbstractDungeon.player.orbs.get(orbIndex);
		
		
		int minimum = 0;	//should read some focus value from player related to min undead of type index
		int decayTime = AbstractDungeon.player.getPower("Shade:MinionsPower").amount;
		
		u.remove((int)((u.count-minimum)/(decayTime*1.0))+1);

		
		u.triggerEvokeAnimation();
		this.isDone = true;
	}

}
