package hdang09.services;

import hdang09.entities.Report;
import hdang09.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    ReportRepository repo;

    public Report getByAccountId(int accountId) {
        // TODO: Update this code
        return null;
    }

    public Report getByAccountIdAndDate(int accountId, int year, int month) {
        // TODO: Update this code
        return null;
    }
}
