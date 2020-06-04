package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;

@SpirePatch(
		clz=DexterityPower.class,
		method="updateDescription",
		paramtypez={}
	)
public class ZombieDex
{
	@SpirePrefixPatch
	public static void Prefix(DexterityPower __instance)
	{
	    for (AbstractOrb o : AbstractDungeon.player.orbs) {
	    	o.applyFocus();
	    }
	}
}