package jp.co.miraino_katachi.todo_sample.dao;

public class DAOFactory {

	public static UserDAO createUserDAO() {
		return new UserDAOImpl();
	}

	public static ItemDAO createItemDAO() {
		return new ItemDAOImpl();
	}
}
