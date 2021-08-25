# Fabric Beta Essentials

The unofficial [Fabric](https://fabricmc.net/) fork for ***Beta 1.7.3.***
Also includes some essentials as modules.

For more information, see each module's README.md file.

## Setup
Import the `build.gradle` file as a project in Intellij IDEA. (Or Eclipse, if supported. Not recommended.)

NOTE: if you want sources (recommended), click the Gradle task `Tasks -> fabric -> genSources`,
or run `gradlew genSources` via commandline.

To be able to run the API as a whole in an API dev environment, ensure the root project has been set up in your ide. This should be automatic, but it pays to make sure.

If you wish to build a copy of API, you can either click the Gradle task `Tasks -> build -> build`,
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
Do you have a suggestion for better versioning? [Create an issue](https://github.com/Better-Than-Updates-MC/API)
here on GitHub, or bring it up on [Discord](https://halotroop.com/Discord.html).

## License

[Unless otherwise stated](./beta-forge-hooks/LICENSE), these APIs are available under the [MIT] license.
Feel free to learn from them and incorporate them in your own projects.

Fabric Beta Essentials is based ***heavily*** on [Cursed Legacy API] and also "borrows" code from [Station API].
Both of which share the [MIT] license of Beta Fabric Essentials.

[Roughly Enough Items] and [ModMenu] are included as optional modules, and [CoreLib] is included with the Textures API.
They all also share the [MIT] license.

(Pre-Lex era) Minecraft Forge API is also included as an optional module to help ease the transition for modders.
It is licensed under the [Minecraft Forge Public License v1.0](./beta-forge-hooks/LICENSE).

Big thanks to the Minecraft Cursed Legacy team, Modification Station, TerraformersMC, paulevs and shedaniel for their
contributions to the modding community.

# About Better Than Updates

Better Than Updates is dedicated to providing ***one*** stable modding platform for mods and overhauls,
free from the tyranny of closed-source Minecraft updates.

Better Than Updates has chosen beta 1.7.3 for its community, it's existing overhauls.
([Better Than Wolves] and [New Frontier Craft]). They are currently being ported to Better Than Updates to maintain
compatibility.

Better Than Updates will *not* be supporting other versions, to keep focus on ***modding*** *rather than updates.*

[Better Than Wolves]:(https://sargunster.com/btw/index.php)
[New Frontier Craft]:(https://newfrontiercraft.net)
[Cursed Legacy API]:(https://github.com/minecraft-cursed-legacy/Cursed-Legacy-API)
[Station API]:(https://github.com/ModificationStation/StationAPI)

[MIT](./LICENSE)