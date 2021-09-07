# Fabric Beta Essentials

The ***modern*** essential mod development pack for **Minecraft beta 1.7.3.**
Includes 

The goal for the module structure of this API (still up for discussion) currently looks like this:

- [beta-accessibility](./beta-accessibility):
  - Full localization support (with selector menu)
  - Sound categories (via SoundCategory registry)
  - Closed caption support
- [beta-essentials]:
  - Events
    - Essential hooks (events)
  - Common API classes (like `Identifier`)
  - Fixes for beta weirdness
  - Credits entries for mod authors and contributors
- [beta-config](./beta-config):
  - Properties for basic configs
  - JSON5 for more advanced configs
  - Automatic ModMenu screen generator (optional)
- [beta-content](./beta-content):
  - Custom audio
  - Custom commands
  - Custom recipes
  - Custom content (blocks, entities, block entities, items, particles, etc.)
  - [beta-registries](./beta-registries):
    - Identifier-based registry system to destroy integer-based ID conflicts.
    - Built-in registries for:
      - [ ] Achievements
      - [x] Blocks
      - [x] Items
      - [x] Entity types
      - [ ] Block entity types
      - [ ] Painting "motives"
      - [ ] Sound categories
      - [ ] Sound events
- [beta-mod-loader-support]:
  - Support for loading old mods with Quilt Loader
  - Stepping stones for porting old mods.
  - [beta-mod-adapter]:
    - [ ] Adapter for loading Risugami's ModLoader mods with Fabric
  - [beta-forge-hooks]:
    - [ ] Full implementation of Forge API version 1.0.X as a compatibility layer
  - [beta-risugami-hooks]:
    - [ ] Full implementation of Risugami's ModLoader & ModLoaderMP API v2 as a compatibility layer
  - beta-fukkit:
    - [ ] Full implementation of CraftBukkit API version 1060 as a compatibility layer
- [beta-events](./beta-events):
  - [beta-lifecycle-events]:
    - [x] Events for startup, shutdown, login, and disconnect
    - [ ] Events for saving and loading the world.
  - [beta-interaction-events]:
    - [x] Events for interactions between the player and other in-game objects.
  - [beta-entity-events]:
    - [x] Events for sleep, combat, dimension-change, death and respawning, etc.
- [beta-networking](./beta-networking):
  - Packet sending/receiving and handling
- [beta-resources](./beta-textures):
  - Automatic custom texture and texture atlas handling
- [beta-world](./beta-world):
  - [beta-dimension-types]:
    - Dimensions
  - [beta-terrain-gen]:
    - Terrain generation
  - [beta-world-types]:
    - World types
- [modmenu](./modmenu):
  - Mod list showcasing mod metadata
  - Full support for all modern features
  - Robust API for adding custom metadata
- [roughly-enough-items](./roughly-enough-items):
  - In-game item list
  - Recipes accessible from the aforementioned list
  - API for adding custom recipe types
- [compatibility](./compatibility)
  - [forge-api](./compatibility/forge-api)
  - [forge-mod-loader](./compatibility/forge-mod-loader)

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

[MIT](./LICENSE)
