package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

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


public class SoulToxin
  extends AbstractShadeCard
{
  public static final String ID = "Shade:SoulToxin";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/SoulToxin.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  

  private static final int COST = 1;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public SoulToxin() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=15;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	    int hpLoss=3;
	    int weakness=2;
	    if(this.upgraded) {
	    	hpLoss=5;
	    	weakness=3;
	    }
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, weakness, false), weakness, true, AbstractGameAction.AttackEffect.NONE));

    	AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, p, hpLoss, AbstractGameAction.AttackEffect.FIRE));
    	
    	if(p.orbs.get(ShadeCharacter.INDEX_WRAITH) instanceof SpawnedUndead) {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
    	}
  }

  
  public AbstractCard makeCopy() { return new SoulToxin(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeMagicNumber(10);
      this.rawDescription=UPGRADED_DESCRIPTION;
      initializeDescription();
    }
  }
}
