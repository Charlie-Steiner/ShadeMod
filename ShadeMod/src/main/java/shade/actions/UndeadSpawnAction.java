package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class UndeadSpawnAction extends AbstractGameAction{

    private AbstractOrb orbType;
    
	public UndeadSpawnAction(AbstractOrb newOrbType)
    {
        if (newOrbType!=null) {
            this.orbType=newOrbType;
            SpawnedUndead s = (SpawnedUndead) newOrbType;
            
        }
    }

	@Override
	public void update() {

        ShadeMod.logger.info("Channelling undead orb");

        if(AbstractDungeon.player instanceof ShadeCharacter)
        	((ShadeCharacter)AbstractDungeon.player).channelUndead(this.orbType);
        
        ShadeMod.logger.info("Done Channelling");
	}
}
