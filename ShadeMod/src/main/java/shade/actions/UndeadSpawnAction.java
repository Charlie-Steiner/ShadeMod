package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class UndeadSpawnAction extends AbstractGameAction{

    private AbstractOrb orbType;
    
	public UndeadSpawnAction(AbstractOrb newOrbType)
    {

        this.duration = Settings.ACTION_DUR_FAST;
		
        if (newOrbType!=null) {
            this.orbType=newOrbType;
        }
    }

	@Override
	public void update() {
		
        if(AbstractDungeon.player instanceof ShadeCharacter)
        	((ShadeCharacter)AbstractDungeon.player).channelUndead(this.orbType);
        
        
        this.isDone = true;
	}
}
