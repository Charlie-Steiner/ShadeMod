package shade.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
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
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shade.cards.ClawBack;
import shade.cards.Defend_Shade;
import shade.ShadeMod;
import shade.actions.FindPremonitions;
import shade.cards.AnimateDead;
import shade.cards.CarefulCut;
import shade.cards.Strike_Shade;
import shade.orbs.SpawnedUndead;
import shade.orbs.UndeadGroup;
import shade.patches.*;
import shade.relics.YorickSkull;

import java.util.ArrayList;
import java.util.List;


public class ShadeCharacter extends CustomPlayer {

    public static final String[] orbTextures = {"ShadeImages/char/orb/layer1.png", "ShadeImages/char/orb/layer2.png", "ShadeImages/char/orb/layer3.png", "ShadeImages/char/orb/layer4.png", "ShadeImages/char/orb/layer5.png", "ShadeImages/char/orb/layer6.png", "ShadeImages/char/orb/layer1d.png", "ShadeImages/char/orb/layer2d.png", "ShadeImages/char/orb/layer3d.png", "ShadeImages/char/orb/layer4d.png", "ShadeImages/char/orb/layer5d.png"};
    
    public static final String notAShade = "I'd need to be a Shade.";

    public static final int INDEX_WRAITH = 0;
    public static final int INDEX_ZOMBIE = 1;
    public static final int INDEX_SKELETON = 2;
    public static final int INDEX_MAX = 2;

    public static Color cardRenderColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);

    public float xStartOffset = (float) Settings.WIDTH * 0.03F;
    private static float xSpaceBetweenSlots = 110 * Settings.scale;
    
    private static float yStartOffset = AbstractDungeon.floorY * 0.29F;
    
    public float[] orbPositionsX = {0,0,0};

    public float[] orbPositionsY = {0,0,0};

    public float renderscale = 1.0F;

    public float leftScale = 0.15F;
    
    public UndeadGroup undeadGroup;
    
    public ShadeCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "ShadeImages/char/orb/vfx.png", (String) null, (String) null);

        initializeClass("ShadeImages/char/idle.png", "ShadeImages/char/shoulder2.png", "ShadeImages/char/shoulder.png", "ShadeImages/char/corpse.png", getLoadout(), 10.0F, 0.0F, 220.0F, 290.0F, new EnergyManager(3));

        initializeSlotPositions();
        
        undeadGroup = new UndeadGroup();
    }
    
    public void initializeSlotPositions() {
    	//we only care about slots 0, 1 and 2 for wraiths, zombies, and skeletons
        orbPositionsX[0] = xStartOffset + (xSpaceBetweenSlots * 1F);
        orbPositionsX[1] = xStartOffset + (xSpaceBetweenSlots * 1.5F);
        orbPositionsX[2] = xStartOffset + (xSpaceBetweenSlots * 0.6F);

        orbPositionsY[0] = yStartOffset + 55 * Settings.scale;
        orbPositionsY[1] = yStartOffset - 60 * Settings.scale;
        orbPositionsY[2] = yStartOffset - 60 * Settings.scale;
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
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[0];
    }
	
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SLIME_SPLIT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }
    
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(Strike_Shade.ID);
        retVal.add(Strike_Shade.ID);
        retVal.add(Strike_Shade.ID);
        retVal.add(Strike_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(Defend_Shade.ID);
        retVal.add(AnimateDead.ID);
        retVal.add(CarefulCut.ID);
        return retVal;
    }
    
    public Color getSlashAttackColor() {
        return Color.BLACK.cpy();
    }
    
    public AbstractPlayer newInstance() {
        return new ShadeCharacter("The Shade", ShadeEnum.SHADE);
    }

    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }
    
    
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Shade", "This sorceror wields the power of the grave. NL A bit of a shady character.", 65, 65, 0, 99, 5, this,

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
        return new AnimateDead();
    }

	public void channelUndead(AbstractOrb orbType) {
		int index = -1;
		
		if(orbType instanceof shade.orbs.Skeleton)
		{
			ShadeMod.logger.info("chaneling skeleton");
			index = INDEX_SKELETON;
		}
		else if (orbType instanceof shade.orbs.Zombie)
		{
			ShadeMod.logger.info("chaneling zombie");
			index = INDEX_ZOMBIE;
		}
		else if (orbType instanceof shade.orbs.Wraith)
		{
			ShadeMod.logger.info("chaneling wraith");
			index = INDEX_WRAITH;
			AbstractDungeon.actionManager.addToBottom(new FindPremonitions());
		}
		
		
		if(index == -1)
			return;
		
		AbstractOrb currentUndead = undeadGroup.undeads.get(index);
		
		if(currentUndead instanceof EmptyOrbSlot)
		{
			ShadeMod.logger.info("only 1 minion");
			undeadGroup.undeads.set(index, orbType);
		}
		else
		{
			ShadeMod.logger.info("more skeletons");
			SpawnedUndead undead =  (SpawnedUndead)currentUndead;
			undead.count++;
			undead.updateDescription();
		}
		
		
		
		AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbType);
		AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbType);
	}
	
	//Put in custom ending images
	@Override
	public List<CutscenePanel> getCutscenePanels(){
		List<CutscenePanel> panels = new ArrayList();
		panels.add(new CutscenePanel("ShadeImages/scenes/shade1.png", "MONSTER_SNECKO_GLARE"));
        panels.add(new CutscenePanel("ShadeImages/scenes/shade2.png", "EVENT_SPIRITS"));
        panels.add(new CutscenePanel("ShadeImages/scenes/shade3.png", "EVENT_GHOSTS"));
		return panels;
	}

	@Override
	public String getPortraitImageName() {
		//Seems to be required on Jason's computer but not Charlie's really should be handled by BaseMod.
		return null;
	}
}

