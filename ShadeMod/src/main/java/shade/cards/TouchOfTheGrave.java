package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.patches.AbstractCardEnum;
import shade.powers.TouchOfTheGravePower;
import shade.powers.TouchOfTheGravePowerUpgraded;


public class TouchOfTheGrave
  extends AbstractShadeCard
{
  public static final String ID = "Shade:TouchOfTheGrave";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/TouchOfTheGrave.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

  
  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  private static final int COST = 1;
  
  public TouchOfTheGrave() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
  }


  
  public AbstractCard makeCopy() { return new TouchOfTheGrave(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      this.isInnate=true;
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    } 
  }
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  if(this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TouchOfTheGravePowerUpgraded(p), 1));
	  }else {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TouchOfTheGravePower(p), 1));
	  }
  }
}
