package service.user.status;

import database.StatusDao;
import model.StatusModel;

import java.util.List;

public class StatusService {
    private StatusDao statusDao = new StatusDao();

    public List<StatusModel> getAllStatuses() {
        return statusDao.getAllStatuses();
    }
}