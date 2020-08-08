package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;

import shade.characters.ShadeCharacter;

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
		if(AbstractDungeon.player instanceof ShadeCharacter) {
		    for (AbstractOrb o : ((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads) {
		    	o.applyFocus();
		    	o.updateDescription();
		    }
		}
	}
}