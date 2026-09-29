# Mining Helmet

A dyeable mining helmet with a built-in lantern. Wear it and the area around you lights up, so you can explore caves without placing torches and keep your offhand free.

- **Recipe:** iron helmet + lantern (shapeless)
- **Dyeing:** craft with any dye, like leather armour
- **Light:** comes from a dynamic lights mod: [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) (Fabric, Quilt, NeoForge) or [Tschipcraft's Dynamic Lights](https://modrinth.com/mod/dynamic-lights) (server-side, any loader; used for Forge 1.20.1 and NeoForge 1.21.4). [Sodium Dynamic Lights](https://modrinth.com/mod/sodium-dynamic-lights) also works on Forge 1.20.1, but only in singleplayer or on servers that have it too
- **Requires:** [GeckoLib](https://modrinth.com/mod/geckolib), plus [Fabric API](https://modrinth.com/mod/fabric-api) on Fabric/Quilt

Download: [Modrinth](https://modrinth.com/mod/mining-helmet-fabric)

## Building

Each folder in `targets/` is one Minecraft version + loader, e.g. `targets/1.21.1-neoforge`. Its `gradle.properties` holds the versions for that build (Minecraft, loader, Fabric API, GeckoLib, and which light mod to depend on).

Gradle must run on **Java 25** (Fabric Loom 1.18 requires it). Older targets still compile for Java 17/21.

```
./gradlew distAll                     # build every target into build/dist/
./gradlew :1.21.1-neoforge:dist       # build one target
./gradlew :1.21.1-fabric:runClient    # play-test one target (runServer also works)
```

`runClient -PjoinLocalServer` joins a `runServer` running on the same machine.

## One source tree, many versions

All targets share `src/main` (common code and assets) plus `src/fabric`, `src/neoforge` or `src/forge`. Code that differs between Minecraft versions or loaders is wrapped in preprocessor comments, which the build resolves per target before compiling:

```java
//#if MC >= 1.21.5
super(properties.humanoidArmor(MiningHelmet.MATERIAL, ArmorType.HELMET));
//#elif MC >= 1.21.2
super(MiningHelmet.MATERIAL, ArmorType.HELMET, properties);
//#else
...
//#endif
```

Conditions compare `MC` with a version, or test `FABRIC`, `NEOFORGE`, `FORGE` or `SODIUM_DYNAMIC_LIGHTS`, combined with `&&`, `||` and `!`. The same comments work in `.json` and `.toml` resources. The preprocessor lives at the top of `build.gradle.kts`. The build also renames data folders for pre-1.21 targets (`recipe/` → `recipes/`) and moves GeckoLib models into `geckolib/` for 1.21.5+.

## Adding a new Minecraft version

1. Copy the newest folder in `targets/` and update its `gradle.properties` (Minecraft, loader, Fabric API and GeckoLib versions).
2. Run `./gradlew :<target>:compileJava` and add `//#if` branches where the APIs changed.
3. Play-test with `runClient`, then publish.

## Publishing to Modrinth

```
MODRINTH_TOKEN=<token> ./gradlew :1.21.1-fabric:modrinth   # one target
MODRINTH_TOKEN=<token> ./gradlew modrinth                  # every target
```

The version number, game versions, loaders (Fabric builds are also tagged Quilt), dependencies and changelog (top section of `CHANGELOG.md`) come from the target and root `gradle.properties`. Bump `mod_version` before each release.

## License

MIT
