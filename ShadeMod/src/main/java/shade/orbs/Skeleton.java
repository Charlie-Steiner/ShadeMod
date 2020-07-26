package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadDecay;
import shade.characters.ShadeCharacter;
import shade.ui.ShadeTipTracker;

public class Skeleton extends SpawnedUndead {


	public static final String ID = "Shade:Skeleton";
	public static final int damage = 3;
	
	
	public Skeleton()
	{

		super(ID, new Color(1.0F, 1.0F, 1.0F, 100F), 0, damage, true, 
				"ShadeImages/orbs/skeleton.png",ShadeCharacter.INDEX_SKELETON);
		this.extraFontColor = Color.ROYAL.cpy();
		this.count = 1;
		this.passiveBonus = 0;
		updateDescription();
		spawnVFX();
		setSlot(ShadeCharacter.INDEX_SKELETON,3);
	}
	
    public void applyFocus() {
        super.applyFocus();
        
    	int str = 0;
    	AbstractPower p = (AbstractPower)AbstractDungeon.player.getPower("Strength");
    	if(p!= null)
    	{
    		logger.info("increasing strength  by " + p.amount/2);
    		str=p.amount;
    	}
    	
    	int strBones = 0;
    	p = (AbstractPower)AbstractDungeon.player.getPower("Shade:StrongBonesPower");
    	if(p!= null)
    	{
    		logger.info("increasing strength of bones  by 3");
    		strBones = 3;
    	}

    	this.passiveAmount = this.basePassiveAmount + str/2 + strBones;
    }
	
	@Override
	public AbstractOrb makeCopy() {
		return new Skeleton();
	}

	@Override
	public void updateDescription() {

		this.applyFocus();
		int damagePer = this.passiveAmount+this.passiveBonus;
		int decayAmt = UndeadDecay.getDecay(this.count);

		String firstPlural;
		String secondPlural;
		String thirdPlural;
		if(count==1) {
			firstPlural = this.descriptions[4];
			secondPlural = this.descriptions[5];
		}else {
			firstPlural = this.descriptions[1];
			secondPlural = this.descriptions[2];
		}
		if(decayAmt==1) {
			thirdPlural = this.descriptions[6];
		}else {
			thirdPlural = this.descriptions[3];
		}
			
		
        this.description = this.descriptions[0] + this.count + firstPlural + damagePer + secondPlural + decayAmt + thirdPlural;
	}

    public void onStartOfTurn() {
    	AbstractDungeon.actionManager.addToTop(new UndeadDecay(ShadeCharacter.INDEX_SKELETON));
    }
	
    public static int smallThreshold = 12;
    public static int bigThreshold = 30;
    
    public void activateEffectUnique() {
    	int damageTot = this.passiveAmount + this.passiveBonus;

    	ShadeTipTracker.checkForTip(ShadeTipTracker.TipKey.SkeletonTip);
    	
    	boolean intangibles = false;
        for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
            if (!mon.isDeadOrEscaped()) {
              if(mon.hasPower("Intangible") || mon.hasPower("Buffer") || mon.currentHealth<=this.passiveAmount) {
            	  intangibles=true;
              }
            }
          } 
    	
    	if(this.count>bigThreshold && !intangibles) {
    		int bigs = (this.count-4)/5;
    		int remain = (this.count-4)%5+4;
	    	for(int i=0; i<bigs;i++) {
	    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,damageTot*5, AbstractGameAction.AttackEffect.BLUNT_HEAVY,this,false,false,0,true,0));
	    	}
	    	for(int i=0; i<remain;i++) {
	    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,damageTot, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));
	    	}
    	}else if(this.count>smallThreshold && !intangibles) {
    		int bigs = (this.count-4)/3;
    		int remain = (this.count-4)%3+4;
	    	for(int i=0; i<bigs;i++) {
	    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,damageTot*3, AbstractGameAction.AttackEffect.BLUNT_HEAVY,this,false,false,0,true,0));
	    	}
	    	for(int i=0; i<remain;i++) {
	    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,damageTot, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));
	    	}
    	}else{
	    	for(int i=0; i<this.count;i++) {
	    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,damageTot, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));
	    	}
    	}
    }

}
