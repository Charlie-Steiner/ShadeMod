package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;

public class DangerousRitual extends AbstractShadeCard {
	public static final String ID = "Shade:DangerousRitual";
	public static final String NAME;
	public static final String DESCRIPTION;
	public static final String IMG_PATH = "cards/dangerous_ritual.png";
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
	public static String UPGRADED_DESCRIPTION;

	private static final int COST = 4;
	
	private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

	public DangerousRitual() {
		super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE,
				RARITY, TARGET);

		this.baseDamage = 18;
		this.damage = this.baseDamage;
		this.baseMagicNumber = 2;
		this.magicNumber = this.baseMagicNumber;

		this.isMultiDamage = true;
		
	}

	  public void applyPowers() {
		    super.applyPowers();
		    
		    if (ShadeMod.combatExhausts>=this.magicNumber) {
		    	this.isCostModified = true;
		    	int diff = this.cost - this.costForTurn;
		    	if(ShadeMod.combatExhausts>=COST*this.magicNumber) {
		    		this.cost=0;
		    	}else {
		    		this.cost = COST-ShadeMod.combatExhausts/this.magicNumber;
		    	}
	    		this.costForTurn = this.cost - diff;
	            if (this.costForTurn < 0) {
	                this.costForTurn = 0;
	            }
		    }
	  }

	public void use(AbstractPlayer p, AbstractMonster m) {
	    CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
	    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));		
	}

	public AbstractCard makeCopy() {
		return new DangerousRitual();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(6);
		}
	}
}
