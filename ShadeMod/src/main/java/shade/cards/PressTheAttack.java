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
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;


public class PressTheAttack
  extends AbstractShadeCard
{
  public static final String ID = "Shade:PressTheAttack";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_attack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;



  private static final int COST = 1;


  
  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}


  
  
  public PressTheAttack() { 
      super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, 
    		  AbstractCardEnum.SHADE, RARITY, TARGET);

      
      this.baseDamage = 4;
      this.isMultiDamage = true;
      this.baseMagicNumber=2;
      this.magicNumber=this.baseMagicNumber;
  }





  
  public AbstractCard makeCopy() { return new PressTheAttack(); }
  
  public void upgrade() {
      if (!this.upgraded) {
          upgradeName();
          upgradeMagicNumber(1);
      }
  }
  
  public void use(AbstractPlayer p, AbstractMonster arg1) {
	    int nUndead=0;
		if (p.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof SpawnedUndead) {
			nUndead += ((SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_ZOMBIE)).count;
		}
		if (p.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead) {
			nUndead += ((SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_SKELETON)).count;
		}
		
		int[] buffedDamage = this.multiDamage;
		for(int i=0;i<buffedDamage.length;i++) {
			buffedDamage[i]+=this.magicNumber*nUndead;
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, buffedDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
  }
}
