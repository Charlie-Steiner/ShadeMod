package shade.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.ShadeMod;
import shade.actions.RavenousHordeAction;
import shade.patches.AbstractCardEnum;

public class RavenousHorde extends AbstractShadeCard {

	  public static final String ID = "Shade:RavenousHorde";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/default_skill.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
	  

	  private static final int COST = -1;

	  private static final CardStrings cardStrings;
	  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
	
	public RavenousHorde() {
		super(ID,NAME,ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AbstractCard makeCopy() {
		return new RavenousHorde();
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
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
		int bonus=0;
		if(this.upgraded)
			bonus=1;
		
		AbstractDungeon.actionManager.addToBottom(new RavenousHordeAction(this.energyOnUse+bonus));
		
	}

}
