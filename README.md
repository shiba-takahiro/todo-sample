# todo-sample
- JSP/Servlet
- JSTL
- MySQL

## 環境設定
### Eclipseへプロジェクトをインポート  
- ファイルメニューのインポートから「Git」>「Gitからプロジェクト」を選択し、「次へ」
- クローンURIを選択し、URIにこのリポジトリのURLをセットしてインポートする
- インポートしたプロジェクトを右クリック > Maven > プロジェクトの更新
### MySQLでデータベース[todo_sample]とユーザ[todouser]を作成
```sh
CREATE DATABASE todo_sample CHARACTER SET utf8;
GRANT ALL PRIVILEGES ON todo_sample.* TO 'todouser'@'localhost' IDENTIFIED BY 'todouser';
```
### 作成したデータベース上で todo-sample.sql を実行
```sh
use todo_sample;
source todo-sample.sql
```
## 実行方法
- Eclipseで 実行 > サーバで実行
- http://localhost:8080/todo-sample/  を開く
- ユーザ名[test1]、パスワード[test1]でログイン
