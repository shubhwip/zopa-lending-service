package com.zopa.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.zopa.exception.InputFileParseException;
import com.zopa.model.Lender;
import javafx.scene.chart.BubbleChart;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVParser implements IParser<Lender> {
    @Override
    public List<Lender> getLenders(String fileName) throws InputFileParseException {
        if (fileName == null)
            return new ArrayList<>(0);
        log.info("Parsing CSV File {} contents", fileName);
        List<Lender> lenders = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            reader.skip(1);
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Lender l = new Lender();
                l.setName(lineInArray[0]);
                l.setRate(new BigDecimal(lineInArray[1]));
                l.setAvailable(new BigDecimal(lineInArray[2]));
                lenders.add(l);
            }
        } catch (IOException | CsvException e) {
            log.error("Exception occured in parsing lenders csv file {} --- Message is {}", fileName, e.getStackTrace());
            throw new InputFileParseException("Exception occured while parsing file" + fileName, e);
        }
        for (Lender l : lenders) {
            log.debug("Lender Details : lender {} \n", l);
        }
        return lenders;
    }
}
