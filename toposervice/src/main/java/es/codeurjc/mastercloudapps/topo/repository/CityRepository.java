package es.codeurjc.mastercloudapps.topo.repository;

import es.codeurjc.mastercloudapps.topo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
}
