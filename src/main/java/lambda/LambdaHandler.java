package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import java.util.stream.Collectors;
import nva.commons.handlers.ApiGatewayHandler;
import nva.commons.handlers.RequestInfo;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

public class LambdaHandler extends ApiGatewayHandler<String, String> {

    public LambdaHandler() {
        super(String.class, LoggerFactory.getLogger(LambdaHandler.class));
    }

    @Override
    protected String processInput(String input, RequestInfo requestInfo, Context context)  {
        logger.trace("This is an trace message");
        logger.debug("This is an debug message");
        logger.info("This is an info message");
        logger.warn("This is an warning message");
        logger.warn("This is an error message");
        return parameterPathsToString(requestInfo);
    }

    private String parameterPathsToString(RequestInfo requestInfo) {
        return requestInfo.getPathParameters().entrySet()
            .stream().map(e->String.format("%s->%s",e.getKey(),e.getValue()))
        .collect(Collectors.joining(","));
    }

    @Override
    protected Integer getSuccessStatusCode(String input, String output) {
        return HttpStatus.SC_OK;
    }
}
