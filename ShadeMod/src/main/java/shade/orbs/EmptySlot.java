package shade.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import shade.ShadeMod;
import shade.characters.ShadeCharacter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class EmptySlot extends EmptyOrbSlot{
  private float vfxTimer;
  private float vfxIntervalMin;
  private float vfxIntervalMax;
  private static final float PI_DIV_16 = 0.19634955F;
  private static final float ORB_WAVY_DIST = 0.05F;
  private static final float PI_4 = 12.566371F;
  private static final float ORB_BORDER_SCALE = 1.2F;
  private static final OrbStrings orbStrings;
  public static final String NAME;
  public static final String[] DESCRIPTIONS;
  public static final String ORB_ID = "Shade:Empty";
  public static Texture img;
  
  static  {
    orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    NAME = orbStrings.NAME;
    DESCRIPTIONS = orbStrings.DESCRIPTION; }
  
  public EmptySlot() {
    img = new Texture(ShadeMod.getResourcePath("orbs/empty1.png"));
    this.baseEvokeAmount = 0;
    this.ID = "Shade:Empty";
    this.name = orbStrings.NAME;
    this.evokeAmount = this.baseEvokeAmount;
    this.basePassiveAmount = 0;
    this.passiveAmount = this.basePassiveAmount;
    this.angle = MathUtils.random(360.0F);
    this.channelAnimTimer = 0.5F;
    updateDescription();
  }
  
  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }

 @Override
   public void setSlot(int slotNum, int maxOrbs) {
       if (AbstractDungeon.player instanceof ShadeCharacter) {
           this.tX = ((ShadeCharacter) AbstractDungeon.player).orbPositionsX[slotNum]+AbstractDungeon.player.drawX;
           this.tY = ((ShadeCharacter) AbstractDungeon.player).orbPositionsY[slotNum]+AbstractDungeon.player.drawY;

       }

       this.hb.move(this.tX, this.tY);
   }
  
  public void onEvoke() {}


  
  public void onEndOfTurn() {}

  
  public void passiveEffect() {}

  
  public void triggerEvokeAnimation() {}

  
  public void updateAnimation() {
    super.updateAnimation();
    this.angle += Gdx.graphics.getDeltaTime() * 10.0F;
  }
  
  public void render(SpriteBatch sb) {
	    sb.setColor(this.c);
	    
	    sb.draw(img, this.cX - 48.0F - this.bobEffect.y / 8.0F, this.cY - 48.0F + this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
	    
	    sb.draw(img, this.cX - 48.0F + this.bobEffect.y / 8.0F, this.cY - 48.0F - this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);

	    renderText(sb);
	    this.hb.render(sb);
  }
  
  protected void renderText(SpriteBatch sb) {}

  public void playChannelSFX() {}

  public AbstractOrb makeCopy() { return new EmptySlot(); }
}
