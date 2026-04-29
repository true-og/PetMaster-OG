<p align="center">
<img src ="https://github.com/PyvesB/PetMaster/blob/master/images/banner.png?raw=true" />
<br/>
</p>

# PetMaster-OG

**PetMaster-OG is the [TrueOG Network](https://true-og.net) fork of [PetMaster](https://github.com/PyvesB/PetMaster), updated for the TrueOG 1.19.4 server stack.**

PetMaster still does the core PetMaster job: players can identify pet owners, transfer ownership, free pets, and protect pets from unwanted interaction. This fork changes the integration layer so it fits a modern TrueOG server without Vault or hologram plugins.

## What is different from upstream PetMaster?

- **No Vault dependency.** Economy support uses `DiamondBank-OG` directly, and permissions are checked through Bukkit permissions so they work with `LuckPerms`.
- **Diamond-based pricing.** Paid `/petm free` actions charge `DiamondBank-OG` shards instead of Vault currency.
- **Armor stand hover labels.** Owner labels are shown with temporary invisible marker armor stands when a player looks at a pet, similar to the player-head labels in `PlayerBounties-OG`. This replaces the old hologram/action-bar owner display path.

## Runtime dependencies

- `DiamondBank-OG`
- `Utilities-OG`
- A Bukkit-compatible permissions provider such as `LuckPerms`

# Getting started

#### :cd: Setup

Ensure you have JDK 17 installed, then run:
````
git clone https://github.com/true-og/PetMaster-OG
cd PetMaster-OG
./gradlew build
````
Your generated plugin jar can be found in the `build/libs/` folder.

Find the project useful, fun or interesting? **Star** the repository by clicking on the icon on the top right of this page!

# License 

GNU General Public License v3.0
