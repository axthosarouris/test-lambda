package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import nva.commons.exceptions.ApiGatewayException;
import nva.commons.handlers.ApiGatewayHandler;
import nva.commons.handlers.RequestInfo;
import nva.commons.utils.JsonUtils;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

public class LambdaHandler extends ApiGatewayHandler<JsonNode,JsonNode> {


    private final URI targetExample = URI.create("https://api.cristin.no/v2/institutions");
    public LambdaHandler() {
        super(JsonNode.class, LoggerFactory.getLogger(LambdaHandler.class));
    }

    @Override
    protected JsonNode processInput(JsonNode input, RequestInfo requestInfo, Context context)
        throws ApiGatewayException {

        HttpRequest request = HttpRequest.newBuilder(targetExample).GET().build();
        HttpResponse<String> response = readDataFromCristin( request);

        JsonNode output = transformResponse(response);

        return output;

    }

    private JsonNode transformResponse(HttpResponse<String> response) throws CristinException {
            return parseCristinResponse(response);
    }

    private JsonNode parseCristinResponse(HttpResponse<String> response) throws CristinException {
        try {
            return JsonUtils.objectMapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new CristinException(e);
        }
    }

    private HttpResponse<String> readDataFromCristin(HttpRequest request) throws CristinException {
        HttpClient client= HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new CristinException("Something went wrong");
        }
        return response;
    }

    @Override
    protected Integer getSuccessStatusCode(JsonNode input, JsonNode output) {
        return HttpStatus.SC_OK;
    }
}
