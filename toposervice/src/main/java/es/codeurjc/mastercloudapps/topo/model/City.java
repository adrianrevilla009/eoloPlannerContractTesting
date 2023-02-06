package es.codeurjc.mastercloudapps.topo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uuid;

    private String id;
    private String landscape;

    protected City() {
        // Used by SpringData
    }

    public City(String landscape) {
        this(null, landscape);
    }

    public City(String id, String landscape) {
        this.id = id;

        this.landscape = landscape;
    }

    public String getId() {
        return id;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", landscape='" + landscape + '\'' +
                '}';
    }
}