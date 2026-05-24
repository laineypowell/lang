package com.laineypowell.lang;

import com.laineypowell.lang.ast.Ast;
import com.laineypowell.lang.ast.FunctionAst;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public void parseClass(Tokens tokens) {
        tokens.expect("[");
        tokens.expect("{");

        var functions = new ArrayList<Ast>();

        String token;
        while (!(token = tokens.get()).equals("}")) {
            if (token.equals("df")) {
                tokens.next();
                functions.add(parseFunction(tokens));

                continue;
            }

            tokens.next();
        }

        tokens.expect("}");

        var name = tokens.get();
        tokens.next();

        var list = List.of("add", "take", "get", "set");
        while (tokens.hasNext() && tokens.get().equals(".")) {
            tokens.next();

            var opName = tokens.get();
            if (!list.contains(opName)) {
                throw new RuntimeException("Expected operator name");
            }

            functions.add(parseFunction(tokens));
        }

    }

    public Ast parseFunction(Tokens tokens) {
        var name = tokens.get();
        tokens.next();

        tokens.expect("{");

        var builder = new StringBuilder();

        String token;
        while (!(token = tokens.get()).equals("-")) {
            if (token.equals("@")) {
                tokens.next();
                tokens.expect("void");

                break;
            }

            builder.append(token).append(" ");
            tokens.next();
        }

        var parameters = builder.toString().split(",");

        tokens.expect("-");
        tokens.expect(">");

        tokens.expect("}");

        return new FunctionAst(name);
    }
}
