package es.codeurjc.mastercloudapps.planner;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import es.codeurjc.mastercloudapps.planner.clients.TopoClient;
import es.codeurjc.mastercloudapps.planner.models.Eoloplant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
public class TopoClientTest {
    private Eoloplant eoloPlant;
    @BeforeEach
    private void init() {
        Eoloplant expected = new Eoloplant(1, "Madrid");
        expected.addPlanning("Flat");
        expected.setProgress(100);

        this.eoloPlant = expected;
    }
    @Pact(consumer = "planner-consumer-rest", provider = "topo-provider")
    public RequestResponsePact getCity(PactDslWithProvider builder) {
        return builder.given("City Madrid exists")
                .uponReceiving("get city with ID Madrid")
                .path("/api/topographicdetails/Madrid")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonBody(object -> {
                    object.stringType("id", "Madrid");
                    object.stringType("landscape", "Flat");
                }).build())
                .toPact();
    }
    @Test
    @PactTestFor(pactMethod = "getCity") // this has to be same string as pact method name
    void getCity_whenCityExist(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
        CompletableFuture<String> landscape = new TopoClient(restTemplate).getLandscape("Madrid");
        landscape.thenAccept(l -> {
            String planning = this.eoloPlant.getPlanning().split("-")[1];
            assertEquals(planning, l);
        });
    }
}
