package shade.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import basemod.abstracts.CustomRelic;
import shade.characters.ShadeCharacter;

public class Thing  extends CustomRelic{
    public static final String ID = "Shade:Thing";
    public static final String IMG_PATH = "relics/Thing.png";
    public static final String OUTLINE_IMG_PATH = "relics/Thing.png";
	
	public Thing() {
        super(ID, new Texture(shade.ShadeMod.getResourcePath(IMG_PATH)), new Texture(shade.ShadeMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.MAGICAL);
	}

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Thing();
    }
	
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof ShadeCharacter;
    }
    
    public void onExhume() {
    	flash();
    	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,3,true));
    }
    
}
