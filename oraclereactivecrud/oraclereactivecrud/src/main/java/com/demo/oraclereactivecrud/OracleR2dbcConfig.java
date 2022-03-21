package com.demo.oraclereactivecrud;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

@Configuration
@Slf4j
public class OracleR2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${database.host}")
    private String host;

    @Value("${database.port}")
    private String port;

    @Value("${database.serviceName}")
    private String serviceName;

    @Value("${database.user}")
    private String user;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        String descriptor = "(DESCRIPTION=" +
                "(ADDRESS=(HOST=" + host + ")(PORT=" + port + ")(PROTOCOL=tcp))" +
                "(CONNECT_DATA=(SERVICE_NAME=" + serviceName + ")))";

        log.info("Creating connection factory with descriptor " + descriptor);

        String r2dbcUrl = "r2dbc:oracle://reactivedemo.c61c95ltx6jw.us-east-1.rds.amazonaws.com:1521/ORCL";
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(r2dbcUrl)
                .mutate()
                .option(ConnectionFactoryOptions.USER, user)
                .option(ConnectionFactoryOptions.PASSWORD, "change1t")
                .build());
    }
}
