package shade.cards;



import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.patches.AbstractCardEnum;
import shade.actions.ReturnExhaustedToDeckAction;

public class ClawBack extends AbstractShadeCard{

	
	
    public static final String ID = "Shade:ClawBack";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/ClawBack.png";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 8;
    private static final int UPGRADE_BONUS = 3;

    
    private static final CardStrings cardStrings;
    
  	static {
  		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  		NAME = cardStrings.NAME;
  		DESCRIPTION = cardStrings.DESCRIPTION;
  		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  	}

    
    public ClawBack() {
        super(ID, NAME, shade.ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);

        this.baseDamage = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ReturnExhaustedToDeckAction(true));
    }


    public AbstractCard makeCopy() {
        return new ClawBack();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }
}
