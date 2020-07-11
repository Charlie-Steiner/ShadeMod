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
		
		u.remove(getDecay(u.count));

		
		u.triggerEvokeAnimation();
		this.isDone = true;
	}
	
	public static int getDecay(int n) {
		int reduction=0;
		if(AbstractDungeon.player.hasPower("Shade:GraveStillnessPower")) {
			reduction += AbstractDungeon.player.getPower("Shade:GraveStillnessPower").amount;
		}
		if(AbstractDungeon.player.hasPower("Shade:MinionsPower")) {
			int decayTime = AbstractDungeon.player.getPower("Shade:MinionsPower").amount;
			if((n-1)/decayTime+1 > reduction) {
				return (n-1)/decayTime+1 - reduction;
			}else {
				return 0;
			}
		} else {
			return 1;
		}
	}
}
