package shade.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import shade.patches.AbstractCardEnum;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class AblativeArmor extends AbstractShadeCard{

	
	
    public static final String ID = "Shade:AblativeArmor";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/default_attack.png";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;

    private static final int COST = 1;
    
    private static final CardStrings cardStrings;
    
  	static {
  		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  		NAME = cardStrings.NAME;
  		DESCRIPTION = cardStrings.DESCRIPTION;
  		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  		EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
  	}

    
    public AblativeArmor() {
        super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);

        this.block = 8;
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		SpawnedUndead u = (SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_ZOMBIE);

		if (u.count > 0) {
			AbstractDungeon.actionManager.addToBottom(
					new GainBlockAction(p,p,this.block));
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
					this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

			AbstractDungeon.actionManager
			.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
			
			if(u.count==1) {
				AbstractDungeon.player.orbs.set(ShadeCharacter.INDEX_SKELETON, new EmptyOrbSlot());
			}else {
				u.count--;
			}
		}
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		if (p.orbs.get(ShadeCharacter.INDEX_ZOMBIE) instanceof EmptyOrbSlot) {
			canUse = false;
			this.cantUseMessage = EXTENDED_DESCRIPTION[0];
		}

		return canUse;
	}

    public AbstractCard makeCopy() {
        return new AblativeArmor();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}