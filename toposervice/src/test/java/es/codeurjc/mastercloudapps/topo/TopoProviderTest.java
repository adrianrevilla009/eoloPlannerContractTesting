package es.codeurjc.mastercloudapps.topo;

import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.topo.model.City;
import es.codeurjc.mastercloudapps.topo.repository.CityRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Provider("topo-provider")
@Consumer("planner-consumer-rest")
//@PactBroker(url = "http://pactbroker:9292", authentication = @PactBrokerAuth(username = "pact_workshop", password = "pact_workshop"))
@PactFolder( "./Pacts")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TopoProviderTest {

    @Mock
    private CityRepository cityRepository;


    @State("City Madrid exists")
    public void cityMadridExists() {
        when(cityRepository.findById("Madrid")).thenReturn(Optional.of(createFakeCity("Madrid")));
    }

    private City createFakeCity(String s) {
        // Faker faker = new Faker();
        return new City(s, "Flat");
    }

}
