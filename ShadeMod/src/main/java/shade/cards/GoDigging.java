package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import shade.ShadeMod;
import shade.actions.RefreshUndeadPower;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;
import shade.powers.CallOfTheGravePower;
import shade.powers.CallOfTheGravePowerUpgraded;
import shade.powers.GoneDiggingPower;
import shade.powers.StrongBonesPower;


public class GoDigging
  extends AbstractShadeCard
{
  public static final String ID = "Shade:GoDigging";
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

  
	public GoDigging() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  AbstractOrb z = p.orbs.get(ShadeCharacter.INDEX_SKELETON);
	  int n = 0;
	  if(!(z instanceof EmptyOrbSlot)) {
		  n=((SpawnedUndead) z).count;
		  if(n>this.magicNumber) {
			  n=this.magicNumber;
		  }
		  ((SpawnedUndead) z).remove(n);
		  
	      AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoneDiggingPower(p,2*n), 2*n));
	  }
  }



  
  public AbstractCard makeCopy() { return new GoDigging(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeMagicNumber(2);
    }
  }
}
