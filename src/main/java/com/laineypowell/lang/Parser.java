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

        var list = new ArrayList<String>();

        String token;
        while (!(token = tokens.get()).equals("}")) {
            list.addFirst(token);

            tokens.next();
        }

        parseBlock(new Tokens(list));

        tokens.expect("}");

        return new FunctionAst(name);
    }

    public void parseBlock(Tokens tokens) {
        var list = new ArrayList<String>();

        var builder = new StringBuilder();

        while (tokens.hasNext()) {
            var token = tokens.get();
            tokens.next();

            if (token.equals(">")) {
                if (tokens.get().equals("-")) {
                    tokens.next();

                    while (tokens.hasNext() && !tokens.get().equals(",")) {
                        var pType = tokens.get();
                        tokens.next();

                        var pName = tokens.get();
                        tokens.next();

                        builder.append(pName).append(" ").append(pType).append(",");

                        tokens.next();
                    }
                    break;
                }

                continue;
            }

            list.addFirst(token);
        }

        var parameters = builder.toString().split(",");

    }
}
