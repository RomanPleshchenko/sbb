package com.pleshchenko.sbb.app.repositories.impl;

import com.pleshchenko.sbb.app.entity.schedule.Route;
import com.pleshchenko.sbb.app.repositories.interfaces.AbstractDao;
import com.pleshchenko.sbb.app.repositories.interfaces.RouteDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by РОМАН on 26.04.2017.
 */
@Repository("routeDao")
public class RouteDaoImpl extends AbstractDao<Integer,Route> implements RouteDao {
    @Override
    public List<Route> findAll() {
        List<Route> routes = getEntityManager()
                .createQuery("SELECT r FROM Route  r ORDER BY r.name ASC")
                .getResultList();
        return routes;
    }
}