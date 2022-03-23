package com.oraclecrudreactive.oraclereactivecruddbclient;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class OracleDBConfig extends AbstractR2dbcConfiguration {



    @Bean
    public ConnectionFactory connectionFactory() {

        String r2dbcUrl = "r2dbc:oracle://reactivedemo.c61c95ltx6jw.us-east-1.rds.amazonaws.com:1521/ORCL";
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(r2dbcUrl)
                .mutate()
                .option(ConnectionFactoryOptions.USER, "reactiveadmin")
                .option(ConnectionFactoryOptions.PASSWORD, "change1t")
                //.option(ConnectionFactoryOptions.CONNECT_TIMEOUT,30)
                .build());
    }

    /*
    @Bean
    DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .namedParameters(true)
                .build();
    }*/
}
