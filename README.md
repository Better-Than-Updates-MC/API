# Fabric Beta API

The unofficial [Fabric](https://fabricmc.net/) fork for ***Beta 1.7.3.***

## Setup
Run the following command (if you are not using eclipse, replace “eclipse” with your relevant ide)

```
./gradlew eclipse
```

NOTE: if you want sources (recommended), instead run this. You only need to run this the first time you need to generate sources.

```
./gradlew :rebuildLVT :genSources eclipse
```

To be able to run the API as a whole in an API dev environment, ensure the root project has been set up in your ide. This should be automatic, but it pays to make sure.

If you wish to build a copy of API, you can run:

```
./gradlew build -x checkstyleTest
```

## Contributing

Make sure to follow the code style guidelines, which can be seen in use in existing files.

CheckStyle can help! Run `gradlew :checkstyleMain` when you're finished with your initial draft,
or install the CheckStyle IDEA plugin and configure it for use with [checkstyle.xml](./config/checkstyle/checkstyle.xml)

## License
[Unless otherwise stated](./legacy-forge-hooks/LICENSE), these APIs are available under the MIT license.
Feel free to learn from them and incorporate them in your own projects.

## Running Tests
If your IDE runs using Gradle, change settings to make your IDE compile it itself, so you can run the test modules.
If your IDE doesn't have this feature get a better IDE not a glorified text editor please. kthx

## Versioning
Subprojects should receive their own [semver](https://semver.org)-compatible versions
The main project is versioned based on refactors that affect any or all of the subprojects. (This is subject to change.)
Do you have a suggestion for better versioning? Create an issue here on GitHub, or bring it up on [Discord](https://halotroop.com/Discord.html).
