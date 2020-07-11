package shade.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.ExhaustFromDeckAction;
import shade.patches.AbstractCardEnum;


public class RitualOffering
  extends AbstractShadeCard
{
  public static final String ID = "Shade:RitualOffering";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/RitualOffering.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 0;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public RitualOffering() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.exhaust = true;
		this.baseMagicNumber = 2;
		this.magicNumber = 2;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {

      AbstractDungeon.actionManager.addToBottom(new ExhaustFromDeckAction(this.magicNumber));
      if(this.upgraded) {
	      AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
	  }
  }



  
  public AbstractCard makeCopy() { return new RitualOffering(); }

  
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		    this.rawDescription=UPGRADED_DESCRIPTION;
		    initializeDescription();
		}
	}
}
