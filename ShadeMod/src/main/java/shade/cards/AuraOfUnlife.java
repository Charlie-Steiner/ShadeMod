package shade.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.ShadeMod;
import shade.patches.AbstractCardEnum;
import shade.powers.LichFormPower;

public class AuraOfUnlife extends AbstractShadeCard {

	  public static final String ID = "Shade:AuraOfUnlife";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/default_power.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	  
	  private static final CardStrings cardStrings;
	  private static final int COST = 1;
	
		static {
			cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			NAME = cardStrings.NAME;
			DESCRIPTION = cardStrings.DESCRIPTION;
			UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
		}
	  
	public AuraOfUnlife() {
		super(ID,NAME,shade.ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
		this.baseMagicNumber=0;
		this.magicNumber=this.baseMagicNumber;
	}
	
	public void applyPowers() {
	    super.applyPowers();
	    
		if(!this.upgraded) {
			this.baseMagicNumber=AbstractDungeon.player.getPower("Shade:MinionsPower").amount + 1;
		}else {
			this.baseMagicNumber=AbstractDungeon.player.getPower("Shade:MinionsPower").amount + 2;
		}
		this.magicNumber=this.baseMagicNumber;
	    
	    if (this.magicNumber > 0) {
	      this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
	      initializeDescription();
	    }
	 }
	  
		
	public void onMoveToDiscard() {
	    this.rawDescription = cardStrings.DESCRIPTION;
	    initializeDescription();
	}

	@Override
	public void upgrade() {
	    if (!this.upgraded) {
	      upgradeName();
		    this.rawDescription=UPGRADED_DESCRIPTION;
		    initializeDescription();
	    } 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if(!this.upgraded) {
			p.getPower("Shade:MinionsPower").amount += 1;
		}else {
			p.getPower("Shade:MinionsPower").amount += 2;
		}
	}

}
