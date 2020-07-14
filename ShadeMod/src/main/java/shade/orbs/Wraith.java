package shade.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadDecay;
import shade.characters.ShadeCharacter;
import shade.ui.ShadeTipTracker;

public class Wraith extends SpawnedUndead {


	public static final String ID = "Shade:Wraith";
	public static final int damage = 30;
	
	public Wraith()
	{
		super(ID, new Color(1.0F, 1.0F, 1.0F, 100F), 0, damage, false, 
				"ShadeImages/orbs/wraith.png",ShadeCharacter.INDEX_WRAITH);
		this.extraFontColor = Color.ROYAL.cpy();
		this.count = 1;
		this.passiveBonus = 0;
		updateDescription();
		spawnVFX();
		setSlot(ShadeCharacter.INDEX_WRAITH,3);
		
		ShadeTipTracker.checkForTip(ShadeTipTracker.TipKey.WraithTip);
	}
	
    public void applyFocus() {
        super.applyFocus();
        
    	int soulPierce = 0;
    	AbstractPower p = (AbstractPower) AbstractDungeon.player.getPower("Shade:SoulPiercePower");
    	if(p!=null) {
    		soulPierce = p.amount;
    	}
    	
    	this.passiveAmount = this.basePassiveAmount + soulPierce;  
    }
	
	
	@Override
	public AbstractOrb makeCopy() {
		return new Wraith();
	}

	@Override
	public void updateDescription() {

		String firstPlural;
		if(count==1) {
			firstPlural = this.descriptions[1];
		}else {
			firstPlural = this.descriptions[2];
		}
		
        this.description = this.descriptions[0] + this.count + firstPlural + this.descriptions[3];
	}
	
    public void onStartOfTurn() {
    	AbstractPlayer p = AbstractDungeon.player;

    	AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33F));
    	
    	AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.passiveAmount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
    	
    	this.remove(1);
    }
	
    public void activateEffectUnique() {
    	if(this.debuffAmount>0) {
    		AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,this.passiveAmount*this.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,0,true,0));
    	}
    }

}
