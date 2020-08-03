package shade.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractShadeCard
  extends CustomCard
{
  
  public AbstractShadeCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) { super(id, name, img, cost, rawDescription, type, color, rarity, target); }
  
  public void triggerOnAnyExhaust() {};
  

}
