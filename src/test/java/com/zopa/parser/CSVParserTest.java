package com.zopa.parser;

import com.zopa.exception.InputFileParseException;
import com.zopa.model.Lender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVParserTest covers test coverage of Parsing CSVFiles only
 */
@Slf4j
public class CSVParserTest {

    IParser parser;

    @BeforeEach
    public void setup() {
        parser = new CSVParser();
    }

    @Test
    public void givenMultipleCsvFiles_returnsListofLenders() {
        // Arrange
        Map<String, Integer> files = new HashMap<String, Integer>() {{
            put("src/test/resources/input/lenders-test1.csv", 2);
            put("src/test/resources/input/lenders-test2.csv", 7);
            put(null, 0);
        }};
        files.forEach((k, v) -> {
            try {
                // Act
                List<Lender> lenders = parser.getLenders(k);
                // Assert
                Assertions.assertEquals(v, lenders.size());
            } catch (InputFileParseException e) {
                e.printStackTrace();
            }

        });
    }

    @Test
    public void givenMalformedCSVFileWhereStringIsGivenInsteadOfNumber_returnsInputFileParseException() {
        // Arrange and Act
        Assertions.assertThrows(NumberFormatException.class,
                () -> parser.getLenders("src/test/resources/input/malformed1.csv"));
    }

    @Test
    public void givenMalformedCSVFileWhereCSVValuesAreNotInCorrectFormat_returnsInputFileParseException() {
        // Arrange and Act
        Assertions.assertThrows(NumberFormatException.class,
                () -> parser.getLenders("src/test/resources/input/malformed2.csv"));
    }

    @Test
    public void givenWrongLocationCSVFileWhereCSVValuesAreNotInCorrectFormat_returnsInputFileParseException() {
        // Arrange and Act
        InputFileParseException exceptionThrown = Assertions.assertThrows(InputFileParseException.class,
                () -> parser.getLenders("src/test/resources/malformed2.csv"));
        // Assert
        Assertions.assertTrue(exceptionThrown.getMessage().contains("malformed2.csv"));
    }


}
