package com.pleshchenko.sbb.service.dao.impl;

import com.pleshchenko.sbb.model.model.entity.route.Station;
import com.pleshchenko.sbb.service.dao.interfaces.AbstractDao;
import com.pleshchenko.sbb.service.dao.interfaces.StationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by РОМАН on 07.04.2017.
 */
@Repository("stationDao")
public class StationDaoImpl extends AbstractDao<Integer,Station> implements StationDao {
    @Override
    public List<Station> findAll() {
        List<Station> stations = getEntityManager()
                .createQuery("SELECT s FROM Station s ORDER BY s.name ASC")
                .getResultList();
        return stations;
    }
}