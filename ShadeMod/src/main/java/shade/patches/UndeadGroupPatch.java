package shade.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import shade.actions.TriggerEndOfTurnUndead;
import shade.characters.ShadeCharacter;
import shade.orbs.UndeadGroup;
public class UndeadGroupPatch
{
  @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
  public static class combatUpdate
  {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractPlayer self) {
    	if(self instanceof ShadeCharacter) {
	      for (AbstractOrb u : ((ShadeCharacter)self).undeadGroup.undeads) {
	        u.update(); 
	      }
    	}
    }
    
    private static class Locator
      extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        ArrayList<Matcher> preMatchers = new ArrayList<Matcher>();
        
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");
        
        return LineFinder.findInOrder(ctMethodToPatch, preMatchers, fieldAccessMatcher);
      }
    }
  }
  
  @SpirePatch(clz = AbstractPlayer.class, method = "update")
  public static class update {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractPlayer self) {
    	if(self instanceof ShadeCharacter) {
  	      for (AbstractOrb u : ((ShadeCharacter)self).undeadGroup.undeads) {
	        u.updateAnimation(); 
  	      }
    	}
    }
    
    private static class Locator
      extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        ArrayList<Matcher> preMatchers = new ArrayList<Matcher>();
        
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");
        
        return LineFinder.findInOrder(ctMethodToPatch, preMatchers, fieldAccessMatcher);
      }
    }
  }
  
  @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
  public static class preBattlePrep
  {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractPlayer self) {
    	if(self instanceof ShadeCharacter) {
    	    ((ShadeCharacter)self).undeadGroup.preBattlePrep();
    	}
    }
    
    private static class Locator
      extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        ArrayList<Matcher> preMatchers = new ArrayList<Matcher>();
        
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");
        
        return LineFinder.findInOrder(ctMethodToPatch, preMatchers, fieldAccessMatcher);
      }
    }
  }
  
  @SpirePatch(clz = AbstractPlayer.class, method = "render")
  public static class render {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractPlayer self, SpriteBatch sb) {
      if(self instanceof ShadeCharacter) {
  	      for (AbstractOrb u : ((ShadeCharacter)self).undeadGroup.undeads) {
  	        u.render(sb); 
  	      }
      }
    }
    
    private static class Locator
      extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        ArrayList<Matcher> preMatchers = new ArrayList<Matcher>();
        
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");
        
        return LineFinder.findInOrder(ctMethodToPatch, preMatchers, fieldAccessMatcher);
      }
    }
  }
  
  @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")
  public static class callEndOfTurnActions
  {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(GameActionManager __instance) {
    	if(AbstractDungeon.player instanceof ShadeCharacter) {
    		__instance.addToBottom(new TriggerEndOfTurnUndead()); 
    	}
    }
    
    private static class Locator
      extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        ArrayList<Matcher> preMatchers = new ArrayList<Matcher>();
        
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(com.megacrit.cardcrawl.dungeons.AbstractDungeon.class, "player");
        
        return LineFinder.findInOrder(ctMethodToPatch, preMatchers, fieldAccessMatcher);
      }
    }
  }
  
  @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnOrbs")
  public static class applyStartOfTurnOrbs
  {
    public static void Postfix(AbstractPlayer self) {
    	if(self instanceof ShadeCharacter) {
    		((ShadeCharacter)self).undeadGroup.triggerStartOfTurnUndead();
    	}
    }
  }
}