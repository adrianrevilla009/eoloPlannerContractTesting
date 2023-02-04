package es.codeurjc.mastercloudapps.topo.service;

import es.codeurjc.mastercloudapps.topo.model.City;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TopoService {

    ConcurrentMap<String, City> cities = new ConcurrentHashMap<>();

    public City getCity(String id) {
        return cities.get(id);
    }
    
    @PostConstruct
    public void init() {

        this.cities.clear();

        this.cities.put("Madrid", new City("Madrid", "Flat"));
        this.cities.put("Barcelona", new City("Barcelona", "Flat"));
    }
}
