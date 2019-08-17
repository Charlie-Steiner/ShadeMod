package shade.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shade.cards.ClawBack;
import shade.cards.Defend_Shade;
import shade.ShadeMod;
import shade.cards.AnimateDead;
import shade.cards.Boneskin;
import shade.cards.Strike_Shade;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;
import shade.patches.ShadeEnum;
import shade.relics.YorickSkull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ShadeCharacter extends CustomPlayer {

    public static final String[] orbTextures = {"ShadeImages/char/orb/layer1.png", "ShadeImages/char/orb/layer2.png", "ShadeImages/char/orb/layer3.png", "ShadeImages/char/orb/layer4.png", "ShadeImages/char/orb/layer5.png", "ShadeImages/char/orb/layer6.png", "ShadeImages/char/orb/layer1d.png", "ShadeImages/char/orb/layer2d.png", "ShadeImages/char/orb/layer3d.png", "ShadeImages/char/orb/layer4d.png", "ShadeImages/char/orb/layer5d.png"};



    public static final int INDEX_WRAITH = 0;
    public static final int INDEX_ZOMBIE = 1;
    public static final int INDEX_SKELETON = 2;

    public static Color cardRenderColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);

    public float xStartOffset = (float) Settings.WIDTH * 0.23F;
    private static float xSpaceBetweenSlots = 90 * Settings.scale;
    private static float xSpaceBottomAlternatingOffset = 0;
    
    private static float yStartOffset = AbstractDungeon.floorY + (100 * Settings.scale);

    private static float ySpaceAlternatingOffset = -60 * Settings.scale;
    
    public float[] orbPositionsX = {0,0,0,0,0,0,0,0,0,0};

    public float[] orbPositionsY = {0,0,0,0,0,0,0,0,0,0};
    
    
    private String atlasURL = "ShadeImages/char/skeleton.atlas";
    private String jsonURL = "ShadeImages/char/skeleton.json";
    private String jsonURLPuddle = "ShadeImages/char/skeletonPuddle.json";

    private String currentJson = jsonURL;

    public float renderscale = 1.0F;

    public boolean moved = false;
    public float leftScale = 0.15F;
    
    
    public ShadeCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "ShadeImages/char/orb/vfx.png", (String) null, (String) null);


        this.initializeClass((String) null, "ShadeImages/char/shoulder2.png", "ShadeImages/char/shoulder.png", "ShadeImages/char/corpse.png", this.getLoadout(), 0.0F, 0.0F, 300.0F, 180.0F, new EnergyManager(3));
        this.reloadAnimation();


        this.dialogX = -200 * Settings.scale;
        this.dialogY = -200 * Settings.scale;
        initializeSlotPositions();

    }
    
    public void reloadAnimation() {
        this.loadAnimation(atlasURL, this.currentJson, renderscale);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());

    }
    
    public void setRenderscale(float renderscale) {
        this.renderscale = renderscale;
        reloadAnimation();


    }
    
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!this.moved) this.movePosition((float)Settings.WIDTH * this.leftScale, AbstractDungeon.floorY); this.moved = true;


//        this.hatX = this.skeleton.findBone("eyeback1").getX();
//        this.hatY = this.skeleton.findBone("eyeback1").getY();

    }
	
    public void initializeSlotPositions() {
        orbPositionsX[0] = xStartOffset + (xSpaceBetweenSlots * 1);
        orbPositionsX[1] = xStartOffset + (xSpaceBetweenSlots * 1) + xSpaceBottomAlternatingOffset;
        orbPositionsX[2] = xStartOffset + (xSpaceBetweenSlots * 2);
        orbPositionsX[3] = xStartOffset + (xSpaceBetweenSlots * 2) + xSpaceBottomAlternatingOffset;
        orbPositionsX[4] = xStartOffset + (xSpaceBetweenSlots * 3);
        orbPositionsX[5] = xStartOffset + (xSpaceBetweenSlots * 3) + xSpaceBottomAlternatingOffset;
        orbPositionsX[6] = xStartOffset + (xSpaceBetweenSlots * 4);
        orbPositionsX[7] = xStartOffset + (xSpaceBetweenSlots * 4) + xSpaceBottomAlternatingOffset;
        orbPositionsX[8] = xStartOffset + (xSpaceBetweenSlots * 5);
        orbPositionsX[9] = xStartOffset + (xSpaceBetweenSlots * 5) + xSpaceBottomAlternatingOffset;

        orbPositionsY[0] = yStartOffset;
        orbPositionsY[1] = yStartOffset + -100 * Settings.scale;
        orbPositionsY[2] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[3] = yStartOffset + -100 * Settings.scale + ySpaceAlternatingOffset;
        orbPositionsY[4] = yStartOffset;
        orbPositionsY[5] = yStartOffset + -100 * Settings.scale;
        orbPositionsY[6] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[7] = yStartOffset + -100 * Settings.scale + ySpaceAlternatingOffset;
        orbPositionsY[8] = yStartOffset;
        orbPositionsY[9] = yStartOffset + -100 * Settings.scale;
    }

    public String getTitle(PlayerClass playerClass) {
        return "The Shade";
    }
    
    public String getLocalizedCharacterName() {
        return "The Shade";
    }

    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.SHADE;
    }

    public Color getCardRenderColor() {

        return cardRenderColor;
    }

    public String getVampireText() {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[5];
    }
	
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SLIME_SPLIT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }
    
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(Strike_Shade.ID);
        retVal.add(Strike_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(AnimateDead.ID);
        retVal.add(ClawBack.ID);
        return retVal;
    }
    
    public Color getSlashAttackColor() {
        return Color.BLACK;
    }
    
    public AbstractPlayer newInstance() {
        return new ShadeCharacter("The Shade", ShadeEnum.SHADE);
    }

    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }
    
    
    public CharSelectInfo getLoadout() {
    	int startingOrbs = 3;
    	
        return new CharSelectInfo("The Shade", "A shady shade.", 60, 60, startingOrbs, 99, 5, this,

                getStartingRelics(), getStartingDeck(), false);
    }
    
    
    public String getSpireHeartText() {
        return "The living will serve the dead.";
    }
    
    
    public int getAscensionMaxHPLoss() {
        return 10;
    }
    
    
    public String getCustomModeCharacterButtonSoundKey() {
        return "SLIME_SPLIT";
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(YorickSkull.ID);
        UnlockTracker.markRelicAsSeen(YorickSkull.ID);
        return retVal;
    }
    
    public Color getCardTrailColor() {
        return cardRenderColor.cpy();
    }
    
    
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    public AbstractCard getStartCardForEvent() {
        return new Strike_Shade();
    }

	public void channelUndead(AbstractOrb orbType) {

		
		int index = -1;
		
		if(orbType instanceof shade.orbs.Skeleton)
		{
			ShadeMod.logger.info("chanelling skeleton");
			index = INDEX_SKELETON;
		}
		else if (orbType instanceof shade.orbs.Zombie)
		{

			ShadeMod.logger.info("chanelling zombie");
			index = INDEX_ZOMBIE;
		}
		
		if(index == -1)
			return;
		
		AbstractOrb currentUndead = orbs.get(index);
		
		if(currentUndead instanceof EmptyOrbSlot)
		{
			ShadeMod.logger.info("only 1 minion");
			orbs.set(index, orbType);
		}
		else
		{
			ShadeMod.logger.info("more skeletons");
			SpawnedUndead undead =  (SpawnedUndead)currentUndead;
			undead.count = undead.count + 1;
		}
		
		
		
		AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbType);
		AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbType);
	}
}

