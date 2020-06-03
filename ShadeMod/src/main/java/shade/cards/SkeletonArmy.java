package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadSpawnAction;
import shade.orbs.Skeleton;
import shade.patches.AbstractCardEnum;


public class SkeletonArmy
  extends AbstractShadeCard
{
  public static final String ID = "Shade:CommandSoldier";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/default_attack.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
  

  private static final int COST = 1;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public SkeletonArmy() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  Skeleton skele = new shade.orbs.Skeleton();
      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(skele));
      if(this.upgraded)
          AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,(skele.passiveAmount+skele.passiveBonus)*skele.count, AbstractGameAction.AttackEffect.BLUNT_LIGHT,skele,false,false,0,true,0));
      else
          AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,(skele.passiveAmount+skele.passiveBonus), AbstractGameAction.AttackEffect.BLUNT_LIGHT,skele,false,false,0,true,0));

  }



  
  public AbstractCard makeCopy() { return new SkeletonArmy(); }

  
  public void upgrade() {
    if (!this.upgraded)
    	upgradeName(); 
	    this.rawDescription=UPGRADED_DESCRIPTION;
	    initializeDescription();
  }
}
