# 🐾 Meowify

> **A client-side Minecraft 1.20.1 Forge mod that turns your chat into full furry / cat speak. Because I'm a cat! (=^･ω･^=)**

Inspired by the uwufication mechanics in *Create: Estrogen* and feline internet culture, **Meowify** intercepts and transforms outgoing chat messages into adorable cat & furry speak before they are sent to the server.

Because it is **100% client-side**, it works on singleplayer worlds as well as any vanilla or modded Forge SMP server without needing to be installed on the server!

---

## ✨ Features

- **Full Furry / UwUfication (Default Mode)**:
  - **Phonetic UwU**: Transforms `r` and `l` into `w` (preserving case: `R`/`L` $\rightarrow$ `W`).
  - **Nya-fication**: Converts syllables like `na`, `ne`, `ni`, `no`, `nu` into `nya`, `nye`, `nyi`, `nyo`, `nyu`.
  - **Feline Vocabulary**: Replaces everyday words with cat/furry slang (*"right now"* $\rightarrow$ *"right meow"*, *"the"* $\rightarrow$ *"da"*, *"with"* $\rightarrow$ *"wif"*, *"please"* $\rightarrow$ *"purrlease"*, *"friend"* $\rightarrow$ *"furriend"*, *"thanks"* $\rightarrow$ *"fankies"*, *"good/great"* $\rightarrow$ *"pawsome/grrreat"*, *"human"* $\rightarrow$ *"hooman"*).
  - **Cute Stuttering**: Random consonant hesitations (*"H-Hewwo"*, *"w-wat"*, *"p-purrlease"*).
  - **Roleplay Actions & Emotes**: Injects cute cat actions (`*purrs*`, `*swishes tail*`, `*nuzzles*`, `*paw pats*`, `*tilts head*`) and kaomoji (`:3`, `>w<`, `owo`, `uwu`, `^w^`, `rawr x3`, `(=^･ω･^=)`).
  - **Expressive Punctuation**: Turns `!` into excited `~!! >w<` or `! :3`, and `?` into curious `? owo?` or `? mew?`.

- **Client-Side Audio Effects**:
  - Plays authentic Minecraft cat meow and purr sound effects locally whenever you send a chat message.

- **Safety & Protections**:
  - **URL Protection**: Web links (`http://` and `https://`) remain untouched.
  - **Coordinate Protection**: Configurable option to preserve pure numbers (coordinates, item counts).
  - **Command Safe**: Commands starting with `/` are never modified.

---

## 🎮 Transformation Modes

| Mode | Description | Example Output |
| :--- | :--- | :--- |
| **`FURRY`** *(Default)* | *Create: Estrogen*-style full furry / uwu chat | `"H-Hewwo, wat awe chu mewing wight meow? owo *tilts head*"` |
| **`MEOW`** | Pure cat sounds (all words replaced by meows) | `"Meow meow, purr mew meow? (=^･ω･^=)"` |
| **`PUNS`** | Subtle cat puns and feline wordplay | `"Hello, what are you doing right meow? ~ meow :3"` |
| **`SUFFIX`** | Appends cute cat sounds and emotes to endings | `"Hello, what are you doing? ~ purr~ :3"` |

---

## 💬 In-Game Commands

| Command | Description |
| :--- | :--- |
| `/meowify` | Display current settings and help menu |
| `/meowify toggle` | Toggle chat meowification on or off |
| `/meowify mode <furry\|meow\|puns\|suffix>` | Switch transformation mode |
| `/meowify sound` | Toggle client-side cat sounds on or off |
| `/meowify test <message>` | Preview how a message will look when meowified |
| `/meowify imacat` | Broadcast cat identity (`(=^･ω･^=) I'm a cat! Meow! :3`) |
| `/meow [message]` | Shortcut command to send a meow |

---

## ⚙️ Configuration

Settings are saved in `.minecraft/config/meowify-client.toml`:

```toml
[general]
  # Whether chat meowification is currently active
  enabled = true

  # Transformation mode: FURRY, MEOW, PUNS, or SUFFIX
  mode = "FURRY"

  # Play a cat sound on client when sending chat messages
  playCatSound = true

  # Volume of the cat sound (0.0 to 1.0)
  catSoundVolume = 1.0

  # Whether to randomize the pitch of the cat meow slightly
  catSoundPitchVariation = true

  # Preserve web URLs in chat messages without meowifying them
  preserveUrls = true

  # Preserve pure numbers (such as coordinates) without meowifying them
  preserveNumbers = false

  # Always append a cat emoticon (:3) to messages
  appendCatFace = false
```

---

## 🛠️ Building & Development

### Requirements
- **Java 17 JDK**
- **Gradle 8.8** (wrapper included)
- **Minecraft Forge 1.20.1** (build `47.4.20`)

### Gradle Commands
```powershell
# Compile, reobfuscate, and build the mod jar
./gradlew build

# Run unit tests
./gradlew test
```
The compiled jar will be located at `build/libs/meowify-1.0.0.jar`.

---

## 📦 Installation

1. Download or build `meowify-1.0.0.jar`.
2. Place the jar file in your Minecraft instance's `mods` folder:
   - **Standard**: `%APPDATA%\.minecraft\mods\`
   - **PrismLauncher**: `<Instance Directory>\minecraft\mods\`
3. Launch Minecraft with the Forge 1.20.1 profile.
4. Enjoy being a cat! 🐾
