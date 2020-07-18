package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import shade.ShadeMod;
import shade.actions.FingerOfDeathAction;
import shade.actions.OneIsEnoughAction;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.patches.AbstractCardEnum;

public class OneIsEnough extends AbstractShadeCard{
	
	  public static final String ID = "Shade:OneIsEnough";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/FingerOfDeath.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
	  

	  private static final int COST = -1;

	  private static final CardStrings cardStrings;
	  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}
	
	public OneIsEnough()
	{
		super(ID,NAME,ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		this.baseDamage = 11;
		this.damage = this.baseDamage;
		this.exhaust = true;
	}
	
	public AbstractCard makeCopy()
	{
		return new OneIsEnough();
	}
	@Override
	public void upgrade() {
	    if (!this.upgraded)
	    {
		      upgradeName();
		      this.rawDescription=UPGRADED_DESCRIPTION;
		      initializeDescription();
	    }
		
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if(p.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof EmptyOrbSlot
				&& p.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof EmptyOrbSlot
				&& p.orbs.get(ShadeCharacter.INDEX_WRAITH) instanceof EmptyOrbSlot) {
			AbstractDungeon.actionManager.addToBottom(new OneIsEnoughAction(this.energyOnUse, this.upgraded, p, m, this.damage,this.damageTypeForTurn, this.freeToPlayOnce));
		}
		else
		{
			AbstractDungeon.player.energy.use(this.energyOnUse);
		}
		
	}

}
