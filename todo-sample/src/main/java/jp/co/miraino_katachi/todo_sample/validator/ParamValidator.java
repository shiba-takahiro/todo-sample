package jp.co.miraino_katachi.todo_sample.validator;

/**
 *
 * TODOアプリの入力パラメータの検証を行うクラス
 *
 * 文字列の各種チェックには、Apache Commons Lang および
 * 正規表現を利用している
 * @see https://commons.apache.org/proper/commons-lang/
 * @see https://www.javadrive.jp/regex/
 */
public class ParamValidator {

	/**
	 * コンストラクタ
	 * インスタンス化しての使用を禁止するため private としている
	 */
	private ParamValidator() {}

	private static boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * ユーザID の検証
	 * - 半角数字のみであること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validateUserID(String value) {
		if (value == null || value.isEmpty()) {
			return "ユーザID がありません";
		} else if (!isNumber(value)) {
			return "ユーザID が不正です";
		} else {
			return null;
		}
	}

	/**
	 * ユーザ名の検証
	 * - 半角英数50文字以下であること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validateUserName(String value) {
		if (value == null || value.isEmpty()) {
			return "ユーザ名を入力してください";
		} else if (value.length() > 50) {
			return "ユーザ名は50文字以内で入力してください";
		} else if (!value.matches("^[0-9a-zA-Z]+$")) {
			return "ユーザ名は半角英数のみで入力してください";
		} else {
			return null;
		}
	}

	/**
	 * パスワードの検証
	 * - 半角英数記号のみであること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validatePassword(String value) {
		if (value == null || value.isEmpty()) {
			return "パスワードを入力してください";
		} else if (!value.matches("^[\\p{Graph}]+$")) {
			// 正規表現で検証。"\p{Graph}"は POSIX 文字クラスで英数字+句読文字を表す
			return "パスワードは半角英数記号のみで入力してください";
		} else {
			return null;
		}
	}

	/**
	 * 項目 ID の検証
	 * - 半角数字のみであること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validateItemID(String value) {
		if (value == null || value.isEmpty()) {
			return "項目 ID がありません";
		} else if (!isNumber(value)) {
			return "項目 ID が不正です";
		} else {
			return null;
		}
	}

	/**
	 * 項目名 の検証
	 * - 入力されていること
	 * - 1文字以上45文字以下であること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validateItemName(String value) {
		if (value == null || value.isEmpty()) {
			return "項目名を入力してください";
		} else if (value.length() > 45) {
			return "項目名は45文字までにしてください";
		} else {
			return null;
		}
	}

	/**
	 * 期限 の検証
	 * - 入力されていること
	 * - yyyy-MM-dd形式であること
	 *
	 * @param value 検証対象パラメータ
	 * @return エラーメッセージを返す。検証 OK の場合は null
	 */
	public static String validateExpireDate(String value) {
		if (value == null || value.isEmpty()) {
			return "期限を入力してください";
		} else if (!value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
			return "期限は yyyy-MM-dd で入力してください";
		} else {
			return null;
		}
	}
}
