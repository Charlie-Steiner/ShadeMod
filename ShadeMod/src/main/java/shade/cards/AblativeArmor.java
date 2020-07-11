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
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import shade.patches.AbstractCardEnum;
import shade.actions.RefreshUndeadPower;
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class AblativeArmor extends AbstractShadeCard{

	
	
    public static final String ID = "Shade:AblativeArmor";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/ablative_armor.png";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
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
        
        this.baseMagicNumber = 2;
        this.magicNumber=this.baseMagicNumber;
        this.baseDamage=6;
        this.isMultiDamage= true;
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		SpawnedUndead u = (SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_ZOMBIE);

		if (u.count > 0) {
		    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
		    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
		    } 
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
			
			u.remove(1);
		}
	      AbstractDungeon.actionManager.addToBottom(new RefreshUndeadPower());
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
            upgradeMagicNumber(1);
        }
    }
}
