package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import shade.ShadeMod;
import shade.actions.RefreshUndeadPower;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.patches.AbstractCardEnum;
import shade.powers.CallOfTheGravePower;
import shade.powers.CallOfTheGravePowerUpgraded;
import shade.powers.StrongBonesPower;
import shade.orbs.SpawnedUndead;


public class LyeBath
  extends AbstractShadeCard
{
  public static final String ID = "Shade:LyeBath";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/lye_bath.png";
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

  
	public LyeBath() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  AbstractOrb z = p.orbs.get(ShadeCharacter.INDEX_ZOMBIE);
	  int n = 0;
	  if(z instanceof SpawnedUndead) {
		  n=((SpawnedUndead) z).count;
		  ((SpawnedUndead) z).remove(n);
	      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton(),n));
	  }
	  
	  if(this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,2));
	  }else {
		  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
	  }
  }
  

  
  public AbstractCard makeCopy() { return new LyeBath(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
	    this.rawDescription=UPGRADED_DESCRIPTION;
	    initializeDescription();
    }
  }
}
