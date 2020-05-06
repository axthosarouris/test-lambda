package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import java.util.stream.Collectors;
import nva.commons.handlers.ApiGatewayHandler;
import nva.commons.handlers.RequestInfo;
import nva.commons.utils.doi.DoiConverter;
import nva.commons.utils.doi.DoiConverterImpl;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

public class LambdaHandler extends ApiGatewayHandler<Void, String> {

    private DoiConverter doiConverter;
    public LambdaHandler() {
        super(Void.class, LoggerFactory.getLogger(LambdaHandler.class));
        doiConverter = new DoiConverterImpl();
    }

    @Override
    protected String processInput(Void input, RequestInfo requestInfo, Context context)  {
        logger.trace("This is an trace message");
        logger.debug("This is an debug message");
        logger.info("This is an info message");
        logger.warn("This is an warning message");
        logger.warn("This is an error message");
        return "hello from lambda!!";

    }

    private String parameterPathsToString(RequestInfo requestInfo) {
        return requestInfo.getPathParameters().entrySet()
            .stream().map(e->String.format("%s->%s",e.getKey(),e.getValue()))
        .collect(Collectors.joining(","));
    }

    @Override
    protected Integer getSuccessStatusCode(Void input, String output) {
        return HttpStatus.SC_OK;
    }
}
