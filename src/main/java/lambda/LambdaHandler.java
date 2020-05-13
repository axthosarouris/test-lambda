package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import nva.commons.exceptions.ApiGatewayException;
import nva.commons.handlers.ApiGatewayHandler;
import nva.commons.handlers.RequestInfo;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

public class LambdaHandler extends ApiGatewayHandler<Void, Publication> {

    public LambdaHandler() {
        super(Void.class, LoggerFactory.getLogger(LambdaHandler.class));
    }

    @Override
    protected Publication processInput(Void input,String apiGatewayJson, Context context) throws ApiGatewayException {
        logger.info("ApiGatewayMessage:"+apiGatewayJson);
        return super.processInput(input,apiGatewayJson,context);
    }

    @Override
    protected Publication processInput(Void input, RequestInfo requestInfo, Context context)  {
        logger.trace("This is an trace message");
        logger.debug("This is an debug message");
        logger.info("This is an info message");
        logger.warn("This is an warning message");
        logger.warn("This is an error message");

        DynamoService dynamoService = new DynamoService();
        Publication publication = createPublication();
       // dynamoService.insertPublication(publication);
        Publication stored=dynamoService.getPublication(publication.getId(),publication.getOwnerId());

        return stored;

    }

    private Publication createPublication() {
        Publication publication = new Publication();
        publication.setId("og@unit.no");
        publication.setOwnerId("UNIT");
        return publication;
    }

    @Override
    protected Integer getSuccessStatusCode(Void input, Publication output) {
        return HttpStatus.SC_OK;
    }
}
