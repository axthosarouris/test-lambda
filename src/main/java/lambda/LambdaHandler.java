package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import nva.commons.exceptions.ApiGatewayException;
import nva.commons.handlers.ApiGatewayHandler;
import nva.commons.handlers.RequestInfo;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

public class LambdaHandler extends ApiGatewayHandler<Void, Publication[]> {

    public static final String USER_POOL_ID = "eu-west-1_QzzhX0cnE";
    private static String ID = "110631488785326413564";
    private static String SUB = "156d0693-e6e5-4556-ad3f-cc0c6faf084e";

    public LambdaHandler() {
        super(Void.class, LoggerFactory.getLogger(LambdaHandler.class));
    }

    @Override
    protected Publication[] processInput(Void input, String apiGatewayJson, Context context)
        throws ApiGatewayException {
        logger.info("ApiGatewayMessage:" + apiGatewayJson);
        return super.processInput(input, apiGatewayJson, context);
    }

    @Override
    protected Publication[] processInput(Void input, RequestInfo requestInfo, Context context) {
        logger.trace("This is an trace message");
        logger.debug("This is an debug message");
        logger.info("This is an info message");
        logger.warn("This is an warning message");
        logger.warn("This is an error message");

        //        requestInfo.
        //        AssumeRoleWithWebIdentityRequest request = new AssumeRoleWithWebIdentityRequest()
        //            .withWebIdentityToken(session.getAccessToken())
        //            .withProviderId("graph.facebook.com")
        //            .withRoleArn(ROLE_ARN)
        //            .withRoleSessionName("wifSession");
        //
        //        AssumeRoleWithWebIdentityResult result = sts.assumeRoleWithWebIdentity(request);
        //
        //        Credentials stsCredentials = result.getCredentials();
        //        String subjectFromWIF = result.getSubjectFromWebIdentityToken();
        //        BasicSessionCredentials credentials = new BasicSessionCredentials(stsCredentials.getAccessKeyId(),
        //            stsCredentials.getSecretAccessKey(),
        //            stsCredentials.getSessionToken());
        //
        //
        //
        //        DynamoService dynamoService = new DynamoService(credentials);
        //        Publication publication = createPublication();
        //        dynamoService.insertPublication(publication);
        //
        //
        //        List<Publication> stored = dynamoService.getPublications(ID);
        //        List<Publication> stored2 = dynamoService.getPublications(SUB);
        //
        //        List<Publication> resultList = new ArrayList<>(stored);
        //        resultList.addAll(stored2);
        //        Publication[] result = new Publication[resultList.size()];
        //        resultList.toArray(result);
        return result;
    }



    private Publication createPublication() {
        Publication publication = new Publication();
        publication.setId(ID);
        publication.setOwnerId("UNIT");
        return publication;
    }

    @Override
    protected Integer getSuccessStatusCode(Void input, Publication[] output) {
        return HttpStatus.SC_OK;
    }
}
