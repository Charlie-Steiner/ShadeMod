package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shade.ShadeMod;
import shade.actions.ReturnExhaustedToDeckAction;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;

import shade.patches.AbstractCardEnum;
import shade.powers.FrenzyDownPower;


public class LoseControl
  extends AbstractShadeCard
{
  public static final String ID = "Shade:LoseControl";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/lose_control.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 0;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public LoseControl() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=3;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wound(), 2));
	  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), 1));
	  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
  }



  
  public AbstractCard makeCopy() { return new LoseControl(); }

  
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		    upgradeMagicNumber(1);
		}
	}
}
