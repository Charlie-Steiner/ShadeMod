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
import shade.powers.LichFormPower;
import shade.powers.SoulPiercePower;

public class SoulPierce extends AbstractShadeCard {

	  public static final String ID = "Shade:SoulPierce";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/soul_pierce.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	  
	  private static final CardStrings cardStrings;
	  private static final int COST = 1;
	
		static {
			cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			NAME = cardStrings.NAME;
			DESCRIPTION = cardStrings.DESCRIPTION;
			UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
		}
	  
	public SoulPierce() {
		super(ID,NAME,shade.ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
		this.baseMagicNumber=40;
		this.magicNumber=this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
	    if (!this.upgraded) {
	      upgradeName();
	      upgradeMagicNumber(20);
	    } 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SoulPiercePower(p, this.magicNumber), this.magicNumber));
		
	    AbstractDungeon.actionManager.addToBottom(new RefreshUndeadPower());
	}

}
