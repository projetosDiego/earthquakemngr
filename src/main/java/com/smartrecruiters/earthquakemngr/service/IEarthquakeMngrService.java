package com.smartrecruiters.earthquakemngr.service;

import java.util.Map;

public interface IEarthquakeMngrService {

	Map<String, Long> getInfo(Double x, Double y);

}
