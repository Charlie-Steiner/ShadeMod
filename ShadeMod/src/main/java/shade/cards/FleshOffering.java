package shade.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

import shade.ShadeMod;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;

public class FleshOffering
  extends AbstractShadeCard {
  public static final String ID = "Shade:FleshOffering";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/flesh_offering.png";
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

  private static final int COST = 3;
  
  public FleshOffering() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseBlock=13;
		this.block=this.baseBlock;
  }

  
  public AbstractCard makeCopy() { return new FleshOffering(); }
  
  public void upgrade() {    
	  if (!this.upgraded) {
	      upgradeName(); 
	      upgradeBlock(5);
	  }
	  
  }
  
  public void use(AbstractPlayer p, AbstractMonster arg1) {
	  
	  AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
	  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), 4));

	  AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Wraith()));
	  AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
  }
}
