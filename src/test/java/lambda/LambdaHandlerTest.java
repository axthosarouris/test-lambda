package lambda;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;

import com.amazonaws.services.lambda.runtime.Context;
import nva.commons.handlers.RequestInfo;
import org.junit.jupiter.api.Test;

class LambdaHandlerTest {


    @Test
    public void lambdaHandlerHasDefaultConstructor(){
        LambdaHandler handler= new LambdaHandler();
        RequestInfo requestInfo = new RequestInfo();
        Context context = mock(Context.class);
        String output=handler.processInput(null,requestInfo,context);
        assertThat(output,is(not(nullValue())));

    }






}