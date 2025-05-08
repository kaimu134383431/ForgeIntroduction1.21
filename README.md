# ForgeItemStorage

## 概要
本MODは、Minecraft Forge 1.21.1に対応した、**一種類のアイテムを大量に格納できるブロック**を追加するMODです。  
MineFactoryReloadedのDeep Storage Unitのような仕様を目指し、保存処理・GUI・同期・描画まで実装しています。

## 使用技術
- Java 17
- Minecraft Forge 1.21.1
- ForgeGradle
- NBT / ItemStackHandler / BlockEntity / ContainerMenu

## 主な機能
- 1スロットの大量保存処理（サーバー／クライアント同期あり）
- GUIでの入出庫・表示
- BlockEntityRendererによるモデル描画
- データ保存（NBT）／ネットワーク同期

## 実行方法
本MODは開発環境内での起動（`runClient`）にて確認可能です。ビルド済みバージョンは現在未公開です。

