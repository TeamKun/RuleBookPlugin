# RuleBookPlugin  
**〇概要**  
・設定した記述済みの本をログインしたプレイヤーに表示することができる。   
・設定はconfig.ymlからも設定可能  
・リストとJoinBookに登録された本はコンフィグに保存されます。  
・動作確認済み:Minecraft1.15.2 ,Paper1.15.2  
  
  
  
**〇コマンド一覧**  
*・/rulebook sddlist*    
リストに本を追加する。右手に記述済みの本を持ちながら実行する。
  
*・/rulebook listinfo*  
リストに登録された本のタイトルの一覧を表示する。  
  
*・/rulebook newbook*  
未記入の本をコマンド実行者に与える。
  
*・/rulebook deletelist <all,リスト内の本のタイトル>*  
リストからすべて、もしくは対象の本を削除する。  
  
*・/rulebook joinbook <リスト内の本のタイトル>*   
ログイン時に表示する本の設定。  
  
*・/rulebook joinread on , /rulebook joinread off*  
joinbookコマンドで設定した本をログイン時に表示するかどうかの設定。  
  
*・/rulebook deletejoinbook*  
joinbookコマンドで登録された本を削除します。  
  
*・/rulebook joinbookshow*  
joinbookコマンドで登録された本を見ることができます。  
  
*・/rulebook read <リスト内の本のタイトル> <対象のプレイヤー>*  
指定した本を対象のプレイヤーに読ませる。  
  
*・/rulebook givebook <リスト内の本のタイトル> <対象のプレイヤー>*  
指定した本を対象のプレイヤーに与える。  
  
