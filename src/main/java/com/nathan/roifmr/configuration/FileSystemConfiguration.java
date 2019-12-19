package com.nathan.roifmr.configuration;

import com.nathan.roifmr.persistence.QuoteFilePersistence;
import com.nathan.roifmr.persistence.QuotePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class FileSystemConfiguration {

/*
    need been hold our directory, provide access for client objects
 */
    @Bean
    public QuotePersistence buildQuotePersistenceBean() throws FileNotFoundException {
        return new QuoteFilePersistence();
    }
}
