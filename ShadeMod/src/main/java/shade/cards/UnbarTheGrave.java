package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.ReturnExhaustedToDeckAction;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;


public class UnbarTheGrave
  extends AbstractShadeCard
{
  public static final String ID = "Shade:UnbarTheGrave";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/unbar_the_grave.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 2;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public UnbarTheGrave() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
		this.exhaust = true;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {

      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton(),this.magicNumber));
      	  for(int i=0;i<this.magicNumber;i++) {
	      AbstractDungeon.actionManager.addToBottom(new ReturnExhaustedToDeckAction(true));
	  }
      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie(),this.magicNumber));
  }



  
  public AbstractCard makeCopy() { return new UnbarTheGrave(); }

  
  public void upgrade() {
	    if (!this.upgraded) {
	        upgradeName();
	        upgradeMagicNumber(1);
	      } 
  }
}
