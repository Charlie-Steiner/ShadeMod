package shade.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import shade.ShadeMod;
import shade.orbs.SpawnedUndead;



public class UndeadAutoAttack extends AbstractGameAction {

    private static final float DURATION = 0.01F;
    private AbstractCreature owner;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int damage;
    private int debuffamount;
    private int block;
    private AbstractPower p;
    private AttackEffect AE;
    public static final Logger logger = LogManager.getLogger(ShadeMod.class.getName());
    private SpawnedUndead undead;
    private boolean beamVFX;
    private boolean appliesPoison;
    private boolean appliesSlimed;
    private boolean appliesWeak;


    public UndeadAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedUndead undead, boolean appliesPoison, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount=debuffamount;
        this.AE=AE;
        this.damage=damage;
        this.undead=undead;
        this.block=block;
        this.beamVFX=beamVFX;
        this.appliesPoison=appliesPoison;
        this.appliesWeak=appliesWeak;

    }

    public void update() {

            logger.info("Starting auto attack");
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        logger.info("Finding target");
        float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();

        if (Settings.FAST_MODE) {

            speedTime = 0.10F;

        }
        AbstractCreature mo = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (mo != null) {
        	

            if (this.block > 0 ) AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));


            AbstractDungeon.actionManager.addToTop(new DamageAction(mo,
                    new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS),
                    AE));

            if (this.appliesPoison) AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.POISON));
            if (this.appliesWeak)  AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.debuffamount, false), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));


            //logger.info("Targeting " + mo.name);

        }


        this.isDone = true;
    }
	
	
}
