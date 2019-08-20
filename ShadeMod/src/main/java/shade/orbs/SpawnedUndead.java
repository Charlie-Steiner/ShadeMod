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
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.animations.AbstractAnimation;
import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.vfx.FakeFlashAtkImgEffect;

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
    public int slimeBonus;
    public boolean movesToAttack;
    public int upgradedInitialBoost;
    public String originalRelic = "";
    public String[] descriptions;
    public com.badlogic.gdx.graphics.Texture intentImage;
    private Color deathColor;
    private Color modelColor;
    public static String orbID = "";
    public boolean noEvokeBonus;
    private float scale = 1F;
    private static int W;
    private Texture img;
    private float x;
    private float px;
    public static SkeletonMeshRenderer sr;
    private AbstractCreature.CreatureAnimation animation;
    private float animationTimer;

    private float animationTimerStart;
    private TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationState state;
    private AnimationStateData stateData;
    private AbstractAnimation animationA;
    public AbstractPlayer p;
    public Color projectileColor;
    private float delayTime;
    private boolean hasSplashed;
    private boolean madePostDuplicated;
    private boolean renderBehind;
    private String atlasString = "images/monsters/theBottom/slimeAltS/skeleton.atlas";
    private String skeletonString = "images/monsters/theBottom/slimeAltS/skeleton.json";
    private String animString = "idle";

    public String customDescription;
    private int yOffset;
    public int debuffBonusAmount;
    public int debuffAmount;
    public int health;
    public int count;
    public int healthBonus;
    public Color extraFontColor = null;
    public boolean topSpawnVFX = false;



	public SpawnedUndead(String ID, int yOffset, Color projectileColor, String atlasString, String skeletonString,
			String animString, float scale, Color modelColor, int passive, int initialBoost, boolean movesToAttack,
			Color deathColor, Texture intentImage, String IMGURL) {

        this.scale = scale;
        this.modelColor = modelColor;
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        //this.renderBehind=true;
        SkeletonJson json = new SkeletonJson(this.atlas);


        json.setScale(Settings.scale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonString));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        AnimationState.TrackEntry e = this.state.setAnimation(0, animString, true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
        this.delayTime = 0.27F;
        this.yOffset = yOffset;

        this.ID = ID;

 //       this.img = ImageMaster.loadImage(IMGURL);


        this.basePassiveAmount = passive;
        this.movesToAttack = movesToAttack;

        this.deathColor = deathColor;

        this.evokeAmount = 1;

        this.passiveAmount = this.basePassiveAmount;
        this.intentImage = intentImage;
        this.upgradedInitialBoost = initialBoost;


        this.channelAnimTimer = 0.5F;

        this.projectileColor = projectileColor;
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

        this.activatedThisTurn = false;

    }


    public void onStartOfTurn() {


        activateEffect();

    }



    /*
    public void update() {

    }
    */

    public void applyFocus() {
        super.applyFocus();
        
        updateDescription();
    }

    public void applyUniqueFocus(int StrAmount) {

        logger.info("Torch head getting buffed by " + StrAmount);
        this.UniqueFocus = this.UniqueFocus + StrAmount;
        this.passiveAmount = this.passiveAmount + StrAmount;
        updateDescription();
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
    
    	//logger.info("rendering");

        if (this.delayTime > 0F) {
            delayTime = delayTime - Gdx.graphics.getDeltaTime();
        } else if (this.atlas == null) {
             //logger.info("rendering null");
            sb.setColor(new Color(1F, 1F, 1F, 2F));

            sb.draw(this.img, this.cX - (float) this.img.getWidth() + this.animX * Settings.scale / 2.0F, this.cY + this.animY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        } else {
            if (!hasSplashed) {
            	AbstractDungeon.effectsQueue.add(new FakeFlashAtkImgEffect(this.cX, this.cY, projectileColor, 0.75F, true, 0.3F));            
                this.hasSplashed = true;
                postSpawnEffects();
            } else {

                // logger.info("rendering skeleton model");
                this.state.update(Gdx.graphics.getDeltaTime());
                this.state.apply(this.skeleton);
                this.skeleton.updateWorldTransform();
                this.skeleton.setPosition(this.cX + this.animX, this.cY + this.animY + this.yOffset);
                //logger.info("x = " + this.cX + " y = " + (this.cY + AbstractDungeon.sceneOffsetY));

                this.skeleton.setColor(modelColor);
                this.skeleton.setFlip(true, false);
                sb.end();
                CardCrawlGame.psb.begin();
                AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
                sb.setBlendFunction(770, 771);

            }
            renderText(sb);

        }
        //this.hb.render(sb);
    }


    public void renderText(SpriteBatch sb) {
    	
//    	logger.info("rendering text");
        if (this.extraFontColor != null) {


            float fontOffset = 26 * Settings.scale;
            if (this.passiveAmount > 9) fontOffset = fontOffset + (6 * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, this.passiveAmount*this.count + "/", this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);



            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString((this.health + this.healthBonus)*this.count), this.cX + this.NUM_X_OFFSET + fontOffset, this.cY + this.NUM_Y_OFFSET, this.extraFontColor, this.fontScale);

        } else {

            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);
        }
    }
	
}
