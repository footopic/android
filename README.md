# 丼 for Android
がんばるぞい


開発はDevelopブランチで行っている。  
DonAPIのURLは直接聞いてください

## SDK VERSION
|TARGET SDK|MIN SDK|
|:--------:|:-----:|
|23        |16     |


## デバック＆アプリ配布
デバッグ＆アプリ配布はDeployGateを利用しています。
以下のリンクから飛べます。  
[https://dply.me/7j0vr8](https://dply.me/7j0vr8)  
バグ報告はSlackの #dev_don_android にお願いします。


## 使用しているライブラリ
* [PagerSlidingTabStrip](https://github.com/jpardogo/PagerSlidingTabStrip)
* [Jackson](https://github.com/FasterXML/jackson)
* [Fresco](https://github.com/facebook/fresco)
* [MarkdownView](https://github.com/falnatsheh/MarkdownView)
* [OkHttp](https://github.com/square/okhttp)
* [OAuth2.0 with the Google API](https://developers.google.com/api-client-library/java/google-api-java-client/oauth2)

### PagerSlidingTabStrip
UIのタブの部分

### Jackson
DonAPIで返ってくるJSONデータの解析

### Fresco
サーバー上にある画像の読み込み。  
他に画像を丸くするのにも使ってる

### MarkdonwView
Markdownの読み込み

### OkHttp
HTTP通信全般

### OAuth2.0 with the Google API
Google+認証
