package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.UndeadGroup;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class TriggerEndOfTurnUndead extends AbstractGameAction
{
	
	public TriggerEndOfTurnUndead() {}
  
	public void update() {
		UndeadGroup ug = ((ShadeCharacter)AbstractDungeon.player).undeadGroup;
		
		ug.undeads.forEach(AbstractOrb::onEndOfTurn);
		
		this.isDone = true;
	}
}