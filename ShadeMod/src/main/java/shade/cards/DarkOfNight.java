package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.RefreshUndeadPower;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;
import shade.powers.CallOfTheGravePower;
import shade.powers.CallOfTheGravePowerUpgraded;
import shade.powers.DarkOfNightPower;
import shade.powers.StrongBonesPower;


public class DarkOfNight
  extends AbstractShadeCard
{
  public static final String ID = "Shade:DarkOfNight";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/dark_of_night.png";
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

  
	public DarkOfNight() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=1;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
      AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkOfNightPower(p,this.magicNumber), this.magicNumber));
      CardCrawlGame.sound.play("EVENT_SPIRITS", 0.05F);
  }



  
  public AbstractCard makeCopy() { return new DarkOfNight(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeMagicNumber(1);
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    }
  }
}
