package com.task.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("!test")
public class H2Config {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebConsoleServer() throws SQLException {
        return Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", "8084"
        );
    }
}