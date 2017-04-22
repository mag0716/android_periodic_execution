# android_periodic_execution

Android の定期実行を検証する

## 目的

* 定期実行を以下で試してそれぞれどのくらいずれるのかを検証する
    * AlarmManager
    *　JobScheduler
    * GcmNetworkManager
    * Firebase JobDispatcher
    
## アプリ

想起訓練により記憶を促すアプリ

### 必要な機能

* 新規登録
    * Question と Answer を新規登録
    * [Option] 画像も登録できると便利そう
* 想起訓練
    * 以下の間隔で Notification を表示
    * [TODO] アプリ表示中は別の方法で訓練した方がいいか？
    * Notification には Question を表示
    * タップで Answer を表示
    * 忘れていた／覚えていたをタップ
    * 忘れていた場合は、1回目から想起訓練をやり直す
    * 覚えていた場合は、以降の間隔で定期実行を登録
* 一覧表示
    * Question の表示
    * 訓練状況の表示
    * 次回の訓練予定の表示
    * Answer はユーザ操作なしでは表示できないようにしておく
* トラッキング
* 設定画面
* 定期実行で利用するクラスの変更
    * デフォルトは GcmNetworkManager
* 削除
* 編集

### 訓練間隔

* 1回目：数秒から30秒程度以内に複数回
* 2回目:数分以内
* 3回目:1時間から1日以内
* 4回目:1日後
* 5回目以降:1ヶ月後

### 検証に必要な情報

* 想定していた通知時間
* 実際に通知された時間
* OS
* 定期実行に使用していたクラス
* Doze 中だったかどうか
* 定期実行のホワイトリスト設定に含まれていたかどうか
* 以下は必要かどうか不明
    * ネットワーク状況
    * 充電状況

### どうせなら試してみたいこと

* Firebase
    * Crashlytics
    * Push
* A/B テスト
* 広告

## 参考

* https://speakerdeck.com/kazy1991/droidkaigi-2017
