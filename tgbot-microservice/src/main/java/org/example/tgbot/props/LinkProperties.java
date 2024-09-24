package org.example.tgbot.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public class LinkProperties {
    @Getter
    @Setter
    private String crudMicroserviceUrl;
}

