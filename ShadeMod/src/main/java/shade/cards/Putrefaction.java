package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.actions.RefreshUndeadPower;
import shade.patches.AbstractCardEnum;
import shade.powers.PutrefactionPower;

public class Putrefaction extends AbstractShadeCard {

	  public static final String ID = "Shade:Putrefaction";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/putrefaction.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	  
	  private static final CardStrings cardStrings;
	  private static final int COST = 2;
	
		static {
			cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			NAME = cardStrings.NAME;
			DESCRIPTION = cardStrings.DESCRIPTION;
			UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
		}
	  
	public Putrefaction() {
		super(ID,NAME,shade.ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
	    if (!this.upgraded) {
	      upgradeName();

	      upgradeBaseCost(1);
	      //this.isInnate = true;
	      this.rawDescription=UPGRADED_DESCRIPTION;
	      initializeDescription();
	    } 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PutrefactionPower(p, this.magicNumber), this.magicNumber));
	}

}
