package com.pleshchenko.sbb.model.service.interfaces;

import com.pleshchenko.sbb.model.entity.Passenger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by РОМАН on 10.04.2017.
 */
@Service("passengerService")
@Transactional
public interface PassengerService {

    Passenger findById(Integer id);
    
    List<Passenger> findAll();
}
