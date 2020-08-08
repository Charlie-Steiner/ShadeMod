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
import shade.actions.OneIsEnoughAction;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.patches.AbstractCardEnum;
import shade.orbs.SpawnedUndead;

public class OneIsEnough extends AbstractShadeCard{
	
	  public static final String ID = "Shade:OneIsEnough";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/OneIsEnough.png";
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
		this.baseDamage = 10;
		this.damage = this.baseDamage;
		
		this.baseBlock=5;
		this.block=this.baseBlock;
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
		      upgradeBlock(1);
		      upgradeDamage(3);
	    }
		
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int count=0;
		
		if(p instanceof ShadeCharacter) {
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON) instanceof EmptyOrbSlot)) {
				count += ( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON)).count;
			}
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_ZOMBIE) instanceof EmptyOrbSlot)) {
				count += ( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_ZOMBIE)).count;
			}
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH) instanceof EmptyOrbSlot)) {
				count += ( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH)).count;
			}
				
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON) instanceof EmptyOrbSlot)) {
				( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON)).remove(count);
			}
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_ZOMBIE) instanceof EmptyOrbSlot)) {
				( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_ZOMBIE)).remove(count);
			}
			if(!(((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH) instanceof EmptyOrbSlot)) {
				( (SpawnedUndead) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_WRAITH)).remove(count);
			}
		}

		AbstractDungeon.actionManager.addToBottom(new OneIsEnoughAction(this.energyOnUse, p, m, this.damage,this.damageTypeForTurn, this.freeToPlayOnce, this.block, count));

	}

}
