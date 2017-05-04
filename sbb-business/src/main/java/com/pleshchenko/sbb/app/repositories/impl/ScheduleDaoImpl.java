package com.pleshchenko.sbb.app.repositories.impl;

import com.pleshchenko.sbb.app.entity.schedule.Schedule;
import com.pleshchenko.sbb.app.repositories.interfaces.AbstractDao;
import com.pleshchenko.sbb.app.repositories.interfaces.ScheduleDao;
import com.pleshchenko.sbb.app.service.interfaces.StationService;
import com.pleshchenko.sbb.app.service.interfaces.TrainService;
import com.pleshchenko.sbb.app.service.other.SetId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.sql.Date;
import java.util.List;

////???????? время показывает на три часа меньше чем храниться в БД временно оставлю так

/**
 * Created by РОМАН on 06.04.2017.
 */

@Repository("scheduleDao")
public class ScheduleDaoImpl extends AbstractDao<Integer,Schedule> implements ScheduleDao {

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Override
    public Schedule findById(Integer id) {
        return getByKey(id);
    }

    @Override
    public List<Schedule> findAll() {

        List<Schedule> schedule = getEntityManager()
                .createQuery("SELECT s FROM Schedule s ORDER BY s.departureTime")
                .getResultList();

        return schedule;
    }

    @Override
    public List<Schedule> findByParameters(int st1,int st2,Date date1,Date date2) {

        String NATIVE_QUERY = "SELECT \n" +
                "   dir.id, " +
                "   dir.active, " +
                "   dir.routeId, " +
                "   dir.trainId, " +
                "   dir.departureTime, " +
                "   dir.destinationTime " +
                "FROM \n" +
                "\tschedule dir      \n" +
                "LEFT JOIN \n" +
                "\n" +
                "(SELECT \n" +
                "    r.id routeId, \n" +
                "    s.departureStationId stId,\n" +
                "    rc.departureTime depTime\n" +
                "FROM route r LEFT JOIN routeComposition rc on r.id=rc.routeId LEFT JOIN segment s on rc.segmentId=s.id) t1 ON t1.stId = :st1 AND t1.routeId = dir.routeId\n" +
                "\n" +
                "LEFT JOIN \n" +
                "\n" +
                "(SELECT \n" +
                "    r.id routeId, \n" +
                "    s.departureStationId stId,\n" +
                "    rc.departureTime depTime\n" +
                "FROM route r LEFT JOIN routeComposition rc on r.id=rc.routeId LEFT JOIN segment s on rc.segmentId=s.id) t2 ON t2.stId = :st2 AND t2.routeId = dir.routeId\n" +
                "  \n" +
                "WHERE t1.depTime < t2.depTime " +
                "AND dir.departureTime + INTERVAL t1.depTime MINUTE BETWEEN :date1 AND (:date2 + INTERVAL 24*3600-1 SECOND)";

        List<Schedule> schedules = getEntityManager()
                .createNativeQuery(NATIVE_QUERY, Schedule.class)
                .setParameter("st1",st1)
                .setParameter("st2",st2)
                .setParameter("date1",date1)
                .setParameter("date2",date2)
                .getResultList();
        return schedules;
    }

    @Override
    public List findFreeSite(int st1,int st2,int dirId,int routeId) {

        String NATIVE_QUERY = "SELECT \n" +
                "\n" +
                "ts.carId,\n" +
                "sp.number,\n" +
                "sc.countSegment,\n" +
                "count(ts.segmentId)\n" +
                "\n" +
                "FROM `tripsSite` ts\n" +
                "\n" +
                "LEFT JOIN sitePrototype sp ON sp.id = ts.sitePrototypeId\n" +
                "\n" +
                "LEFT JOIN (\n" +
                "\n" +
                "SELECT\n" +
                "count(segmentId) countSegment\n" +
                "\n" +
                "FROM `routeComposition` \n" +
                "\n" +
                "WHERE routeId = :routeId \n" +
                "   \n" +
                "AND departureTime >= (SELECT departureTime\n" +
                "    \n" +
                "\t\t\tFROM `routeComposition`rc\n" +
                "\n" +
                "\t\t\tLEFT JOIN segment s ON rc.segmentId= s.id\n" +
                "\n" +
                "\t\t\tWHERE rc.routeId = :routeId AND s.departureStationId = :st1)\n" +
                "AND destinationTime <= (SELECT destinationTime   \n" +
                "\t\t\t\n" +
                "\t\t\tFROM `routeComposition`rc\n" +
                "\n" +
                "\t\t\tLEFT JOIN segment s ON rc.segmentId= s.id\n" +
                "\n" +
                "\t\t\tWHERE rc.routeId = :routeId AND s.destinationStationId = :st2)\n" +
                "\n" +
                ") sc on true\n" +
                "\n" +
                "WHERE scheduleId = :dirId\n" +
                "    \n" +
                "AND ts.sold = FALSE\n" +
                "\n" +
                "AND segmentId In (\n" +
                "\n" +
                "SELECT segmentId \n" +
                "\n" +
                "FROM `routeComposition` \n" +
                "\n" +
                "WHERE routeId = :routeId \n" +
                "\n" +
                "AND departureTime >= (SELECT departureTime\n" +
                "    \n" +
                "\t\t\tFROM `routeComposition`rc\n" +
                "\n" +
                "\t\t\tLEFT JOIN segment s ON rc.segmentId= s.id\n" +
                "\n" +
                "\t\t\tWHERE rc.routeId = :routeId AND s.departureStationId = :st1)\n" +
                "AND destinationTime <= (SELECT destinationTime   \n" +
                "\t\t\t\n" +
                "\t\t\tFROM `routeComposition`rc\n" +
                "\n" +
                "\t\t\tLEFT JOIN segment s ON rc.segmentId= s.id\n" +
                "\n" +
                "\t\t\tWHERE rc.routeId = :routeId AND s.destinationStationId = :st2)\n" +
                ")\n" +
                "\n" +
                "GROUP By ts.carId,sp.number\n" +
                "\n" +
                "HAVING count(ts.segmentId) = sc.countSegment";

        List list = getEntityManager()
                .createNativeQuery(NATIVE_QUERY)
                .setParameter("st1",st1)
                .setParameter("st2",st2)
                .setParameter("dirId",dirId)
                .setParameter("routeId",routeId)
                .getResultList();

        return list;
    }

    @Override
    public List<Schedule> findByStation(String stationName) {


        List<Schedule> schedule = getEntityManager()
                .createQuery("SELECT s FROM Schedule s WHERE s.segment.departureStation.name LIKE '" + stationName + "'")
                .getResultList();
        return schedule;
    }

    @Override
    public void save(Schedule dir){
        persist(dir);
    }

    private Instant dateToInstant(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return instant;
    }
}

