package shade.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;


public class UndeadGroup
{
  public int maxUndead = 3;
  
  public ArrayList<AbstractOrb> undeads = new ArrayList();
  
  private ArrayList<AbstractOrb> emptySlots = new ArrayList();

  
  public UndeadGroup() {

  }

  
  public void render(SpriteBatch sb) {
    AbstractPlayer p = AbstractDungeon.player;
    if (p != null && ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) && !p.isDead && 
      !this.undeads.isEmpty()) {
      for (AbstractOrb u : this.undeads) {
        u.render(sb);
      }
    }
  }


  
  public void preBattlePrep() {
	  undeads.clear();
	  
	  undeads.add(new EmptySlot());
	  undeads.add(new EmptySlot());
	  undeads.add(new EmptySlot());
  }

  
  
  public void triggerStartOfTurnUndead() {
      for (AbstractOrb u : this.undeads) {
        u.onStartOfTurn();
      }
  }

}
