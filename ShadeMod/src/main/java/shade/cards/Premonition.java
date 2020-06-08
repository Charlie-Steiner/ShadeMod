package shade.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;


public class Premonition
  extends AbstractShadeCard
{
  public static final String ID = "Shade:Premonition";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static final String IMG_PATH = "cards/default_attack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
  public static String UPGRADED_DESCRIPTION;
  
  private static final int COST = 0;
  
  private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
  
  public Premonition() {
      super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);
   
	    this.baseBlock=5;
	    this.block=this.baseBlock;

  }

  
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block)); 
	}
  
  public AbstractCard makeCopy() { return new Premonition(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeBlock(3);
    } 
  }
}
