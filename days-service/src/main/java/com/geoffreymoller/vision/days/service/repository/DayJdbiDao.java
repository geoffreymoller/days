package com.geoffreymoller.vision.days.service.repository;

import com.geoffreymoller.vision.days.service.domain.Activity;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.mapper.ActivityMapper;
import com.geoffreymoller.vision.days.service.repository.mapper.DayMapper;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class DayJdbiDao implements DayDao {

    private DayDao dayDao;
    private ActivityDao activityDao;

    public DayJdbiDao(DBI dbi) {
        dayDao = dbi.onDemand(DayDao.class);
        activityDao = dbi.onDemand(ActivityDao.class);
    }

    @Override
    public Day save(Day day) {
        long dayId = dayDao.insert(day);
        if(!day.getActivities().isEmpty()){
            insertActivities(day.getActivities());
        }
        return get(dayId);
    }

    public Day update(Day day) {
        //TODO - diff, delegate
        dayDao.update(day);
        return get(day.getId());
    }

    private void insertActivities(List<Activity> activities) {
        List<Long> ids = activities.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<Long> userIds = activities.stream().map(a -> a.getUserId()).collect(Collectors.toList());
        List<Long> dayIds = activities.stream().map(a -> a.getDayId()).collect(Collectors.toList());
        List<Long> dates = activities.stream().map(a -> a.getDate().getMillis()).collect(Collectors.toList());
        List<String> names = activities.stream().map(a -> a.getName()).collect(Collectors.toList());
        List<String> tags = activities.stream().map(a -> a.getTags()).collect(Collectors.toList());
        List<String> durations = activities.stream().map(a -> a.getDuration()).collect(Collectors.toList());
        List<Long> durationMs = activities.stream().map(a -> a.getDurationMs()).collect(Collectors.toList());
        List<Long> counts = activities.stream().map(a -> a.getCount()).collect(Collectors.toList());
        List<String> notes = activities.stream().map(a -> a.getNotes()).collect(Collectors.toList());
        activityDao.insertMultiple(ids, userIds, dayIds, dates, names, tags, durations, durationMs, counts, notes);
    }

    public Day get(Long userId, Long date) {
        return getDayWithActivities(dayDao.get(userId, date));
    }

    @Override
    public Day get(Long id) {
        return getDayWithActivities(dayDao.get(id));
    }

    @Override
    public Day findByDate(DateTime date) {
        return dayDao.findByDate(date.getMillis());
    }

    @Override
    public int count() {
        return dayDao.count();
    }

    @Override
    public boolean exists(Long id) {
        return dayDao.get(id) != null;
    }

    private Day getDayWithActivities(Day day) {
        List<Activity> activityList = activityDao.findByDate(day.getId());
        if (!activityList.isEmpty()) {
            day = new Day(day.getId(), day.getUserId(), day.getDate(), day.getJournal(), activityList);
        }
        return day;
    }

    @Override
    @Transaction
    public void delete(Long id) {
        Day day = get(id);
        if (!day.getActivities().isEmpty()) {
            day.getActivities()
                    .stream()
                    .map(a -> a.getId())
                    .forEach(activityDao::deleteById);

        }
        dayDao.deleteById(id);
    }

    @RegisterMapper(DayMapper.class)
    private interface DayDao extends Transactional<DayDao> {

        @SqlUpdate("insert into DAY (id, user_id, date, journal) values (:d.id, :d.userId, :d.date, :d.journal)")
        @GetGeneratedKeys
        long insert(@BindBean("d") Day day);

        @SqlUpdate("update DAY set id = :p.id, user_id = :p.userId, date = :p.date, journal = :p.journal where dayId = :d.id")
        void update(@BindBean("d") Day day);

        @SqlQuery("select * from DAY where dayId = :id")
        Day get(@Bind("id") long id);

        @SqlQuery("select * from DAY where user_id = :user_id and date = :date")
        Day get(@Bind("user_id") long user_id, @Bind("date") Long date);

        @SqlQuery("select count(*) from DAY")
        int count();

        @SqlQuery("select * from DAY where date = :date")
        Day findByDate(@Bind("date") long date);

        @SqlUpdate("delete from DAY where dayId = :id")
        void deleteById(@Bind("id") long id);
    }

    @RegisterMapper(ActivityMapper.class)
    private interface ActivityDao extends Transactional<ActivityDao> {

        @SqlBatch("insert into ACTIVITY (id, user_id, day_id, date, name, tags, duration, duration_ms, count, notes) " +
                "values (:id, :user_id, :day_id, :date, :name, :tags, :duration, :duration_ms, :count, :notes)")
        void insertMultiple(@Bind("id") List<Long> ids,
                          @Bind("user_id") List<Long> userIds,
                          @Bind("day_id") List<Long> dayIds,
                          @Bind("date") List<Long> date,
                          @Bind("name") List<String> names,
                          @Bind("tags") List<String> tags,
                          @Bind("duration") List<String> durations,
                          @Bind("duration_ms") List<Long> duration_ms,
                          @Bind("count") List<Long> counts,
                          @Bind("notes") List<String> notes);

        @SqlUpdate("insert into ACTIVITY (id, user_id, day_id, date, name, tags, duration, duration_ms, count, notes) " +
                "values (:a.id, :a.userId, :a.dayId, :a.date, :a.name, :a.tags, :a.duration, :a.durationMs, :a.count, :a.notes)")
        @GetGeneratedKeys
        long insert(@BindBean("a") Activity activity);

        @SqlUpdate("update ACTIVITY set id = :a.id, user_id = :a.userId, day_id = :a.dayId, date = :a.date, name = :a.name, tags = :a.tags," +
                "duration = :a.duration, duration_ms = :a.durationMs, count = :a.count, notes = a.notes where activityId = :a.id")
        void update(@BindBean("a") Activity activity);

        @SqlQuery("select * from ACTIVITY where activityId = :id")
        Day get(@Bind("id") long id);

        @SqlQuery("select * from ACTIVITY where date = :date")
        List<Activity> findByDate(@Bind("date") long date);

        @SqlUpdate("delete from ACTIVITY where activityId = :id")
        void deleteById(@Bind("id") long id);

//        TODO - sql in
//        @SqlUpdate("delete from ACTIVITY where activityId in :ids")
//        void deleteMany(@Bind("ids") long ids);
    }

}
