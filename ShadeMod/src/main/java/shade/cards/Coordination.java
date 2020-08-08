package shade.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import shade.actions.RefreshUndeadPower;
import shade.actions.UndeadAutoAttack;
import shade.actions.UndeadSpawnAction;
import shade.characters.ShadeCharacter;
import shade.orbs.Skeleton;
import shade.orbs.SpawnedUndead;
import shade.patches.AbstractCardEnum;
import shade.powers.CoordinationPower;
import shade.powers.LichFormPower;
import shade.powers.SoulPiercePower;

public class Coordination extends AbstractShadeCard {

	  public static final String ID = "Shade:Coordination";
	  public static final String NAME;
	  public static final String DESCRIPTION;
	  public static String UPGRADED_DESCRIPTION;
	  public static final String IMG_PATH = "cards/Coordination.png";
	  private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
	  private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
	  private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

	  
	  private static final CardStrings cardStrings;
	  private static final int COST = 1;
	
		static {
			cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			NAME = cardStrings.NAME;
			DESCRIPTION = cardStrings.DESCRIPTION;
			UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
		}
	  
	public Coordination() {
		super(ID,NAME,shade.ShadeMod.getResourcePath(IMG_PATH),COST,DESCRIPTION,TYPE,AbstractCardEnum.SHADE,RARITY,TARGET);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void upgrade() {
	    if (!this.upgraded) {
	      upgradeName();
	      this.rawDescription=UPGRADED_DESCRIPTION;
	      initializeDescription();
	    } 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CoordinationPower(p)));
		

	      
	      if(this.upgraded && p instanceof ShadeCharacter) {
	    	  
		      int nHits=0;
		      Skeleton s;
		      if( ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON) instanceof SpawnedUndead){
		    	  s=(Skeleton) ((ShadeCharacter)p).undeadGroup.undeads.get(ShadeCharacter.INDEX_SKELETON);
		    	  nHits=Math.min(s.count,2);
		    	  
		    	  for(int i=0;i<nHits;i++){
			          	AbstractDungeon.actionManager.addToBottom(new UndeadAutoAttack(AbstractDungeon.player,(s.passiveAmount+s.passiveBonus), AbstractGameAction.AttackEffect.BLUNT_LIGHT,s,false,false,0,true,0));
			      }
		      }
		      
		  }
	}

}
