package es.codeurjc.mastercloudapps.topo;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.HttpsTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.topo.controller.TopoController;
import es.codeurjc.mastercloudapps.topo.model.City;
import es.codeurjc.mastercloudapps.topo.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Provider("topo-provider")
@Consumer("planner-consumer-rest")
@PactFolder( "./Pacts")
@ExtendWith(MockitoExtension.class)
@PactBroker
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TopoProviderTest {

    @InjectMocks
    private TopoController topoController;

    @Mock
    private CityRepository cityRepository;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void setUp(PactVerificationContext context) {
        Mockito.reset(cityRepository);

        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(topoController);
        context.setTarget(testTarget);
    }

    @State("City Madrid exists")
    public void cityMadridExists() {
        when(cityRepository.findById("Madrid")).thenReturn(Optional.of(createFakeCity("Madrid")));
    }

    private City createFakeCity(String s) {
        // Faker faker = new Faker();
        return new City(s, "Flat");
    }

}
