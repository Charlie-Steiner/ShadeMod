package shade.cards;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class ScuttlingHand
  extends AbstractShadeCard
{
  public static final String ID = "Shade:ScuttlingHand";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/ScuttlingHand.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;

  public static final Logger logger = LogManager.getLogger(ShadeMod.class.getName());
  private static final int COST = 0;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public ScuttlingHand() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
    this.baseDamage = 4;
    this.damage=this.baseDamage;
    this.magicNumber = ShadeMod.combatExhausts;

  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}
	

  public AbstractCard makeCopy() { return new ScuttlingHand(); }

  
  public void triggerOnAnyExhaust() {

		AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(this));
  }
  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(2);
    } 
  }

}
