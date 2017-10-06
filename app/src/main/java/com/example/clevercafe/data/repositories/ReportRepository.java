package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.ReportDao;

/**
 * Created by Chudofom on 06.10.17.
 */

public class ReportRepository {
    public ReportDao reportDao;

    public ReportRepository(ReportDao reportDao) {
        this.reportDao = reportDao;
    }
}
