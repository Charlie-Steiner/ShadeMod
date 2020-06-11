package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class Tombstone
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Tombstone";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/default_attack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;
  
  private static final int COST = 3;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public Tombstone() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
    this.baseDamage = 36;
    this.damage=this.baseDamage;
    
    this.exhaust = true;
  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
	    if (m != null) {
	    	AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
	    }
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
	}

  
  public AbstractCard makeCopy() { return new Tombstone(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(12);
    } 
  }
}
