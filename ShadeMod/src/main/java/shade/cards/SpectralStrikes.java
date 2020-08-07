package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.patches.AbstractCardEnum;
import shade.powers.SpectralStrikesPower;


public class SpectralStrikes
  extends AbstractShadeCard
{
  public static final String ID = "Shade:SpectralStrikes";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/SpectralStrikes.png";
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
  
  public SpectralStrikes() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
  
      this.tags.add(AbstractCard.CardTags.STRIKE);
  }


  
  public AbstractCard makeCopy() { return new SpectralStrikes(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      this.isInnate = true;
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    } 
  }
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpectralStrikesPower(p), 1));
  }
}
