package shade.cards;

import basemod.helpers.BaseModCardTags;
import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;


public class Trample
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Trample";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/Trample.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;
  
  private static final int COST = 1;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public Trample() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
      
    this.baseMagicNumber=4;
    this.magicNumber=this.baseMagicNumber;
    
    this.baseDamage = 0;
    this.damage=this.baseDamage;
  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.baseDamage = 0;
		if (p.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof SpawnedUndead) {
			this.baseDamage += this.magicNumber*((SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_ZOMBIE)).count;
		}
		
		this.damage=this.baseDamage;
		
		DamageInfo d = new DamageInfo(p, this.damage, this.damageTypeForTurn);
		d.applyPowers(p, m);
		
		AbstractDungeon.actionManager
				.addToBottom(new DamageAction(m, d, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}
	
	public void calculateCardDamage(AbstractMonster mo) {
		
		this.baseDamage = 0;
		if (AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof SpawnedUndead) {
			this.baseDamage += this.magicNumber*((SpawnedUndead) AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_ZOMBIE)).count;
		}
		
	    super.calculateCardDamage(mo);
	    
	    this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
    	
	    initializeDescription();
	}

	  
	public void onMoveToDiscard() {
		this.rawDescription = cardStrings.DESCRIPTION;
		
	    initializeDescription();
	}
	
	public void triggerOnExhaust(){
		this.rawDescription = cardStrings.DESCRIPTION;

	    initializeDescription();
	}
  
  public AbstractCard makeCopy() { return new Trample(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeMagicNumber(1);
    } 
  }
}
