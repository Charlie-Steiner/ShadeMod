package shade.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class Strike_Shade
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Strike_Shade";
  public static final String NAME = "Strike";
  public static final String DESCRIPTION = "Deal !D! damage.";
  public static final String IMG_PATH = "cards/Strike_Shade.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  
  private static final int COST = 1;
  private static final int POWER = 6;
  private static final int UPGRADE_BONUS = 3;
  
  public Strike_Shade() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
    this.baseDamage = 6;
    this.tags.add(BaseModCardTags.BASIC_STRIKE);
    this.tags.add(AbstractCard.CardTags.STRIKE);
  }

  
  public void use(AbstractPlayer p, AbstractMonster m) { AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT)); }



  
  public AbstractCard makeCopy() { return new Strike_Shade(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(3);
    } 
  }
}
