package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import nva.commons.exceptions.ApiGatewayException;
import nva.commons.hanlders.ApiGatewayHandler;
import nva.commons.hanlders.RequestInfo;
import org.apache.http.HttpStatus;

public class LambdaHandler extends ApiGatewayHandler<RequestBody,ResponseBody> {

    public LambdaHandler() {
        super(RequestBody.class);
    }

    @Override
    protected ResponseBody processInput(RequestBody input, RequestInfo requestInfo, Context context)
        throws ApiGatewayException {
        LambdaLogger logger = context.getLogger();
        logger.log("Hello from Logger");
        return new ResponseBody.Builder().answer(input.getQuestion().toUpperCase()).build();
    }

    @Override
    protected Integer getSuccessStatusCode(RequestBody input, ResponseBody output) {
        return HttpStatus.SC_OK;
    }
}
