package shade.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;

public class CallWraith
  extends AbstractShadeCard {
  public static final String ID = "Shade:CallWraith";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/CallWraith.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;





  
  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  private static final int COST = 2;
  
  public CallWraith() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=5;
		this.magicNumber=this.baseMagicNumber;
		
		this.exhaust = true;
  }

  
  public AbstractCard makeCopy() { return new CallWraith(); }
  
  public void upgrade() {    
	  if (!this.upgraded) {
	      upgradeName(); 
	      upgradeBaseCost(1);
	  }
	  
  }
  
  public void use(AbstractPlayer p, AbstractMonster arg1) {
	  
	  AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
	  AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
	  AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Wraith()));
	  
  }
}
