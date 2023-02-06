package es.codeurjc.mastercloudapps.topo.service;

import es.codeurjc.mastercloudapps.topo.model.City;
import es.codeurjc.mastercloudapps.topo.repository.CityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TopoService {

    ConcurrentMap<String, City> cities = new ConcurrentHashMap<>();

    @Autowired
    private CityRepository cityRepository;

    public City getCity(String id) {
        // return cities.get(id);
        return this.cityRepository.getReferenceById(id);
    }
    
    @PostConstruct
    public void init() {

        this.cityRepository.deleteAll();
        /*this.cities.clear();

        this.cities.put("Madrid", new City("Madrid", "Flat"));
        this.cities.put("Barcelona", new City("Barcelona", "Flat"));*/
        this.cityRepository.save(new City("Madrid", "Flat"));
        this.cityRepository.save(new City("Barcelona", "Flat"));
    }
}
