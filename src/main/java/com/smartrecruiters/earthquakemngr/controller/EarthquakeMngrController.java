package com.smartrecruiters.earthquakemngr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartrecruiters.earthquakemngr.service.IEarthquakeMngrService;

@RestController
public class EarthquakeMngrController {
	
	@Autowired
	private IEarthquakeMngrService service;
	
	@RequestMapping(value = "/info/{x}/{y}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Long>> feed(@PathVariable Double x, @PathVariable Double y) {
		return new ResponseEntity<Map<String, Long>>(service.getInfo(x, y), HttpStatus.OK);
	}

}
