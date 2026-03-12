package org.local;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        long start = System.currentTimeMillis();
        long result = 0;
        for (int i = 0; i < 1_000_000_000; i++) {
            result += new Vector(i, i + 1, i + 2).dot(new Vector(i + 3, i + 4, i + 5));
        }
        long duration = System.currentTimeMillis() - start;
        return String.format("result=%d in %dms (input: %s)", result, duration, input);
    }

    private record Vector(long x, long y, long z) {
        long dot(Vector other) {
                return x * other.x + y * other.y + z * other.z;
            }
        }
}
