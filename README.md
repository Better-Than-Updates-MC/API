# Fabric Beta Essentials

The ***modern*** essential mod development pack for **Minecraft beta 1.7.3.**

- API includes [Cursed Legacy API], and [CoreLib]
- Compatibility includes [Minecraft Forge] and [Forge Mod Loader]
- Essentials includes [Creative Mode], [ModMenu], [Roughly Enough Items]

*( See below for licensing information on these. )*

The goal for the module structure of this project (still up for discussion) currently looks like this:

- [libraries](./libraries):
  - Includes the entire [Cursed Legacy API] for compatibility.
  - [accessibility](./libraries/accessibility)
    - [ ] Locale menu and translation support.
    - [ ] Sound categories and extended volume settings.
    - [ ] Closed captions
  - [core](./libraries/core)
    - Includes [Cursed Legacy API] base.
  - [events](./libraries/events)
    - Includes [Cursed Legacy API] events.
  - [vanilla-fixes](./libraries/vanilla-fixes):
    - 
- [essentials](./essentials)
  - [continue-button](./essentials/continue-button):
    - Adds a "Continue" button to the title screen that loads the most recently played world.
  - [creative](./essentials/creative):
    - Re-enables creative mode, with a creative inventory reminiscent of modern Minecraft.
  - [modmenu](./essentials/modmenu):
    - Mod list showcasing mod metadata
    - Full support for all modern features
    - Robust API for adding custom metadata
  - [mod-updater](./essentials/mod-updater):
    - Allows mods to be automatically updated via custom metadata in `fabric.mod.json`.
  - [roughly-enough-items](./essentials/roughly-enough-items):
    - In-game item list
    - Recipes accessible from the aforementioned list
    - API for adding custom recipe types
- [compatibility](./compatibility)
  - [forge-api](./compatibility/forge-api)
    - [ ] Full implementation of Minecraft [Forge API] version 1.0.X as a compatibility layer
  - [forge-mod-loader](./compatibility/forge-mod-loader)
    - [ ] Full implementation of [Forge Mod Loader] (Risugami's/ModloaderMP) as a compatibility layer

For more information, or for help in using the APIs,
see the [wiki](https://github.com/Better-Than-Updates-MC/API/wiki).

## Setup
Import the `build.gradle` file as a project in Intellij IDEA. (Or Eclipse, if supported. Not recommended.)

NOTE: if you want sources (recommended), click the Gradle task `Tasks -> fabric -> genSources`,
or run `gradlew genSources` via commandline.

To be able to run the API as a whole in an API dev environment, ensure the root project has been set up in your ide. This should be automatic, but it pays to make sure.

If you wish to build a copy of FBE, you can either click the Gradle task `Tasks -> build -> build`,
or run `./gradlew build` via commandline.

## Contributing

Make sure to follow the code style guidelines, which can be seen in use in existing files.

CheckStyle can help! Run `gradlew :checkstyleMain` when you're finished with your initial draft,
or install the CheckStyle IDEA plugin and configure it for use with [checkstyle.xml](./config/checkstyle/checkstyle.xml)

## Running Tests

If your IDE runs using Gradle, change settings to make your IDE compile it itself, so you can run the test modules.
If your IDE doesn't have this feature get a better IDE not a glorified text editor please. kthx

## Versioning

Subprojects should receive their own [semver](https://semver.org)-compatible versions
The main project is versioned based on refactors that affect any or all of the subprojects. (This is subject to change.)
Do you have a suggestion for better versioning? [Create an issue](https://github.com/Better-Than-Updates-MC/API/issues)
here on GitHub, or bring it up on [Discord](https://halotroop.com/Discord.html).

## License

[Unless otherwise stated](./beta-forge-hooks/LICENSE), these APIs are available under the [MIT] license.
Feel free to learn from them and incorporate them in your own projects.

Fabric Beta Essentials is based ***heavily*** on [Cursed Legacy API]
which shares the [MIT] license of FBE.

FBE also "borrows" code from [Station API] which is licensed under Creative Commons Zero.

[Roughly Enough Items] and [ModMenu] are included as optional modules, and [CoreLib] is included with the Textures API.
They all also share the [MIT] license.

(Pre-Lex era) Minecraft Forge API is also included as an optional module to help ease the transition for modders.
It is licensed under the [Minecraft Forge Public License v1.0](./beta-forge-hooks/LICENSE).

Big thanks to the Minecraft Cursed Legacy team, Modification Station, TerraformersMC, paulevs and shedaniel for their
contributions to the modding community.

# About Better Than Updates

Better Than Updates is dedicated to providing ***one*** stable modding platform for mods and overhauls,
free from the tyranny of closed-source Minecraft updates.

Better Than Updates has chosen beta 1.7.3 for its community, and it's existing overhauls.
([Better Than Wolves] and [New Frontier Craft]). They are currently being ported to Better Than Updates to maintain
compatibility.

Better Than Updates will *not* be supporting other versions, to keep focus on ***modding*** *rather than updates.*

[Roughly Enough Items]:(https://github.com/shedaniel/RoughlyEnoughItems)
[ModMenu]:(https://github.com/TerraformersMC/ModMenu)
[CoreLib]:(https://github.com/paulevsGitch/B.1.7.3-CoreLib)
[Better Than Wolves]:(https://sargunster.com/btw/index.php)
[New Frontier Craft]:(https://newfrontiercraft.net)
[Cursed Legacy API]:(https://github.com/minecraft-cursed-legacy/Cursed-Legacy-API)
[Station API]:(https://github.com/ModificationStation/StationAPI)
[Forge API]:(https://github.com/MinecraftForge/MinecraftForge/tree/0a874de91d6b26a9369d1ab0fb19ebe47a5124e3)
[Forge Mod Loader]:(https://github.com/MinecraftForge/FML)

[MIT](./LICENSE)
