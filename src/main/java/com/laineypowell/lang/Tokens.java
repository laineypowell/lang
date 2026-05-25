package com.laineypowell.lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Tokens {
    public static final String REGEX = "[a-zA-Z][a-zA-Z0-9]*|\\S";

    private final List<String> tokens;

    private int i;

    public Tokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public String get() {
        return tokens.get(i);
    }

    public boolean hasNext() {
        return i < tokens.size();
    }

    public void next() {
        i++;
    }

    public void expect(String token) {
        var t = tokens.get(i);
        if (t.equals(token)) {
            i++;
            return;
        }

        throw new RuntimeException(String.format("Expected token %s but got %s", token, t));
    }

    public static Tokens read(Path path) throws IOException {
        try (var reader = Files.newBufferedReader(path)) {
            var builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            var matcher = Pattern.compile(REGEX).matcher(builder.toString());

            var tokens = new ArrayList<String>();
            while (matcher.find()) {
                tokens.add(matcher.group());
            }

            return new Tokens(Collections.unmodifiableList(tokens));
        }
    }

}
