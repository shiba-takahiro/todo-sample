package jp.co.miraino_katachi.todo_sample.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo_sample.dao.DAOFactory;
import jp.co.miraino_katachi.todo_sample.dao.ItemDAO;
import jp.co.miraino_katachi.todo_sample.exceptions.DAOException;

public class DeleteActionLogic {
	private static final Logger logger = LoggerFactory.getLogger(DeleteActionLogic.class);

	public boolean execute(int itemID) throws DAOException {
		logger.trace("Enter");

		ItemDAO itemDAO = DAOFactory.createItemDAO();
		if(!itemDAO.delete(itemID)) {
			logger.error("作業項目の削除に失敗しました");
			return false;
		}

		logger.trace("Exit");
		return true;
	}

}
