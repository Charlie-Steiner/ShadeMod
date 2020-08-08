package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import shade.ShadeMod;
import shade.actions.AsphyxiateAction;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;


public class Asphyxiate
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Asphyxiate";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/Asphyxiate.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;
  
  private static final int COST = 2;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public Asphyxiate() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
      this.baseMagicNumber=7;
      this.magicNumber=this.baseMagicNumber;
    
    this.exhaust = true;
  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new AsphyxiateAction(m,this.damageTypeForTurn));
		
	}
	
	public void calculateCardDamage(AbstractMonster mo) {
		
		this.baseDamage = 0;
		if(mo.hasPower("Poison")) {
			this.baseDamage += mo.getPower("Poison").amount;
		}
		
		if(!mo.hasPower("Artifact")) {
			this.baseDamage += this.magicNumber;
		}
	    super.calculateCardDamage(mo);
	    
	    this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
    	
	    initializeDescription();
	}
	
	public void unhover() {
	    super.unhover();
	    
		this.rawDescription = cardStrings.DESCRIPTION;
		
	    initializeDescription();
	}
	  
	public void onMoveToDiscard() {
		this.rawDescription = cardStrings.DESCRIPTION;
		
	    initializeDescription();
	}

  
  public AbstractCard makeCopy() { return new Asphyxiate(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeMagicNumber(2);
    } 
  }
}
