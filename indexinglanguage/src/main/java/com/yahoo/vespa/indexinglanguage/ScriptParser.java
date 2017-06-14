// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.indexinglanguage;

import com.yahoo.javacc.FastCharStream;
import com.yahoo.vespa.indexinglanguage.expressions.Expression;
import com.yahoo.vespa.indexinglanguage.expressions.ScriptExpression;
import com.yahoo.vespa.indexinglanguage.expressions.StatementExpression;
import com.yahoo.vespa.indexinglanguage.parser.CharStream;
import com.yahoo.vespa.indexinglanguage.parser.IndexingParser;
import com.yahoo.vespa.indexinglanguage.parser.ParseException;
import com.yahoo.vespa.indexinglanguage.parser.TokenMgrError;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen</a>
 */
public final class ScriptParser {

    public static Expression parseExpression(ScriptParserContext config) throws ParseException {
        return parse(config, new ParserMethod<Expression>() {

            @Override
            public Expression call(IndexingParser parser) throws ParseException {
                return parser.root();
            }
        });
    }

    public static ScriptExpression parseScript(ScriptParserContext config) throws ParseException {
        return parse(config, new ParserMethod<ScriptExpression>() {

            @Override
            public ScriptExpression call(IndexingParser parser) throws ParseException {
                return parser.script();
            }
        });
    }

    public static StatementExpression parseStatement(ScriptParserContext config) throws ParseException {
        return parse(config, new ParserMethod<StatementExpression>() {

            @Override
            public StatementExpression call(IndexingParser parser) throws ParseException {
                try {
                    return parser.statement();
                }
                catch (TokenMgrError e) {
                    throw new ParseException(e.getMessage());
                }
            }
        });
    }

    private static interface ParserMethod<T extends Expression> {

        T call(IndexingParser parser) throws ParseException;
    }

    private static <T extends Expression> T parse(ScriptParserContext config, ParserMethod<T> method)
            throws ParseException {
        CharStream input = config.getInputStream();
        IndexingParser parser = new IndexingParser(input);
        parser.setAnnotatorConfig(config.getAnnotatorConfig());
        parser.setDefaultFieldName(config.getDefaultFieldName());
        parser.setLinguistics(config.getLinguistcs());
        try {
            return method.call(parser);
        } catch (ParseException e) {
            if (!(input instanceof FastCharStream)) {
                throw e;
            }
            throw new ParseException(((FastCharStream)input).formatException(e.getMessage()));
        } finally {
            if (parser.token != null && parser.token.next != null) {
                input.backup(parser.token.next.image.length());
            }
        }
    }
}
