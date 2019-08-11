package shade.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractShadeCard
  extends CustomCard
{
  public int selfDamage;
  public boolean upgradeSelfDamage;
  public boolean isSelfDamageModified;
  public int poison;
  public boolean upgradePoison;
  
  public AbstractShadeCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) { super(id, name, img, cost, rawDescription, type, color, rarity, target); }
  
  
  public boolean isPoisonModified;
  public int slimed;
  public int baseSlimed;
  public boolean isSlimedModified;
  public boolean upgradeSlimed;
  

}
