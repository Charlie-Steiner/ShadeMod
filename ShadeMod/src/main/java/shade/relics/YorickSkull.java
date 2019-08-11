package shade.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Logger;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
    private static final int HP_PER_CARD = 1;

    public YorickSkull() {
    	        super(ID, new Texture(shade.ShadeMod.getResourcePath(IMG_PATH)), new Texture(shade.ShadeMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    public void atBattleStartPreDraw() {}

    @Override
    public AbstractRelic makeCopy() {
        return new YorickSkull();
    }

}