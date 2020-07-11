package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
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

import shade.patches.AbstractCardEnum;
import shade.powers.FrenzyDownPower;


public class GreyBargain
  extends AbstractShadeCard
{
  public static final String ID = "Shade:GreyBargain";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/GreyBargain.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 0;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public GreyBargain() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  if(p.hand.size()>0) {
		  AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p,p,1,false));
		  AbstractDungeon.actionManager.addToBottom(new ReturnExhaustedToDeckAction(false));
	  }
	  if(this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
	  }
  }



  
  public AbstractCard makeCopy() { return new GreyBargain(); }

  
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		    upgradeBaseCost(0);
		    this.rawDescription=UPGRADED_DESCRIPTION;
		    initializeDescription();
		}
	}
}
