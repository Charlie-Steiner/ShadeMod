package shade.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import shade.patches.AbstractCardEnum;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class CoffinDance extends AbstractShadeCard{

	
	
    public static final String ID = "Shade:CoffinDance";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/CoffinDance.png";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    
    private static final CardStrings cardStrings;
    
  	static {
  		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  		NAME = cardStrings.NAME;
  		DESCRIPTION = cardStrings.DESCRIPTION;
  		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  		EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
  	}

    
    public CoffinDance() {
        super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);

        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

		
		int undeads=0;
		if(p.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead) {
			undeads += ((SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_SKELETON)).count;
		}
		if(p.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof SpawnedUndead) {
			undeads += ((SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_ZOMBIE)).count;
		}
		
		if(undeads>=4 && undeads%4==0) {
		    AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie(),this.magicNumber));
		}
	}
	
    public AbstractCard makeCopy() {
        return new CoffinDance();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
