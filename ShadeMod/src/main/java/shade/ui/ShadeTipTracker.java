package shade.ui;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;

import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShadeTipTracker {
	
	
  public enum TipKey {
    DecayTip,
    SkeletonTip,
    ZombieTip,
    WraithTip;
  }
  
  private static final Logger logger = LogManager.getLogger(ShadeTipTracker.class.getName());
  public static SpireConfig config;
  private static String ModFileName = "Shade_tipTracker";
  private static final Properties DEFAULTS = new Properties();
  static  {
    for (TipKey key : TipKey.values()) {
      DEFAULTS.setProperty(key.name(), "false");
    }
  }
  
  public static void initialize() {
    try {
      config = new SpireConfig("The Shade", ModFileName, new Properties());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }
  
  private static void save() {
    try {
      config.save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public static boolean shouldShow(TipKey key) { return !config.getBool(key.name()); }
  
  public static void neverShowAgain(TipKey key) {
    logger.info(key + " will never be shown again!");
    config.setBool(key.name(), true);
    save();
  }
  
  public static void showAgain(TipKey key) {
    logger.info(key + " is reactivated");
    config.setBool(key.name(), false);
    save();
  }
  
  public static void reset() {
    logger.info("resetting state of all tips");
    for (TipKey key : TipKey.values()) {
      config.setBool(key.name(), false);
    }
    save();
  }
  
  public static void checkForTip(TipKey key) {
	    if (ShadeTipTracker.shouldShow(key)) {
	    	
	    	TutorialStrings tempStrings=CardCrawlGame.languagePack
				    .getTutorialString("Shade:DecayTip");
	    	if(key==TipKey.SkeletonTip) {
	    		tempStrings=CardCrawlGame.languagePack
	    			    .getTutorialString("Shade:SkeletonTip");
	    	}else if(key==TipKey.ZombieTip) {
	    		tempStrings=CardCrawlGame.languagePack
	    			    .getTutorialString("Shade:ZombieTip");
	    	}else if(key==TipKey.WraithTip) {
	    		tempStrings=CardCrawlGame.languagePack
	    			    .getTutorialString("Shade:WraithTip");
	    	}
	    	AbstractDungeon.ftue = new FtueTip(tempStrings.LABEL[0], tempStrings.TEXT[0] + tempStrings.TEXT[1], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, FtueTip.TipType.NO_FTUE);
	      ShadeTipTracker.neverShowAgain(key);
	    } 
  }
}
