package com.laineypowell.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class LangTest {

    @Test
    public void test() throws IOException {
        new Parser().parseClass(Tokens.read(Paths.get("src/test/resources/Example.lan")));
    }
}
