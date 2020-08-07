package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;


public class Bonestorm
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Bonestorm";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/bonestorm.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;



  private static final int COST = 1;


  
  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}


  
  
  public Bonestorm() { 
      super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, 
    		  AbstractCardEnum.SHADE, RARITY, TARGET);

      
      this.baseDamage = 7;
      this.isMultiDamage = true;
      
      this.baseMagicNumber=1;
      this.magicNumber=this.baseMagicNumber;
  }





  
  public AbstractCard makeCopy() { return new Bonestorm(); }
  
  public void upgrade() {
      if (!this.upgraded) {
          upgradeName();
          upgradeMagicNumber(1);
	        this.rawDescription=UPGRADED_DESCRIPTION;
	        initializeDescription();
      }
  }
  
  public void use(AbstractPlayer p, AbstractMonster arg1) {
	  AbstractDungeon.actionManager.addToBottom(
				new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		
      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Skeleton(),this.magicNumber));
  }
}
