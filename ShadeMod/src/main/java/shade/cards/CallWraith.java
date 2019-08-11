package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;

public class CallWraith
  extends AbstractCard {
  public static final String ID = "Shade:CallWraith";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_skill.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;





  
  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  private static final int COST = 2;
  
  public CallWraith() {
    super("Shade:CallWraith", NAME, ShadeMod.getResourcePath("cards/default_skill.png"), 2, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
    
    this.exhaust = true;
  }


  
  public AbstractCard makeCopy() { return new CallWraith(); }
  
  public void upgrade() {}
  
  public void use(AbstractPlayer arg0, AbstractMonster arg1) {}
}
