package shade.orbs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.animations.AbstractAnimation;
import shade.ShadeMod;
import shade.characters.ShadeCharacter;

public abstract class SpawnedUndead extends AbstractOrb{

	
    public float NUM_X_OFFSET = 1.0F * Settings.scale;
    public float NUM_Y_OFFSET = -35.0F * Settings.scale;
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.2F;
    private float vfxIntervalMax = 0.7F;

    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;
    public AbstractCard lockedCard;
    protected boolean showChannelValue = true;
    public static final Logger logger = LogManager.getLogger(ShadeMod.class.getName());
    public boolean upgraded = false;
    public boolean showPassive = true;
    public boolean activatedThisTurn = false;
    public int UniqueFocus;
    public float animX;
    public float animY;
    public boolean movesToAttack;
    public String[] descriptions;
    private Color mainColor;
    public static String orbID = "";
    public boolean noEvokeBonus;
    private float scale = 1F;
    private static int W;
    public Texture img;
    private float x;
    private float px;
    private AbstractCreature.CreatureAnimation animation;
    private float animationTimer;
    private float animationTimerStart;

    public AbstractPlayer p;
    private float delayTime;

    public String customDescription;
    private int yOffset;
    public int debuffBonusAmount;
    public int debuffAmount;
    public int count;
    public int passiveBonus;
    public Color extraFontColor = null;
    public boolean topSpawnVFX = false;
    
    public int index;



	public SpawnedUndead(String ID, Color mainColor, int yOffset, int passive, boolean movesToAttack, String IMGURL,int index) {

        this.c = mainColor.cpy();
        //this.renderBehind=true;

        this.yOffset = yOffset;

        this.ID = ID;

        this.img = new Texture(IMGURL);

        this.basePassiveAmount = passive;
        this.movesToAttack = movesToAttack;

        this.evokeAmount = 0;

        this.passiveAmount = this.basePassiveAmount;


        this.index = index;
        
        this.channelAnimTimer = 0.5F;

        this.descriptions = CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;

        this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;

        //AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeFlareEffect(this, OrbVFXColor), .1F));
        this.applyFocus();

        updateDescription();


    }

public void spawnVFX(){
	
}
 @Override
    public void setSlot(int slotNum, int maxOrbs) {
        if (AbstractDungeon.player instanceof ShadeCharacter) {
            this.tX = ((ShadeCharacter) AbstractDungeon.player).orbPositionsX[slotNum];
            this.tY = ((ShadeCharacter) AbstractDungeon.player).orbPositionsY[slotNum];

        }



        this.hb.move(this.tX, this.tY);
    }


    public void onEndOfTurn() {
        activateEffect();
    }
    
    public void onStartOfTurn() {}

    public void applyFocus() {
        super.applyFocus();
        
        if(index == ShadeCharacter.INDEX_SKELETON)
        {
        	int str = 0;
        	AbstractPower p = (AbstractPower)AbstractDungeon.player.getPower("Strength");
        	if(p!= null)
        	{
        		logger.info("increasing strength  by " + p.amount);
        		str=p.amount;
        	}
        	
        	int strBones = 0;
        	p = (AbstractPower)AbstractDungeon.player.getPower("Shade:StrongBonesPower");
        	if(p!= null)
        	{
        		logger.info("increasing strength of bones  by 3");
        		strBones = 3;
        	}

        	this.passiveAmount = this.basePassiveAmount + str + strBones;
        }
        else if(index == ShadeCharacter.INDEX_ZOMBIE)
        {
        	AbstractPower p = (AbstractPower)AbstractDungeon.player.getPower("Dexterity");
        	if(p!= null)
        	{
        		logger.info("increasing dex  by " + p.amount);
        		this.passiveAmount = this.basePassiveAmount + p.amount;
        	}
        	else
        		this.passiveAmount = this.basePassiveAmount;
        }
        else if(index == ShadeCharacter.INDEX_WRAITH)
        {
        	int soulPierce = 0;
        	AbstractPower p = (AbstractPower) AbstractDungeon.player.getPower("Shade:SoulPiercePower");
        	if(p!=null) {
        		soulPierce = p.amount;
        	}
        	
        	this.passiveAmount = this.basePassiveAmount + soulPierce;
        }
      
        
        //updateDescription();
    }

    public void applyUniqueFocus(int StrAmount) {

        logger.info("Torch head getting buffed by " + StrAmount);
        this.UniqueFocus = this.UniqueFocus + StrAmount;
        this.passiveAmount = this.passiveAmount + StrAmount;
        //updateDescription();
        //AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(this.cX, this.cY));
    }


    public void onEvoke() {
        triggerEvokeAnimation();
    }


    public void triggerEvokeAnimation() {

        CardCrawlGame.sound.play("DUNGEON_TRANSITION", 0.1F);

    }


    public void activateEffect() {
        activateEffectUnique();
    }

    public void activateEffectUnique() {
    }


    public void playChannelSFX() {

        CardCrawlGame.sound.play("SLIMED_ATK", 0.1F);

    }

    public void useFastAttackAnimation() {
        this.animationTimer = 0.4F;
        this.animationTimerStart = this.animationTimer;
        this.animation = AbstractCreature.CreatureAnimation.ATTACK_FAST;
    }

    @Override
    public void updateAnimation() {

        if (this.animationTimer != 0.0F) {
            switch (this.animation) {
                case ATTACK_FAST:
                    this.updateFastAttackAnimation();
                    break;
            }
        }

            this.cX = MathHelper.orbLerpSnap(this.cX, this.tX);
            this.cY = MathHelper.orbLerpSnap(this.cY, this.tY);


        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }

        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
    }


    public void postSpawnEffects() {
    }

    protected void updateFastAttackAnimation() {
        this.animationTimer -= Gdx.graphics.getDeltaTime();
        float targetPos = 50.0F * Settings.scale;


        if (this.animationTimer > (this.animationTimerStart / 2)) {
            this.animX = Interpolation.exp5Out.apply(0.0F, targetPos, ((this.animationTimerStart / 2) - (this.animationTimer - (this.animationTimerStart / 2))) / (this.animationTimerStart / 2));
            //logger.info("pow2Out " + ((this.animationTimerStart / 2) - (this.animationTimer - (this.animationTimerStart / 2))) / (this.animationTimerStart / 2));

        } else if (this.animationTimer < 0.0F) {
            this.animationTimer = 0.0F;
            this.animX = 0.0F;
        } else {
            //logger.info("fade " + this.animationTimer /(this.animationTimerStart / 2));
            this.animX = Interpolation.fade.apply(0.0F, targetPos, (this.animationTimer / (this.animationTimerStart / 2)));
        }

    }

    public void render(SpriteBatch sb) {      
        sb.setColor(this.c);
        sb.draw(img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        renderText(sb);
        this.hb.render(sb);
    }


    public void renderText(SpriteBatch sb) {
    	
//    	logger.info("rendering text");
        if (this.extraFontColor != null) {


            float fontOffset = 26 * Settings.scale;
            if (this.count > 9) fontOffset = fontOffset + (6 * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString((this.passiveAmount + this.passiveBonus)) + "\u00D7", this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);

            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.count), this.cX + this.NUM_X_OFFSET + fontOffset, this.cY + this.NUM_Y_OFFSET, this.extraFontColor, this.fontScale);

        } else {

            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString((this.passiveAmount + this.passiveBonus)), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);
        }
    }
	
	public void remove(int amount){
		if(count>amount) {
			count-= amount;
			triggerEvokeAnimation();
			this.updateDescription();
		}else{
	    	AbstractDungeon.player.orbs.set(index, new EmptySlot());
	        ((AbstractOrb)AbstractDungeon.player.orbs.get(index)).setSlot(index, AbstractDungeon.player.maxOrbs);
		}
	}
}
