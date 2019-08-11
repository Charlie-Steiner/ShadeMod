package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class WallOfFlesh
		extends AbstractCard {
	public static final String ID = "Shade:WallOfFlesh";
	public static final String NAME;
	public static final String DESCRIPTION;
	public static String UPGRADED_DESCRIPTION;
	public static final String IMG_PATH = "cards/default_skill.png";
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	private static final CardStrings cardStrings;

	private static final int COST = 1;

	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

	public WallOfFlesh() {
		super("Shade:WallOfFlesh", NAME, ShadeMod.getResourcePath("cards/default_skill.png"), 1, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
	}


  
  public AbstractCard makeCopy() { return new WallOfFlesh(); }
  
  public void upgrade() {}
  
  public void use(AbstractPlayer arg0, AbstractMonster arg1) {}
}
