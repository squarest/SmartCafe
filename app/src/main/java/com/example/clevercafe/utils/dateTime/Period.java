package com.example.clevercafe.utils.dateTime;

import java.util.Date;

/**
 * Created by Chudofom on 22.08.17.
 */

public class Period {
    public Date startDate;
    public Date endDate;

    public Period() {
    }

    public Period(Date startDate, Date endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
