================================================================================
                                  MEOWIFY
                   Client-Side Minecraft Forge 1.20.1 Mod
                              "Because I'm a cat!"
================================================================================

Meowify is a purely client-side Forge 1.20.1 mod that meowifies and transforms
all outgoing chat messages sent by the player into cat / furry speech!
Inspired by the uwufication mechanics in Create: Estrogen and internet cat culture.

No server installation required. Compatible with any vanilla or modded SMP server!


FEATURES
================================================================================
* Full Furry / UwUfication (Default):
  - Phonetics: Turns 'r' and 'l' into 'w' (preserving case: R/L -> W).
  - Nya-fication: Syllables like na/ne/ni/no/nu become nya/nye/nyi/nyo/nyu.
  - Furry Cat Vocabulary: Replaces words with cat/furry slang
    (e.g., "right now" -> "right meow", "the" -> "da", "with" -> "wif",
    "please" -> "purrlease", "friend" -> "furriend", "thanks" -> "fankies",
    "good/great" -> "pawsome/grrreat", "human" -> "hooman").
  - Cute Stuttering: Occasional hesitations (e.g., "H-Hewwo", "w-wat").
  - Roleplay Actions & Emotes: Injects cat actions (*purrs*, *swishes tail*,
    *nuzzles*, *paw pats*) and kaomoji (:3, >w<, owo, uwu, rawr x3, (=^･ω･^=)).
  - Expressive Punctuation: Turns '!' into excited '~!! >w<' or '! :3',
    and '?' into curious '? owo?' or '? mew?'.

* Audio Effects:
  - Plays cat meows and purrs locally on the client when sending chat messages.

* Safety & Protections:
  - Web URLs (http:// and https://) are preserved and never mangled.
  - Pure numbers (coordinates, amounts) can be preserved via config.
  - Commands (starting with '/') are never modified.

* 4 Transformation Modes:
  1. FURRY (Default) - Create: Estrogen-style full furry / uwu chat.
  2. MEOW            - Pure cat speak (all words replaced by meows and purrs).
  3. PUNS            - Subtle cat puns and feline wordplay only.
  4. SUFFIX          - Appends cute cat sounds and emotes to sentence ends.


IN-GAME COMMANDS
================================================================================
/meowify                     - Display current settings and help
/meowify toggle              - Toggle chat meowification on/off
/meowify mode <mode>         - Switch mode (furry, meow, puns, suffix)
/meowify sound               - Toggle client-side cat sounds on/off
/meowify test <message>      - Preview how a message will be meowified
/meowify imacat              - "(=^･ω･^=) I'm a cat! Meow! :3"
/meow [message]              - Quick meow command


CONFIGURATION
================================================================================
Configuration is stored in `.minecraft/config/meowify-client.toml`:

[general]
  enabled = true                 # Whether meowification is active
  mode = "FURRY"                 # FURRY, MEOW, PUNS, or SUFFIX
  playCatSound = true            # Play sound effect when chatting
  catSoundVolume = 1.0           # Volume (0.0 to 1.0)
  catSoundPitchVariation = true  # Randomize pitch slightly
  preserveUrls = true            # Keep links unchanged
  preserveNumbers = false        # Keep numbers unchanged (e.g. coords)
  appendCatFace = false          # Always ensure message ends with :3


BUILDING & DEVELOPMENT
================================================================================
Requirements:
* Java 17 JDK
* Gradle (wrapper included)
* Minecraft Forge 1.20.1 (47.4.20)

Commands:
* Compile & Build Jar:
  ./gradlew build
  (Output jar located in build/libs/meowify-1.0.0.jar)

* Run Unit Tests:
  ./gradlew test


INSTALLATION
================================================================================
1. Copy `meowify-1.0.0.jar` into your Minecraft instance's `mods` folder:
   e.g. `%APPDATA%\.minecraft\mods\`
   or for PrismLauncher:
   `...\PrismLauncher\instances\<InstanceName>\minecraft\mods\`
2. Launch Minecraft 1.20.1 using Forge loader.
3. Enjoy being a cat! (=^･ω･^=)
