package shade.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;
import shade.orbs.Wraith;
import shade.orbs.Zombie;

public class UndeadSpawnAction extends AbstractGameAction{

    private AbstractOrb orbType;
    private int nUndead;
    
	public UndeadSpawnAction(AbstractOrb newOrbType)
    {
		this.nUndead=1;
        this.duration = Settings.ACTION_DUR_FAST;
		
        if (newOrbType!=null) {
            this.orbType=newOrbType;
        }
    }
	
	public UndeadSpawnAction(AbstractOrb newOrbType, int number)
    {
		this.nUndead = number;
        this.duration = Settings.ACTION_DUR_FAST;
		
        if (newOrbType!=null) {
            this.orbType=newOrbType;
        }
    }

	@Override
	public void update() {
		
        if(AbstractDungeon.player instanceof ShadeCharacter) {
        	for(int i=0;i<nUndead;i++) {
        		((ShadeCharacter)AbstractDungeon.player).channelUndead(this.orbType);
        	}
        	
        	//play sound
        	if(this.orbType instanceof Wraith) {
        		CardCrawlGame.sound.play("MONSTER_CHAMP_CHARGE", 0.05F);
        	}else if(this.orbType instanceof Skeleton){
        		CardCrawlGame.sound.play("MONSTER_BYRD_ATTACK_1", 0.05F);
        	}else if(this.orbType instanceof Zombie){
        		CardCrawlGame.sound.play("MONSTER_BYRD_ATTACK_0", 0.05F);
        	}else{
        		CardCrawlGame.sound.play("APPEAR", 0.05F);
        	}
        }else {
        	AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.player, ShadeCharacter.notAShade));
        }
        
        
        this.isDone = true;
	}
}
