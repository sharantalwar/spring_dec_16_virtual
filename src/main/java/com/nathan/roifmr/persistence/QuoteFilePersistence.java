package com.nathan.roifmr.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;


public class QuoteFilePersistence implements QuotePersistence {
    private final Logger log = LoggerFactory.getLogger(QuoteFilePersistence.class);

    private final String PATH_SEARCH_LOCALE = "classpath:quote_files/mock/readme.md";
    private String generatedMockFileDirectory;
    private final String FILEEXT = ".json";

    private ObjectMapper jsonMapper;

    public QuoteFilePersistence() throws FileNotFoundException {
        jsonMapper = new ObjectMapper();
        generatedMockFileDirectory = ResourceUtils.getFile(PATH_SEARCH_LOCALE).getParent();
    }

    @Override
    public boolean save(String storageKey, HistoricalQuotes quoteValue) {

        try (BufferedWriter rider = Files.newBufferedWriter(Path.of(generatedMockFileDirectory,(storageKey+FILEEXT)), StandardOpenOption.CREATE)){
            rider.write(jsonMapper.writeValueAsString(quoteValue));
            rider.flush();
            return true;
        } catch (IOException e) {
            log.error(String.format("unable to complete writing %s",storageKey),e);
        }
        return false;
    }

    @Override
    public Optional<HistoricalQuotes> find(String storageKey) {
        /*
        determine if file present, then read json to java object
         */

        try {
            Optional<Path> paths =
                    Files.find(Paths.get(generatedMockFileDirectory), Integer.MAX_VALUE, (path, attributes) ->
                            !attributes.isDirectory() && path.toString().contains(storageKey)).findFirst();
            if (paths.isPresent()){
                HistoricalQuotes rtnObject = jsonMapper.readValue(new File(paths.get().toString()),HistoricalQuotes.class);
                return Optional.of(rtnObject);
            }
        } catch (IOException e) {
            log.error("Failed to find "+storageKey,e);
        }
        return Optional.empty();
    }
}
