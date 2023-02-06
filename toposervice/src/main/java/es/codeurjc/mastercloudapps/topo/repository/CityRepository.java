package es.codeurjc.mastercloudapps.topo.repository;

import es.codeurjc.mastercloudapps.topo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, String> {

    @Query("SELECT u FROM City u WHERE u.id = ?1")
    City searchById(String name);
}
