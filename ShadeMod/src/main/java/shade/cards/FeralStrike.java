package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.ShadeMod;
import shade.actions.FingerOfDeathAction;
import shade.actions.UndeadSpawnAction;
import shade.patches.AbstractCardEnum;

public class FeralStrike extends AbstractShadeCard{
	
	  public static final String ID = "Shade:FeralStrike";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/feral_strike.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
	  

	  private static final int COST = 1;

	  private static final CardStrings cardStrings;
	  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
	
	public FeralStrike()
	{
		super(ID,NAME,ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		this.baseDamage = 10;
		this.damage = this.baseDamage;
		
		this.exhaust=true;
		this.isMultiDamage=true;
		
		this.tags.add(AbstractCard.CardTags.STRIKE);
	}
	
	public AbstractCard makeCopy()
	{
		return new FeralStrike();
	}
	@Override
	public void upgrade() {
	    if (!this.upgraded)
	    {
		      upgradeName(); 
		      upgradeDamage(3);
	    }
		
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wound(), 1));

	}

}
