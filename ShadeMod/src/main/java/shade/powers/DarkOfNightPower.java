package shade.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import shade.ShadeMod;
import shade.characters.ShadeCharacter;


public class DarkOfNightPower extends AbstractPower {
	
    public static final String POWER_ID = "Shade:DarkOfNightPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/FirmFortitude.png";

    public static String[] DESCRIPTIONS;
    
    public DarkOfNightPower(AbstractCreature owner, int newAmount)
    {
    	this.ID = POWER_ID;
    	this.owner = owner;
    	this.amount = newAmount;
        this.img = new com.badlogic.gdx.graphics.Texture(ShadeMod.getResourcePath(IMG));
        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
    	updateDescription();
    	

    }
    
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.exhaust && this.amount > 0) {
          flash();
          AbstractMonster m = null;
          
          if (action.target != null) {
            m = (AbstractMonster)action.target;
          }
          
          AbstractCard tmp = card.makeSameInstanceOf();
          AbstractDungeon.player.limbo.addToBottom(tmp);
          tmp.current_x = card.current_x;
          tmp.current_y = card.current_y;
          tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
          tmp.target_y = Settings.HEIGHT / 2.0F;
          
          if (m != null) {
            tmp.calculateCardDamage(m);
          }
          
          tmp.purgeOnUse = true;
          AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);

          
          this.amount--;
          if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
          }
        } 
      }
    

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
    
	public void updateDescription() {
	    if (this.amount == 1) {
	        this.description = DESCRIPTIONS[0];
	      } else {
	        this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	      } 
	}
	
}
