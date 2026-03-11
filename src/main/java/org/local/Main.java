package org.local;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        System.out.println(input);
        return "Hello World! Input was: " + input;
    }
}
