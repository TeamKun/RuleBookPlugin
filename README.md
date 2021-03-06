# RuleBookPlugin  
**〇概要**  
・設定した記述済みの本をログインしたプレイヤーに表示することができる。   
・設定はconfig.ymlからも設定可能    
・動作確認済み:Minecraft1.15.2 ,Paper1.15.2  
  
  
  
**〇コマンド一覧**  
*・/rulebook add*    
リストに本を追加する。右手に記述済みの本を持ちながら実行する。
  
*・/rulebook list*  
リストに登録された本のタイトルの一覧を表示する。  
  
*・/rulebook new*  
未記入の本をコマンド実行者に与える。
  
*・/rulebook remove <リスト内の本のタイトル>*  
リストからすべて、もしくは対象の本を削除する。  
  
*・/rulebook on-join set <リスト内の本のタイトル>*   
ログイン時に表示する本の設定。  
  
*・/rulebook on-join remove*  
joinbookコマンドで登録された本を削除します。  
  
*・/rulebook on-join get*  
joinbookコマンドで登録された本を見ることができます。  
  
*・/rulebook show <リスト内の本のタイトル> <対象のプレイヤー>*  
指定した本を対象のプレイヤーに読ませる。  
  
*・/rulebook give <リスト内の本のタイトル> <対象のプレイヤー>*  
指定した本を対象のプレイヤーに与える。  
  
*・/rulebook config reload*  
コンフィグをリロードします。  
  
*・/rulebook config save*  
コンフィグに現在の設定を保存します。
