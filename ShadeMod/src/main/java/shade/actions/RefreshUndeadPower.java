package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import shade.orbs.SpawnedUndead;



public class RefreshUndeadPower extends AbstractGameAction {

	
	
	public RefreshUndeadPower()
	{
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	@Override
	public void update() {
		for(int i=0; i<AbstractDungeon.player.maxOrbs;i++) {
			AbstractDungeon.player.orbs.get(i).applyFocus();
			AbstractDungeon.player.orbs.get(i).updateDescription();
		}
		this.isDone = true;
	}

}
