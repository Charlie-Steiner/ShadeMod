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
import shade.actions.UndeadSpawnAction;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;

import shade.patches.AbstractCardEnum;
import shade.powers.FrenzyDownPower;


public class Brains
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Brains";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_skill.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 1;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public Brains() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));

	  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
  }



  
  public AbstractCard makeCopy() { return new Brains(); }

  
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		    upgradeMagicNumber(1);
		}
	}
}