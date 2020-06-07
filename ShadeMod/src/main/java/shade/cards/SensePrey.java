package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;


public class SensePrey
  extends AbstractShadeCard
{
  public static final String ID = "Shade:SensePrey";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_skill.png";
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

  
	public SensePrey() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		
		this.exhaust = true;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {

      int count = 0;
      for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
        if (!mon.isDeadOrEscaped()) {
          count++;
        }
      } 
      for (int i = 0; i < count; i++) {
    	  AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton()));
      }
  }



  
  public AbstractCard makeCopy() { return new SensePrey(); }

  
  public void upgrade() {
	    if (!this.upgraded) {
	        upgradeName();
	        this.isInnate = true;
	        this.rawDescription=UPGRADED_DESCRIPTION;
	        initializeDescription();
	      } 
  }
}
