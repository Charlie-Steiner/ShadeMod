package shade;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shade.characters.ShadeCharacter;
import shade.orbs.EmptySlot;
import shade.cards.*;
import shade.relics.*;
import shade.ui.ShadeTipTracker;
import shade.patches.*;
import shade.powers.MinionsPower;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class ShadeMod implements PostInitializeSubscriber, 
		EditCharactersSubscriber, EditRelicsSubscriber, 
		EditCardsSubscriber, EditKeywordsSubscriber, EditStringsSubscriber, 
		OnStartBattleSubscriber, PostPowerApplySubscriber, PostExhaustSubscriber, PostBattleSubscriber{

	private static final Color SHADE_COLOR = com.megacrit.cardcrawl.helpers.CardHelper
			.getColor(25.0F, 95.0F, 25.0F);

	public static final Logger logger = LogManager.getLogger(ShadeMod.class.getName());

	public static ShadeCharacter shadeCharacter;
	public static ShadeMod shademod;

	private static final String ATTACK_CARD = "512/bg_attack_slimebound.png";
	private static final String SKILL_CARD = "512/bg_skill_slimebound.png";
	private static final String POWER_CARD = "512/bg_power_slimebound.png";
	private static final String ENERGY_ORB = "512/card_slimebound_orb.png";
	private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

	private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_slimebound.png";
	private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_slimebound.png";
	private static final String POWER_CARD_PORTRAIT = "1024/bg_power_slimebound.png";
	private static final String ENERGY_ORB_PORTRAIT = "1024/card_slimebound_orb.png";

	private static final String CHAR_BUTTON = "charSelect/button.png";
	private static final String CHAR_PORTRAIT = "charSelect/portrait.png";
	
	public static int combatExhausts = 0;

	public ShadeMod() {
		BaseMod.subscribe(this);
		BaseMod.addColor(AbstractCardEnum.SHADE, SHADE_COLOR, SHADE_COLOR, SHADE_COLOR, SHADE_COLOR, SHADE_COLOR,
				SHADE_COLOR, SHADE_COLOR, getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD),
				getResourcePath(POWER_CARD), getResourcePath(ENERGY_ORB), getResourcePath(ATTACK_CARD_PORTRAIT),
				getResourcePath(SKILL_CARD_PORTRAIT), getResourcePath(POWER_CARD_PORTRAIT),
				getResourcePath(ENERGY_ORB_PORTRAIT), getResourcePath(CARD_ENERGY_ORB));
		
		

	    logger.info("Adding mod settings");
	    ShadeTipTracker.initialize();
	    logger.info("Done adding mod settings");
	}
	
    @SuppressWarnings("unused")
	public static void initialize() {
		shademod = new ShadeMod();
	}

	public static final String getResourcePath(String resource) {
		return "ShadeImages/" + resource;
	}
	
    @Override
	public void receiveEditCharacters() {
		shadeCharacter = new ShadeCharacter("the Shade", ShadeEnum.SHADE);
		BaseMod.addCharacter(shadeCharacter, getResourcePath("charSelect/button.png"),
				getResourcePath("charSelect/portrait.png"), ShadeEnum.SHADE);
		logger.info("Added Shade Character");
	}

    @Override
	public void receiveEditCards() {

    	//BATCH ONE:
		// starter cards
		BaseMod.addCard(new shade.cards.Defend_Shade());
		BaseMod.addCard(new shade.cards.Strike_Shade());
		BaseMod.addCard(new shade.cards.AnimateDead());
		BaseMod.addCard(new shade.cards.ClawBack());

		// common cards
		BaseMod.addCard(new shade.cards.FreshBones());
		BaseMod.addCard(new shade.cards.WallOfFlesh());
		BaseMod.addCard(new shade.cards.Frenzy());
		BaseMod.addCard(new shade.cards.ExtraLimbs());
		BaseMod.addCard(new shade.cards.GreyBargain());
		BaseMod.addCard(new shade.cards.AncientSpear());
		BaseMod.addCard(new shade.cards.FuneraryArmor());
		BaseMod.addCard(new shade.cards.FingerOfDeath());
		BaseMod.addCard(new shade.cards.Bonestorm());
		BaseMod.addCard(new shade.cards.CommandSoldier());
		BaseMod.addCard(new shade.cards.SkeletonArmy());

		// uncommon cards
		//BaseMod.addCard(new shade.cards.Boneskin());
		BaseMod.addCard(new shade.cards.TouchOfTheGrave());
		BaseMod.addCard(new shade.cards.CallOfTheGrave());
		BaseMod.addCard(new shade.cards.Bite_Shade());
		BaseMod.addCard(new shade.cards.RitualOffering());
		BaseMod.addCard(new shade.cards.FleshLikeOak());
		BaseMod.addCard(new shade.cards.CloudOfShrapnel());
		BaseMod.addCard(new shade.cards.OminousRitual());
		BaseMod.addCard(new shade.cards.FuneraryJewels());
		BaseMod.addCard(new shade.cards.SanguineRitual());
		BaseMod.addCard(new shade.cards.RavenousHorde());
		BaseMod.addCard(new shade.cards.AblativeArmor());
		BaseMod.addCard(new shade.cards.CallWraith());

		// rare cards
		BaseMod.addCard(new shade.cards.SelfBurial());
		BaseMod.addCard(new shade.cards.DangerousRitual());
		BaseMod.addCard(new shade.cards.LichForm());
		
		//unlock cards:
		//unlock starter cards
		UnlockTracker.unlockCard(Defend_Shade.ID);
		UnlockTracker.unlockCard(Strike_Shade.ID);
		UnlockTracker.unlockCard(AnimateDead.ID);
		UnlockTracker.unlockCard(ClawBack.ID);

		//unlock common cards

		UnlockTracker.unlockCard(FreshBones.ID);
		UnlockTracker.unlockCard(WallOfFlesh.ID);
		UnlockTracker.unlockCard(Frenzy.ID);
		UnlockTracker.unlockCard(ExtraLimbs.ID);
		UnlockTracker.unlockCard(GreyBargain.ID);
		UnlockTracker.unlockCard(AncientSpear.ID);
		UnlockTracker.unlockCard(FuneraryArmor.ID);
		UnlockTracker.unlockCard(FingerOfDeath.ID);
		UnlockTracker.unlockCard(Bonestorm.ID);
		UnlockTracker.unlockCard(CommandSoldier.ID);
		UnlockTracker.unlockCard(SkeletonArmy.ID);

		//unlock uncommon cards
		//UnlockTracker.unlockCard(Boneskin.ID);
		UnlockTracker.unlockCard(TouchOfTheGrave.ID);
		UnlockTracker.unlockCard(CallOfTheGrave.ID);
		UnlockTracker.unlockCard(Bite_Shade.ID);
		UnlockTracker.unlockCard(RitualOffering.ID);
		UnlockTracker.unlockCard(FleshLikeOak.ID);
		UnlockTracker.unlockCard(CloudOfShrapnel.ID);
		UnlockTracker.unlockCard(OminousRitual.ID);
		UnlockTracker.unlockCard(FuneraryJewels.ID);
		UnlockTracker.unlockCard(SanguineRitual.ID);
		UnlockTracker.unlockCard(RavenousHorde.ID);
		UnlockTracker.unlockCard(AblativeArmor.ID);
		UnlockTracker.unlockCard(CallWraith.ID);

		//unlock rare cards
		UnlockTracker.unlockCard(SelfBurial.ID);
		UnlockTracker.unlockCard(DangerousRitual.ID);
		UnlockTracker.unlockCard(LichForm.ID);
		
		//BATCH TWO:
		//common cards
		BaseMod.addCard(new shade.cards.LyeBath());
		BaseMod.addCard(new shade.cards.GraveStillness());
		BaseMod.addCard(new shade.cards.Trample());
		BaseMod.addCard(new shade.cards.CoffinDance());
		BaseMod.addCard(new shade.cards.BoneShards());
		BaseMod.addCard(new shade.cards.SkeletonHug());
		BaseMod.addCard(new shade.cards.Premonition());
		BaseMod.addCard(new shade.cards.Brains());
		BaseMod.addCard(new shade.cards.FleshToFlesh());
		BaseMod.addCard(new shade.cards.FeralStrike());
		
		//uncommon cards
		BaseMod.addCard(new shade.cards.GoDigging());
		BaseMod.addCard(new shade.cards.AuraOfUnlife());
		BaseMod.addCard(new shade.cards.PotionOfRestlessness());
		BaseMod.addCard(new shade.cards.Awaken());
		BaseMod.addCard(new shade.cards.Patience());
		BaseMod.addCard(new shade.cards.PressTheAttack());
		BaseMod.addCard(new shade.cards.SensePrey());
		BaseMod.addCard(new shade.cards.OpenGraves());
		BaseMod.addCard(new shade.cards.Haruspicy());
		BaseMod.addCard(new shade.cards.Carnophage());
		BaseMod.addCard(new shade.cards.Excise());
		BaseMod.addCard(new shade.cards.StitchTogether());
		BaseMod.addCard(new shade.cards.FleshOffering());
		BaseMod.addCard(new shade.cards.ScuttlingHand());
		
		//rare cards
		BaseMod.addCard(new shade.cards.GreaterAnimation());
		BaseMod.addCard(new shade.cards.OfferingToTheBeyond());
		BaseMod.addCard(new shade.cards.SoulPierce());
		BaseMod.addCard(new shade.cards.UnbarTheGrave());
		BaseMod.addCard(new shade.cards.LoseControl());
		BaseMod.addCard(new shade.cards.DarkOfNight());
		BaseMod.addCard(new shade.cards.Tombstone());
		
		//unlock cards:
		//common cards
		UnlockTracker.unlockCard(LyeBath.ID);
		UnlockTracker.unlockCard(GraveStillness.ID);
		UnlockTracker.unlockCard(Trample.ID);
		UnlockTracker.unlockCard(CoffinDance.ID);
		UnlockTracker.unlockCard(BoneShards.ID);
		UnlockTracker.unlockCard(SkeletonHug.ID);
		UnlockTracker.unlockCard(Premonition.ID);
		UnlockTracker.unlockCard(Brains.ID);
		UnlockTracker.unlockCard(FleshToFlesh.ID);
		UnlockTracker.unlockCard(FeralStrike.ID);
		
		//uncommon cards
		UnlockTracker.unlockCard(GoDigging.ID);
		UnlockTracker.unlockCard(AuraOfUnlife.ID);
		UnlockTracker.unlockCard(PotionOfRestlessness.ID);
		UnlockTracker.unlockCard(Awaken.ID);
		UnlockTracker.unlockCard(Patience.ID);
		UnlockTracker.unlockCard(PressTheAttack.ID);
		UnlockTracker.unlockCard(SensePrey.ID);
		UnlockTracker.unlockCard(OpenGraves.ID);
		UnlockTracker.unlockCard(Haruspicy.ID);
		UnlockTracker.unlockCard(Carnophage.ID);
		UnlockTracker.unlockCard(Excise.ID);
		UnlockTracker.unlockCard(StitchTogether.ID);
		UnlockTracker.unlockCard(FleshOffering.ID);
		UnlockTracker.unlockCard(ScuttlingHand.ID);
		
		//rare cards
		UnlockTracker.unlockCard(GreaterAnimation.ID);
		UnlockTracker.unlockCard(OfferingToTheBeyond.ID);
		UnlockTracker.unlockCard(SoulPierce.ID);
		UnlockTracker.unlockCard(UnbarTheGrave.ID);
		UnlockTracker.unlockCard(LoseControl.ID);
		UnlockTracker.unlockCard(DarkOfNight.ID);
		UnlockTracker.unlockCard(Tombstone.ID);
		
		//BATCH THREE:
		BaseMod.addCard(new shade.cards.Putrefaction());
		BaseMod.addCard(new shade.cards.PoundOfFlesh());
		BaseMod.addCard(new shade.cards.Doomsay());
		BaseMod.addCard(new shade.cards.Consume());
		BaseMod.addCard(new shade.cards.Coordination());
		BaseMod.addCard(new shade.cards.SoulToxin());
		BaseMod.addCard(new shade.cards.Asphyxiate());
		BaseMod.addCard(new shade.cards.SpontaneousRitual());
		BaseMod.addCard(new shade.cards.CarefulCut());
		BaseMod.addCard(new shade.cards.MadHubris());
		BaseMod.addCard(new shade.cards.OneIsEnough());
		BaseMod.addCard(new shade.cards.LastGasp());

		//unlock cards:
		UnlockTracker.unlockCard(shade.cards.Putrefaction.ID);
		UnlockTracker.unlockCard(shade.cards.PoundOfFlesh.ID);
		UnlockTracker.unlockCard(shade.cards.Doomsay.ID);
		UnlockTracker.unlockCard(shade.cards.Consume.ID);
		UnlockTracker.unlockCard(shade.cards.Coordination.ID);
		UnlockTracker.unlockCard(shade.cards.SoulToxin.ID);
		UnlockTracker.unlockCard(shade.cards.Asphyxiate.ID);
		UnlockTracker.unlockCard(shade.cards.SpontaneousRitual.ID);
		UnlockTracker.unlockCard(shade.cards.CarefulCut.ID);
		UnlockTracker.unlockCard(shade.cards.MadHubris.ID);
		UnlockTracker.unlockCard(shade.cards.OneIsEnough.ID);
		UnlockTracker.unlockCard(shade.cards.LastGasp.ID);
		
		logger.info("Done adding Shade cards!");
	}

    @Override
	public void receiveEditRelics() {
		BaseMod.addRelicToCustomPool(new YorickSkull(), AbstractCardEnum.SHADE);
		BaseMod.addRelicToCustomPool(new YorickPolishedSkull(), AbstractCardEnum.SHADE);
		BaseMod.addRelicToCustomPool(new Thing(), AbstractCardEnum.SHADE);
	}


    @Override
	public void receivePostInitialize() {

		logger.info("Load Badge Image and mod options");
		// Load the Mod Badge
		Texture badgeTexture = new Texture(getResourcePath("badge.png"));

		// Create the Mod Menu
		ModPanel settingsPanel = new ModPanel();
		settingsPanel.addUIElement(
				new ModLabel("Shade Mod doesn't have any settings!", 400.0f, 700.0f, settingsPanel, (me) -> {}));
		
		BaseMod.registerModBadge(badgeTexture, "Shade", "Jove", "Adds the Shade character to the game. Art credit: CausticImp", settingsPanel);

		logger.info("Done loading badge Image and mod options");

	}
    
    @Override
	public void receiveEditStrings() {

		logger.info("begin editing Shade strings");
		String relicStrings = Gdx.files.internal("localization/Shade-RelicStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		String cardStrings = Gdx.files.internal("localization/Shade-CardStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		String powerStrings = Gdx.files.internal("localization/Shade-PowerStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
		String orbStrings = Gdx.files.internal("localization/Shade-OrbStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
		String uiStrings = Gdx.files.internal("localization/Shade-UIStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
		String tutorialStrings=Gdx.files.internal("localization/Shade-TutorialStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
		
/*		
		String monsterStrings = Gdx.files.internal("localization/Shade-MonsterStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
		String potionStrings = Gdx.files.internal("localization/Shade-PotionStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
		String eventStrings = Gdx.files.internal("localization/Shade-EventStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
		String modStrings = Gdx.files.internal("localization/Shade-DailyModStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RunModStrings.class, modStrings);
*/
		logger.info("done editing strings");

	}
    
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        
        logger.info("Adding keywords:");
        String keywordStrings=Gdx.files.internal("localization/Shade-KeywordStrings.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(keywordStrings, Keyword[].class);

        if (keywords != null) {
          for (Keyword keyword : keywords) {
            BaseMod.addKeyword("shade", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            logger.info(keyword.PROPER_NAME);
          }
        }
     }

    @Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		if (AbstractDungeon.player.chosenClass == ShadeEnum.SHADE) {
			if(target == AbstractDungeon.player)
			{
				for(int i = 0; i <= ShadeCharacter.INDEX_MAX; i++)
				{
					AbstractOrb u = AbstractDungeon.player.orbs.get(i);
					if (!(u instanceof EmptyOrbSlot))
					{
						u.applyFocus();
						u.updateDescription();
					}
				}
			}
		}
	}

    public int decayConstant = 3;
    
    @Override
	public void receiveOnBattleStart(AbstractRoom room) {
	    if (AbstractDungeon.player.chosenClass == ShadeEnum.SHADE) {
	    	AbstractDungeon.player.orbs.set(0, new EmptySlot());
	    	AbstractDungeon.player.orbs.set(1, new EmptySlot());
	    	AbstractDungeon.player.orbs.set(2, new EmptySlot());
	        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
	            ((AbstractOrb)AbstractDungeon.player.orbs.get(i)).setSlot(i, AbstractDungeon.player.maxOrbs);
	         }

	        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
					new MinionsPower(AbstractDungeon.player, decayConstant),decayConstant));
			logger.info(CardCrawlGame.languagePack.getPowerStrings("Shade:MinionsPower").NAME);

			
			combatExhausts = 0;
	    }
	}

	@Override
	public void receivePostExhaust(AbstractCard arg0) {
		if (AbstractDungeon.player.chosenClass == ShadeEnum.SHADE) {
			combatExhausts++;
		}
	}
	
	public void receivePostBattle(AbstractRoom arg0) {
		combatExhausts=0;
	}


}