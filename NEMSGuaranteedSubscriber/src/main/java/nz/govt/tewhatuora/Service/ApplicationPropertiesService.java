package nz.govt.tewhatuora.Service;

import com.solace.messaging.config.SolaceProperties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesService {

  public static Properties loadProperties() throws IOException {
    Properties configuration = new Properties();
    InputStream inputStream =
        ApplicationPropertiesService.class
            .getClassLoader()
            .getResourceAsStream("application.properties");
    if (inputStream == null) {
      throw new IllegalArgumentException("File not found: application.properties");
    } else {
      configuration.load(inputStream);
      inputStream.close();
      return configuration;
    }
  }

  public static Properties setProperties(Properties appProperties) throws IOException {

    Properties properties = new Properties();
    properties.setProperty(
        SolaceProperties.TransportLayerProperties.HOST,
        appProperties.getProperty("nems.broker.host")); // host:port
    properties.setProperty(
        SolaceProperties.ServiceProperties.VPN_NAME,
        appProperties.getProperty("nems.broker.vpn")); // message-vpn
    properties.setProperty(
        SolaceProperties.AuthenticationProperties.SCHEME_BASIC_USER_NAME,
        appProperties.getProperty("nems.broker.username")); // client-username
    properties.setProperty(
        SolaceProperties.AuthenticationProperties.SCHEME_BASIC_PASSWORD,
        appProperties.getProperty("nems.broker.password"));
    properties.setProperty(
        SolaceProperties.TransportLayerProperties.RECONNECTION_ATTEMPTS,
        "20"); // recommended settings
    properties.setProperty(
        SolaceProperties.TransportLayerProperties.CONNECTION_RETRIES_PER_HOST, "5");

    return properties;
  }
}
