package com.smartrecruiters.earthquakemngr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;

import mil.nga.sf.geojson.FeatureCollection;
import mil.nga.sf.geojson.FeatureConverter;

@Service
public class EarthquakeMngrServiceImpl implements IEarthquakeMngrService{
	
	public static final String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

	@Override
	public Map<String, Long> getInfo(Double x, Double y) {
		HashMap<String, Long> map          = new HashMap<String, Long>();
		Map<String, Long>     sortedNewMap = new HashMap<String, Long>();
		
		try {
			InputStream    is       = new URL(URL).openStream();
			BufferedReader rd       = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String         jsonText = readAll(rd);
			
			FeatureCollection featureCollection = FeatureConverter.toFeatureCollection(jsonText);
			featureCollection.getFeatures().forEach(iterator -> {
				Double x2 = iterator.getFeature().getGeometry().getCentroid().getX();
				Double y2 = iterator.getFeature().getGeometry().getCentroid().getY();
				
				Long distance = (long) SloppyMath.haversinKilometers(x, y, x2, y2);
				
				if(!map.containsValue(distance)) {
					map.put(iterator.getFeature().getProperties().get("title").toString(), distance);
				}
			});
			
			sortedNewMap = map.entrySet().stream().sorted((e1,e2)->
	                Double.compare(e1.getValue(), e2.getValue()))
					.limit(10)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new)); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sortedNewMap;
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

}
