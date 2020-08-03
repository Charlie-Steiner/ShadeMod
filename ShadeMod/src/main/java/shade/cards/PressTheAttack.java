package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import shade.ShadeMod;
import shade.actions.RefreshUndeadPower;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;
import shade.powers.StrongBonesPower;


public class PressTheAttack
  extends AbstractShadeCard
{
  public static final String ID = "Shade:PressTheAttack";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/PressTheAttack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
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


  
  
  public PressTheAttack() { 
      super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, 
    		  AbstractCardEnum.SHADE, RARITY, TARGET);

      
      this.baseDamage = 9;
  }





  
  public AbstractCard makeCopy() { return new PressTheAttack(); }
  
  public void upgrade() {
      if (!this.upgraded) {
          upgradeName();
          upgradeDamage(1);
          this.rawDescription=UPGRADED_DESCRIPTION;
          initializeDescription();
      }
  }
  
  public void use(AbstractPlayer p, AbstractMonster m) {
	  AbstractDungeon.actionManager
		.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
	  
	  if(this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrongBonesPower(p,2), 2));
	  }else {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrongBonesPower(p,1), 1));
	  }
	  AbstractDungeon.actionManager.addToBottom(new RefreshUndeadPower());
  }
}
