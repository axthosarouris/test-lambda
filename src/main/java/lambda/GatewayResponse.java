package lambda;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class GatewayResponse implements Serializable {

    private final String body;
    private final Map<String, String> headers;
    private final int statusCode;

    /**
     * Constructor for GatewayResponse.
     *
     * @param body       body of response
     * @param headers    http headers for response
     * @param statusCode status code for response
     */
    public GatewayResponse(String body, Map<String, String> headers, int statusCode) {

        this.statusCode = statusCode;
        this.body = body;
        this.headers = Collections.unmodifiableMap(Map.copyOf(headers));
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
