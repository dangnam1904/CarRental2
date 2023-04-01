package com.project.CarRental2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.CarRental2.model.District;
import com.project.CarRental2.service.DistrictService;

@RestController
public class AjaxController {

	@Autowired
	private DistrictService districtService;
	
//	@GetMapping("/getDistrict/{id}")
//	public  String getDistric(@PathVariable(name="id") int id){
//		 List<District> d= districtService.getAllDistrictByIdProvince(id);
//		 JSONArray.toJSONString(d);
//		System.err.println(JSONArray.toJSONString(d));
//		return JSONArray.toJSONString(d);
//	}
	
	@GetMapping("/getDistrict{id}")
	public List<District> getDistric1(@RequestParam("id") String id){
		int a= Integer.valueOf(id);
		System.err.println(a);
		 List<District> d= districtService.getAllDistrictByIdProvince(a);
		 List<District> newlist= new ArrayList<>();
	
		 for (District district : d) {
			District newDistrict= new District( district.getIdDistrict(),district.getNameDistrict());
			newlist.add(newDistrict);
		
		}
		 
		
		
		return newlist ;
	}
	
	
}
