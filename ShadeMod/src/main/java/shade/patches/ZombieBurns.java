package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import shade.characters.ShadeCharacter;

@SpirePatch(clz= Burn.class,method="use",
        paramtypez = {AbstractPlayer.class, AbstractMonster.class})
public class ZombieBurns {

	@SpireInsertPatch(rloc=1,
        localvars= {"dontTriggerOnUseCard", "magicNumber"})
    public static SpireReturn<Void> Insert(Burn __instance, AbstractPlayer p, AbstractMonster m, boolean dontTriggerOnUseCard, int magicNumber) {

        if (AbstractDungeon.player instanceof ShadeCharacter) {
            if (dontTriggerOnUseCard) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.getRandomMonster(), magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();

        }

    }
    

    }

