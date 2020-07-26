package shade.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import shade.characters.ShadeCharacter;

@SpirePatch(clz= SpireShield.class,method="takeTurn",
        paramtypez = {})
public class SpireShieldAI {


    public static SpireReturn<Void> Prefix() {

        if (AbstractDungeon.player instanceof ShadeCharacter) {
        	AbstractMonster me = AbstractDungeon.getMonsters().getMonster("SpireShield");
            switch (me.nextMove) {
            case 1:
              AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(me, "ATTACK"));
              AbstractDungeon.actionManager.addToBottom(new WaitAction(0.35F));
              AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)me.damage
                    .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
              
              AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, me, new StrengthPower(AbstractDungeon.player, -1), -1));
              break;


            case 2:
              for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, me, 30));
              }
              break;
            case 3:
              AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(me, "OLD_ATTACK"));
              AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
              AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)me.damage
                    .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
              
              if (AbstractDungeon.ascensionLevel >= 18) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(me, me, 99)); break;
              } 
              AbstractDungeon.actionManager.addToBottom(new GainBlockAction(me, me, ((DamageInfo)me.damage.get(1)).output));
              break;
          } 
          
          AbstractDungeon.actionManager.addToBottom(new RollMoveAction(me));
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();

        }

    }
    

    }

