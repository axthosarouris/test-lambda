package lambda;

import nva.commons.exceptions.ApiGatewayException;
import org.apache.http.HttpStatus;

public class CristinException extends ApiGatewayException {

    public CristinException(Exception e) {
        super(e);
    }

    public CristinException(String message) {
        super(message);
    }

    @Override
    protected Integer statusCode() {
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }
}
