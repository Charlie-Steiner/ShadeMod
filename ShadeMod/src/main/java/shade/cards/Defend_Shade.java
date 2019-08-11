package shade.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.patches.AbstractCardEnum;

public class Defend_Shade extends AbstractShadeCard {
  public static final String ID = "Shade:Defend_Shade";
  public static final String NAME = "Defend";
  public static final String DESCRIPTION = "Gain !B! Block.";
  public static final String IMG_PATH = "cards/defendSlime.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  
  private static final int COST = 1;
  private static final int BLOCK = 5;
  private static final int UPGRADE_BONUS = 3;
  
  public Defend_Shade() {
    super("Shade:Defend_Shade", "Defend", ShadeMod.getResourcePath("cards/defendSlime.png"), 1, "Gain !B! Block.", TYPE, AbstractCardEnum.SHADE, RARITY, TARGET);

    
    this.baseBlock = 5;
    this.tags.add(BaseModCardTags.BASIC_DEFEND);
  }

  
  public void use(AbstractPlayer p, AbstractMonster m) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block)); }


  
  public AbstractCard makeCopy() { return new Defend_Shade(); }

  
  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeBlock(3);
    } 
  }
}
