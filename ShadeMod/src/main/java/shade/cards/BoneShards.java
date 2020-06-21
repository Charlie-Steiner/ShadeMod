package shade.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import shade.characters.ShadeCharacter;
import shade.orbs.SpawnedUndead;

public class BoneShards extends AbstractShadeCard{

	
	
    public static final String ID = "Shade:BoneShards";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/default_attack.png";
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

    
    public BoneShards() {
        super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);

        this.baseDamage = 9;
        this.baseMagicNumber = 2;
        this.magicNumber=this.baseMagicNumber;
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		if(p.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead) {
			SpawnedUndead u = (SpawnedUndead) p.orbs.get(ShadeCharacter.INDEX_SKELETON);
			for(int i=0;i<Math.min(u.count, this.magicNumber);i++) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
			}
			u.remove(this.magicNumber);
		}
		
	}
	
    public AbstractCard makeCopy() {
        return new BoneShards();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
        }
    }
}
