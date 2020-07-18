package shade.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;


public class FleshToFlesh
		extends AbstractShadeCard {
	public static final String ID = "Shade:FleshToFlesh";
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

	public FleshToFlesh() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
	}


  
  public AbstractCard makeCopy() { return new FleshToFlesh(); }
  
  public void upgrade() {
	  if(!this.upgraded)
	  {
		  upgradeName();
		  upgradeMagicNumber(1);
	  }
	  	
  }
  
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  
	  ShadeMod.logger.info("Use " + ID);
	  for(int i=0;i<this.magicNumber;i++){
		  AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
	  }
	  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), 1));
  }
}
