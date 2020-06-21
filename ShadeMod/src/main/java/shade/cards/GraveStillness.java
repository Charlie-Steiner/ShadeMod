package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;
import shade.powers.GraveStillnessPower;
import shade.powers.LichFormPower;


public class GraveStillness extends AbstractShadeCard {
	public static final String ID = "Shade:GraveStillness";
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

	public GraveStillness() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		this.baseBlock = 5;
		this.block = this.baseBlock;
	}


  
  public AbstractCard makeCopy() { return new GraveStillness(); }
  
  public void upgrade() {
	  if(!this.upgraded)
	  {
		  upgradeName();
		  upgradeBlock(3);
	  }
  }
  
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
	  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GraveStillnessPower(p, 1), 1));
  }
}
