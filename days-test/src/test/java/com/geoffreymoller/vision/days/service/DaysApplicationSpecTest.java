package com.geoffreymoller.vision.days.service;

import com.geoffreymoller.vision.days.service.configuration.DaysConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class DaysApplicationSpecTest {

    @ClassRule
    public static final DropwizardAppRule<DaysConfiguration> RULE =
            new DropwizardAppRule<>(DaysApplication.class,
                    DaysApplicationSpecTest.class.getResource("days.test.yml").getPath());

    @Test
    public void when_a_non_existent_day_is_requested_return_500() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
        Response response = client.target(
                String.format("http://localhost:%d/api/v1/day/1/2099-10-10", RULE.getLocalPort()))
                .request()
                .get();
        assertThat(response.getStatus()).isEqualTo(500);
    }
}
