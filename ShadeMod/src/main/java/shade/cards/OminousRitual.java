package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;

public class OminousRitual extends AbstractShadeCard {
	public static final String ID = "Shade:OminousRitual";
	public static final String NAME;
	public static final String DESCRIPTION;
	public static final String IMG_PATH = "cards/default_attack.png";
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
	public static String UPGRADED_DESCRIPTION;

	private static final int COST = 2;
	
	private static final CardStrings cardStrings;
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

	public OminousRitual() {
		super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE,
				RARITY, TARGET);

		this.baseDamage = 0;
		this.damage = this.baseDamage;
		this.baseMagicNumber = 0;
		this.magicNumber = this.baseMagicNumber;

		this.isMultiDamage = true;
		
		if (CardCrawlGame.dungeon != null) {
			setM();
		}

	}

	public void setM() {
		this.magicNumber=ShadeMod.combatExhausts;
		this.damage = this.magicNumber;
	}

	public void triggerOnCardPlayed(AbstractCard c) {
		setM();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
	    CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
	    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));		
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new OminousRitual();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(1);
		}
	}
}
