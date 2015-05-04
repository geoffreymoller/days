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
    public void loginHandlerRedirectsAfterPost() {

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        Response response = client.target(
                String.format("http://localhost:%d/api/v1/day/1/1430781196522", RULE.getLocalPort()))
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }
}
