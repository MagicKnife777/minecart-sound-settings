# Minecart Sound Settings / 矿车声音设置

[![Minecraft](https://img.shields.io/badge/Minecraft-26.3-brightgreen.svg)](https://minecraft.net)
[![Fabric](https://img.shields.io/badge/Loader-Fabric-blue.svg)](https://fabricmc.net)

**Minecart Sound Settings** is a client-side Fabric mod that adds a separate volume slider specifically for minecart sounds in the vanilla "Music & Sounds" settings menu. No longer will you have to mute all neutral mobs just to quiet down the annoying rumbling of a minecart!

**矿车声音设置** 是一个纯客户端 Fabric 模组。它在原版的“音乐和声音”设置界面中添加了一个专门针对“矿车声音”的独立音量滑块。你再也不用为了屏蔽矿车的噪音而被迫关掉所有“环境”或“友好生物”的声音了！

---

## Features / 功能特性

- **Independent Volume Control / 独立音量控制**: A dedicated slider for all minecart-related sounds (riding sounds and internal sounds). / 为所有矿车相关音效（乘坐音和内部音）提供独立的控制滑块。
- **Real-time Adjustment / 实时调节**: The volume changes instantly as you drag the slider. / 拖动滑块时，音效大小会实时改变。
- **Perfect Integration / 完美融入**: The slider seamlessly blends into the vanilla sound settings list. / 滑块完美嵌入原版的音效设置列表中。
- **Config Persistence / 配置保存**: Your preferred volume setting is saved locally. / 你偏好的音量设置会被自动保存，重启游戏后依然生效。

---

## Installation / 安装说明

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 26.3. / 安装适用于 Minecraft 26.3 的 Fabric Loader。
2. Download and install [Fabric API](https://modrinth.com/mod/fabric-api). / 下载并安装 Fabric API。
3. Download the latest `.jar` file of this mod from the [Releases](https://github.com/magicknife/minecart-sound-settings/releases) page. / 从 Releases 页面下载本模组最新的 `.jar` 文件。
4. Place the `.jar` file into your `.minecraft/mods` folder. / 把 `.jar` 文件放入你的 `.minecraft/mods` 文件夹中。
5. Launch the game! / 启动游戏！

---

## Building from Source / 从源码构建

If you want to build the mod yourself, you need JDK 25 and Git installed. / 如果你想自己编译，请确保安装了 JDK 25 和 Git。

```bash
# Clone the repository / 克隆仓库
git clone https://github.com/magicknife/minecart-sound-settings.git
cd minecart-sound-settings

# Build the mod / 构建模组
./gradlew build