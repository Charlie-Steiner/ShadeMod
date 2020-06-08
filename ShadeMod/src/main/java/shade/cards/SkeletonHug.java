package shade.cards;

import basemod.helpers.BaseModCardTags;
import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;


public class SkeletonHug
  extends AbstractShadeCard
{
  public static final String ID = "Shade:SkeletonHug";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/default_attack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;
  
  private static final int COST = 2;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public SkeletonHug() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
    this.baseDamage = 6;
    this.damage=this.baseDamage;
    this.baseMagicNumber = 1;
    this.magicNumber=this.baseMagicNumber;
  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
	    AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton()));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber+1, false), this.magicNumber+1, true, AbstractGameAction.AttackEffect.NONE));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
	}

  
  public AbstractCard makeCopy() { return new SkeletonHug(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(3);
      upgradeMagicNumber(1);
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    } 
  }
}
