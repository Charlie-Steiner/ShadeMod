package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;

@SpirePatch(
		clz=StrengthPower.class,
		method="updateDescription",
		paramtypez={}
	)
public class SkeletonStr
{
	@SpirePrefixPatch
	public static void Prefix(StrengthPower __instance)
	{
	    for (AbstractOrb o : AbstractDungeon.player.orbs) {
	    	o.applyFocus();
	    	o.updateDescription();
	    }
	}
}