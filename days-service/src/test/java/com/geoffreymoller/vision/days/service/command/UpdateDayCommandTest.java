package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.DayJdbiDao;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateDayCommandTest {

    private long ID = 1L;
    private long USER_ID = 1L;
    private String JOURNAL = "journal content";
    private DateTime DATE = new DateTime(1430600085578L);
    private MetricRegistry registry;

    DBI dbiMock;
    DayJdbiDao dao;

    @Before
    public void setupTest() {
        dao = mock(DayJdbiDao.class);
        dbiMock = mock(DBI.class);
        registry = mock(MetricRegistry.class);
    }

    @Test
    public void testRun() throws Exception {
        Day day = new Day(ID, USER_ID, DATE, JOURNAL);
        when(dao.update(day)).thenReturn(day);
        Optional<Day> returnedDayOptional = new UpdateDayCommand(registry, dao, day).execute();
        assertTrue(returnedDayOptional.isPresent());
        assertEquals(returnedDayOptional.get(), day);
    }

    @Test
    public void testGetFallback() throws Exception {
        Day day = new Day(ID, USER_ID, DATE, JOURNAL);
        when(dao.update(day)).thenThrow(new NullPointerException());
        Optional<Day> returnedDayOptional = new UpdateDayCommand(registry, dao, day).execute();
        assertFalse(returnedDayOptional.isPresent());
    }

}