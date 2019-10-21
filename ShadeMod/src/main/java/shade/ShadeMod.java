package shade;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shade.cards.*;
import shade.characters.ShadeCharacter;
import shade.relics.*;
import shade.patches.*;
import shade.powers.MinionsPower;

import java.nio.charset.StandardCharsets;
import java.util.List;

@com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
public class ShadeMod implements AddCustomModeModsSubscriber, PostDungeonInitializeSubscriber, PostBattleSubscriber,
		PostInitializeSubscriber, PreMonsterTurnSubscriber, OnCardUseSubscriber,
		basemod.interfaces.EditCharactersSubscriber, basemod.interfaces.EditRelicsSubscriber,
		basemod.interfaces.EditCardsSubscriber, basemod.interfaces.EditKeywordsSubscriber, EditStringsSubscriber,
		basemod.interfaces.PostDrawSubscriber, basemod.interfaces.PostPowerApplySubscriber,
		basemod.interfaces.OnStartBattleSubscriber {

	private static final com.badlogic.gdx.graphics.Color SHADE_COLOR = com.megacrit.cardcrawl.helpers.CardHelper
			.getColor(25.0F, 95.0F, 25.0F);

	public static ShadeCharacter shadeCharacter;

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
	}

	public static void initialize() {
		new ShadeMod();
	}

	public static final String getResourcePath(String resource) {
		return "ShadeImages/" + resource;
	}

	public void receivePostExhaust() {
		combatExhausts++;
	}
	
	public void receiveEditCharacters() {

		shadeCharacter = new ShadeCharacter("TheShade", ShadeEnum.SHADE);
		BaseMod.addCharacter(shadeCharacter, getResourcePath("charSelect/button.png"),
				getResourcePath("charSelect/portrait.png"), ShadeEnum.SHADE);

	}

	public void receiveEditCards() {

		// starter cards
		BaseMod.addCard(new shade.cards.Defend_Shade());
		BaseMod.addCard(new shade.cards.Strike_Shade());
		BaseMod.addCard(new shade.cards.AnimateDead());
		BaseMod.addCard(new shade.cards.ClawBack());

		// common cards

		BaseMod.addCard(new shade.cards.Boneskin());
		BaseMod.addCard(new shade.cards.RainOfBones());
		BaseMod.addCard(new shade.cards.WallOfFlesh());
		BaseMod.addCard(new shade.cards.Frenzy());
		BaseMod.addCard(new shade.cards.ExtraLimbs());
		BaseMod.addCard(new shade.cards.GraveBargain());
		BaseMod.addCard(new shade.cards.BoneSpear());
		BaseMod.addCard(new shade.cards.FuneraryArmor());
		BaseMod.addCard(new shade.cards.FingerOfDeath());

		// uncommon cards
		BaseMod.addCard(new shade.cards.TouchOfTheGrave());
		BaseMod.addCard(new shade.cards.CallOfTheGrave());
		BaseMod.addCard(new shade.cards.Bonestorm());
		BaseMod.addCard(new shade.cards.Bite_Shade());
		BaseMod.addCard(new shade.cards.RitualOffering());
		BaseMod.addCard(new shade.cards.FleshLikeOak());
		BaseMod.addCard(new shade.cards.CloudOfShrapnel());
		BaseMod.addCard(new shade.cards.OminousRitual());
		BaseMod.addCard(new shade.cards.FuneraryJewels());
		BaseMod.addCard(new shade.cards.SanguineRitual());
		BaseMod.addCard(new shade.cards.RavenousHorde());

		// rare cards
		BaseMod.addCard(new shade.cards.CallWraith());
		BaseMod.addCard(new shade.cards.SelfBurial());
		BaseMod.addCard(new shade.cards.DangerousRitual());
		BaseMod.addCard(new shade.cards.LichForm());
	}

	public void receiveEditRelics() {

		BaseMod.addRelicToCustomPool(new YorickSkull(), AbstractCardEnum.SHADE);
	}

	public static final Logger logger = LogManager.getLogger(ShadeMod.class.getName());

	public void receivePostInitialize() {

		logger.info("Load Badge Image and mod options");
		// Load the Mod Badge
		Texture badgeTexture = new Texture(getResourcePath("badge.png"));

		// Create the Mod Menu
		ModPanel settingsPanel = new ModPanel();
		settingsPanel.addUIElement(
				new ModLabel("Shade Mod doesn't have any settings!", 400.0f, 700.0f, settingsPanel, (me) -> {
				}));
		BaseMod.registerModBadge(badgeTexture, "Shade", "Jove", "Adds the Shade character to the game.", settingsPanel);

		logger.info("Done loading badge Image and mod options");

	}

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

	public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {

		this.printEnemies();

		return true;
	}

	public void printEnemies() {
		for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
			logger.info(monster.name + " HP " + monster.currentHealth);
		}
	}

	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		
		if(target == AbstractDungeon.player)
		{
			for(int i = 0; i <= ShadeCharacter.INDEX_MAX; i++)
			{
			
				AbstractOrb u = AbstractDungeon.player.orbs.get(i);
				if (!(u instanceof EmptyOrbSlot))
				{
					u.updateDescription();
				}
			}
		}

	}

	public void receivePostDungeonInitialize() {

	}

	public void receiveOnBattleStart(AbstractRoom room) {
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new MinionsPower(AbstractDungeon.player)));
		logger.info(CardCrawlGame.languagePack.getPowerStrings("Shade:MinionsPower").NAME);
		logger.info(CardCrawlGame.languagePack.getPowerStrings("Slimebound:PotencyPower").NAME);
		logger.info("preMonsterTurn");
		logger.info("Common card #: " + AbstractDungeon.commonCardPool.group.size());
		for (AbstractCard card : AbstractDungeon.commonCardPool.group) {
			logger.info(card.name);
		}
		logger.info("Uncommon card #: " + AbstractDungeon.uncommonCardPool.group.size());
		logger.info("Rare card #: " + AbstractDungeon.rareCardPool.group.size());
		
		combatExhausts = 0;
	}

	public void receivePostDraw(AbstractCard c) {

	}

	public void receiveCustomModeMods(List<CustomMod> l) {

	}

	public void receiveCardUsed(AbstractCard c) {

	}

	public void receiveEditKeywords() {
		// TODO Auto-generated method stub

	}

	public void receivePostBattle(AbstractRoom arg0) {
		// TODO Auto-generated method stub

	}

}