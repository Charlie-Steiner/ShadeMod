package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.patches.AbstractCardEnum;
import shade.powers.CallOfTheGravePower;
import shade.powers.CallOfTheGravePowerUpgraded;


public class CallOfTheGrave
  extends AbstractCard
{
  public static final String ID = "Shade:CallOfTheGrave";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_power.png";
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
  
  public CallOfTheGrave() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
  }


  
  public AbstractCard makeCopy() { return new CallOfTheGrave(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    } 
  }
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  if(!this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CallOfTheGravePower(p), 1));
	  }else {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CallOfTheGravePowerUpgraded(p), 1));
	  }
  }
}
