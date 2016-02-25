package com.nobodyiam.spring.cloud.config.demo.client;

import com.nobodyiam.spring.cloud.config.demo.model.EnvironmentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jason on 2/24/16.
 */
@RestController
@RefreshScope
@RequestMapping("/demo")
public class CloudConfigController {
    private static final String APPLICATION_NAME_CONFIG = "spring.application.name";
    private static final String ACTIVE_PROFILE_CONFIG = "spring.profiles.active";
    private static final String LABEL_CONFIG = "spring.cloud.config.label";
    private static final String CONFIG_SERVER_CONFIG = "spring.cloud.config.uri";

    @Autowired
    private Environment env;

    @RequestMapping(value = "/config/{configName}", method = RequestMethod.GET)
    public String queryConfig(@PathVariable String configName) {
        return env.getProperty(configName, "undefined");
    }

    @RequestMapping(value = "/env", method = RequestMethod.GET)
    @ResponseBody
    public EnvironmentConfig queryEnv() {
        return EnvironmentConfig.builder()
                .application(env.getProperty(APPLICATION_NAME_CONFIG))
                .profiles(env.getProperty(ACTIVE_PROFILE_CONFIG))
                .label(env.getProperty(LABEL_CONFIG))
                .serverUri(env.getProperty(CONFIG_SERVER_CONFIG))
                .build();
    }

}