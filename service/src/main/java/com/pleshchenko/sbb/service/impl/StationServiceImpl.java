package com.pleshchenko.sbb.service.impl;

import com.pleshchenko.sbb.model.model.entity.route.Station;
import com.pleshchenko.sbb.service.dao.interfaces.StationDao;
import com.pleshchenko.sbb.service.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by РОМАН on 07.04.2017.
 */
@Service("stationService")
@Transactional
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao dao;

    @Override
    public List<Station> findAll() {
        List<Station> station = dao.findAll();
        return station;
    }
}