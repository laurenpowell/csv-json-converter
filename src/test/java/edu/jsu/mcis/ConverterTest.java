package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    private String csv;
    private String json;

	@Before
    public void setUp() {
		Converter c = new Converter();
		csv = c.readTheFile("src/test/resources/grades.csv");
        json = c.readTheFile("src/test/resources/grades.json");
	}

    @Test
    public void testConvertCSVtoJSON() {
        assertTrue(Converter.jsonStringsAreEqual(Converter.csvToJson(csv), json));
    }

    @Test
    public void testConvertJSONtoCSV() {
        assertEquals(Converter.jsonToCsv(json), csv);
    }
}




