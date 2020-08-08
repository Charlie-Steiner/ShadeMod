package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;

import shade.characters.ShadeCharacter;

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
		if(AbstractDungeon.player instanceof ShadeCharacter) {
		    for (AbstractOrb o : ((ShadeCharacter)AbstractDungeon.player).undeadGroup.undeads) {
		    	o.applyFocus();
		    	o.updateDescription();
		    }
		}
	}
}