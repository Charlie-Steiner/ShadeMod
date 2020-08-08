package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;



public class RefreshUndeadPower extends AbstractGameAction {

	
	
	public RefreshUndeadPower()
	{
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	@Override
	public void update() {
		if(AbstractDungeon.player instanceof ShadeCharacter) {
			for(int i=0; i<((ShadeCharacter)AbstractDungeon.player).undeadGroup.maxUndead;i++) {
				((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(i).applyFocus();
				((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads.get(i).updateDescription();
			}
		}
		this.isDone = true;
	}

}
