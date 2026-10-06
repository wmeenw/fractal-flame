package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ParamParserTest {

    @Test
    void parseAffineParamsShouldReturnCorrectList() {
        String raw = "0.5,0,0,0,0.5,0/0.3,1.0,-0.2,0.4,1.0,1.0";
        List<AffineParams> list = ParamParser.parseAffineParams(raw);
        assertEquals(2, list.size());
        AffineParams first = list.get(0);
        assertEquals(0.5, first.getA());
        assertEquals(0.0, first.getB());
        assertEquals(0.0, first.getC());
        assertEquals(0.0, first.getD());
        assertEquals(0.5, first.getE());
        assertEquals(0.0, first.getF());
        AffineParams second = list.get(1);
        assertEquals(0.3, second.getA());
        assertEquals(1.0, second.getB());
        assertEquals(-0.2, second.getC());
        assertEquals(0.4, second.getD());
        assertEquals(1.0, second.getE());
        assertEquals(1.0, second.getF());
    }

    @Test
    void parseAffineParamsShouldThrowOnInvalidBlock() {
        String raw = "0.5,0,0,0,0.5"; // only 5 numbers
        assertThrows(IllegalArgumentException.class, () -> ParamParser.parseAffineParams(raw));
    }

    @Test
    void parseFunctionsShouldReturnCorrectList() {
        String raw = "swirl:1.0,horseshoe:0.8,linear:0.5";
        List<Config.VariationConfig> list = ParamParser.parseFunctions(raw);
        assertEquals(3, list.size());
        assertEquals("swirl", list.get(0).getName());
        assertEquals(1.0, list.get(0).getWeight());
        assertEquals("horseshoe", list.get(1).getName());
        assertEquals(0.8, list.get(1).getWeight());
        assertEquals("linear", list.get(2).getName());
        assertEquals(0.5, list.get(2).getWeight());
    }

    @Test
    void parseFunctionsShouldThrowOnInvalidPair() {
        String raw = "swirl:1.0,horseshoe"; // missing weight
        assertThrows(IllegalArgumentException.class, () -> ParamParser.parseFunctions(raw));
    }

    @Test
    void parseAffineParamsEmptyStringReturnsEmptyList() {
        List<AffineParams> list = ParamParser.parseAffineParams("");
        assertTrue(list.isEmpty());
    }

    @Test
    void parseFunctionsEmptyStringReturnsEmptyList() {
        List<Config.VariationConfig> list = ParamParser.parseFunctions("");
        assertTrue(list.isEmpty());
    }
}