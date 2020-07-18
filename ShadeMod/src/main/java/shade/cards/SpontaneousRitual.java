package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.ExhaustFromHandAction;
import shade.patches.AbstractCardEnum;

public class SpontaneousRitual extends AbstractShadeCard {
	public static final String ID = "Shade:SpontaneousRitual";
	public static final String NAME;
	public static final String DESCRIPTION;
	public static final String IMG_PATH = "cards/spontaneous_ritual.png";
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
	public static String UPGRADED_DESCRIPTION;

	private static final int COST = 1;
	
	private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

	public SpontaneousRitual() {
		super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE,
				RARITY, TARGET);

		this.baseDamage = 0;
		this.damage = this.baseDamage;
		this.baseMagicNumber=0;
		this.magicNumber=this.baseMagicNumber;
		
	}

	  public void applyPowers() {
	    super.applyPowers();
	    
	    this.baseDamage=ShadeMod.combatExhausts;
	    this.damage=this.baseDamage;
	    this.baseMagicNumber=ShadeMod.combatExhausts;
		this.magicNumber=this.baseMagicNumber;
	    
	    if (this.magicNumber > 0) {
	    	if(this.upgraded) {
	    		this.rawDescription = UPGRADED_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
	    	}else {
	    		this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
	    	}
	    	initializeDescription();
	    }
	    

	  }

	public void use(AbstractPlayer p, AbstractMonster m) {

	    this.baseDamage=ShadeMod.combatExhausts;
	    this.damage=this.baseDamage;
	    int tempDamage=this.damage+p.hand.size();
	    
	    if(p.hand.contains(this)) {
	    	tempDamage-= 1;
	    }

	    
		if(this.upgraded) {
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Wound(), 3));
			tempDamage+=3;
		}
		
		AbstractDungeon.actionManager.addToBottom(new ExhaustFromHandAction(10,true,false,false));

	    CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
	    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, tempDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
		
	}
	
	  public void calculateCardDamage(AbstractMonster mo) {
	    super.calculateCardDamage(mo);
	    if (ShadeMod.combatExhausts > 0) {
	    	if(this.upgraded) {
	    		this.rawDescription = UPGRADED_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
	    	}else {
	    		this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
	    	}
	    }
    	initializeDescription();
	  }

	public AbstractCard makeCopy() {
		return new SpontaneousRitual();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		      this.rawDescription=UPGRADED_DESCRIPTION;
		      initializeDescription();
		}
	}
}
