package com.pleshchenko.sbb.web.controller;

import com.pleshchenko.sbb.model.entity.route.Station;
import com.pleshchenko.sbb.service.dto.other.ParametersForSearch;
import com.pleshchenko.sbb.service.dto.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by РОМАН on 05.04.2017.
 */

@Controller
@RequestMapping("/")
public class HomeController {

//    @Autowired
//    StationService stationService;

    @RequestMapping("/")
    public String goHome(ModelMap model){

//        List<Station> stations  = stationService.findAll();
//        ParametersForSearch parametersForSearch = new ParametersForSearch();
//        model.addAttribute("parametersForSearch",parametersForSearch);
//        model.addAttribute("stations", stationService.findAll());
        return "home";
    }

}

