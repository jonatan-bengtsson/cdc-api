package com.tingcore.cdc.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class IntegrationConfiguration {

  @Component
  @ConfigurationProperties(prefix = "integration.user", ignoreUnknownFields = false)
  public static class UserServiceInformation {
    @NotBlank
    private String hostname;

    public String getHostname() {
      return hostname;
    }

    public void setHostname(final String hostname) {
      this.hostname = hostname;
    }
  }

  @Component
  @ConfigurationProperties(prefix = "integration.asset", ignoreUnknownFields = false)
  public static class AssetServiceInformation {
    @NotBlank
    private String hostname;

    public String getHostname() {
      return hostname;
    }

    public void setHostname(final String hostname) {
      this.hostname = hostname;
    }
  }

}
