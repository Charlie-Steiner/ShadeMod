package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.patches.AbstractCardEnum;
import shade.powers.CarnophagePower;
import shade.powers.LichFormPower;
import shade.powers.StitchTogetherPower;

public class StitchTogether extends AbstractShadeCard {

	  public static final String ID = "Shade:StitchTogether";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/default_power.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	  
	  private static final CardStrings cardStrings;
	  private static final int COST = 1;
	
		static {
			cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			NAME = cardStrings.NAME;
			DESCRIPTION = cardStrings.DESCRIPTION;
			UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
		}
	  
	public StitchTogether() {
		super(ID,NAME,shade.ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
		this.baseMagicNumber=6;
		this.magicNumber=this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
	    if (!this.upgraded) {
	      upgradeName();
	      upgradeMagicNumber(3);
	    } 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StitchTogetherPower(p, this.magicNumber), this.magicNumber));
	}

}
