package com.geoffreymoller.vision.days.service.repository;

import com.geoffreymoller.vision.days.service.domain.Day;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public interface DayDao extends CrudDao<Day>  {
    Day findByDate(DateTime date);
}
