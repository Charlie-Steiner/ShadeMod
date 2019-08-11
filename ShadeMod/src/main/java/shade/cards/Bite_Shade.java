package shade.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class Bite_Shade
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Bite_Shade";
  public static final String NAME = "Bite";
  public static final String DESCRIPTION = "Deal !D! damage. NL Heal !M! HP.";
  public static final String IMG_PATH = "cards/attackSlime.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  
  private static final int COST = 1;
  private static final int POWER = 7;
  private static final int HEAL_AMT = 2;
  private static final int UPGRADE_BONUS = 1;
  
  public Bite_Shade() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
    this.baseDamage = 7;
    this.baseMagicNumber = 2;
    this.magicNumber = this.baseMagicNumber;
    this.tags.add(AbstractCard.CardTags.HEALING);
  }

  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  if (m != null) {
	        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR), 0.3F));
	  }
	  AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
	  AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
  }



  
  public AbstractCard makeCopy() { return new Bite_Shade(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(3);
      upgradeMagicNumber(1);
    } 
  }
}
