package com.pleshchenko.sbb.service.interfaces;

import com.pleshchenko.sbb.model.model.entity.route.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by РОМАН on 06.04.2017.
 */
@Service("scheduleService")
@Transactional
public interface ScheduleService {

    List<Schedule> findAll();

}
