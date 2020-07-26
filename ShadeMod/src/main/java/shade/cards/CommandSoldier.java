package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shade.ShadeMod;
import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;


public class CommandSoldier
  extends AbstractShadeCard
{
  public static final String ID = "Shade:CommandSoldier";
  public static final String NAME;
  public static final String DESCRIPTION;
  public static String UPGRADED_DESCRIPTION;
  public static final String IMG_PATH = "cards/command_soldier.png";
  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
  

  private static final int COST = 1;

  private static final CardStrings cardStrings;
  
	static {
		cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		NAME = cardStrings.NAME;
		DESCRIPTION = cardStrings.DESCRIPTION;
		UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	}

  
	public CommandSoldier() {
		super(ID, NAME, ShadeMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,
				AbstractCardEnum.SHADE, RARITY, TARGET);
		
		this.baseMagicNumber=2;
		this.magicNumber=this.baseMagicNumber;
	}

  public void use(AbstractPlayer p, AbstractMonster m) {
	  Skeleton skele = new shade.orbs.Skeleton();
      AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(skele));
      Skeleton s;
      
      int nHits=1;
      
      if( AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead){
    	  s=(Skeleton) AbstractDungeon.player.orbs.get(ShadeCharacter.INDEX_SKELETON);
    	  nHits = Math.min(s.count+1, this.magicNumber);
      }else {
    	  s=skele;
      }
      
      s.applyFocus();
      
	  for(int i=0;i<nHits;i++){
      	AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,(s.passiveAmount+s.passiveBonus), AbstractGameAction.AttackEffect.BLUNT_LIGHT,s,false,false,0,true,0));
	  }
  }

  
  public AbstractCard makeCopy() { return new CommandSoldier(); }

  
  public void upgrade() {
    if (!this.upgraded)
    	upgradeName(); 
    	upgradeMagicNumber(2);
  }
}
