package com.project.CarRental2.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CarRental2.model.Car;
import com.project.CarRental2.service.BrandCarService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.UploadFile;
import com.project.CarRental2.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/car")
public class CarAPI {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private BrandCarService brandCarService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFile uploadFile;

    @Autowired
    private CarService carService;

    @GetMapping("")
    public List<Car> getCarByAdrress(){
       return null;
    }
}
