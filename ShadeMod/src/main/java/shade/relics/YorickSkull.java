package shade.relics;

import basemod.abstracts.CustomRelic;
import shade.actions.UndeadSpawnAction;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Logger;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class YorickSkull extends CustomRelic {
    public static final String ID = "Shade:YorickSkull";
    public static final String IMG_PATH = "relics/YorickSkull.png";
    public static final String OUTLINE_IMG_PATH = "relics/YorickSkullOutline.png";
    private static final int COUNT = 10;
    private boolean start=true;

    public YorickSkull() {
    	        super(ID, new Texture(shade.ShadeMod.getResourcePath(IMG_PATH)), new Texture(shade.ShadeMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.MAGICAL);
    	        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.counter++;
        
        if (this.counter == 10) {
          this.counter = 0;
          flash();
          this.pulse = false;
          AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
          AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
        } else if (this.counter == 9) {
          beginPulse();
          this.pulse = true;
        }
    }
    
    
    public void atBattleStart() {
        if (this.counter == 9) {
          beginPulse();
          this.pulse = true;
        }
        this.start=true;
    }
    
    public void atTurnStartPostDraw() {
        if (this.start) {
        	this.start=false;
        	  AbstractDungeon.actionManager.addToBottom(new UndeadSpawnAction(new shade.orbs.Zombie()));
        }
      }

    @Override
    public AbstractRelic makeCopy() {
        return new YorickSkull();
    }

}