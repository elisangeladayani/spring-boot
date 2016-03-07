package br.com.appday.logback;

import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.decorate.JsonGeneratorDecorator;

/**
 * Created by dayani on 07/03/16.
 */
public class JsonGeneratorDecoratorConfig implements JsonGeneratorDecorator {
    @Override
    public JsonGenerator decorate(JsonGenerator jsonGenerator) {
        return jsonGenerator.useDefaultPrettyPrinter();
    }
}
