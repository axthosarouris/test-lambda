package lambda;

import static java.lang.String.format;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.stream.Collectors;
import nva.commons.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamoService {

    private static final String DynamoTable = "Publications";
    private static final Logger logger = LoggerFactory.getLogger(DynamoService.class);
    private  Table table;
    private  AmazonDynamoDB client;
    private  DynamoDB db;

    public DynamoService() {
        try {
            client = AmazonDynamoDBClientBuilder.defaultClient();
             db = new DynamoDB(client);
            table = db.getTable(DynamoTable);
        } catch (ResourceNotFoundException e) {
            logger.error(format("Error: The table \"%s\" can't be found.\n", DynamoTable));
            logger.error("Be sure that it exists and that you've typed its name correctly!");
            throw new IllegalStateException(e);
        }
    }

    public void insertPublication(Publication publication) {

        try {
            String json = JsonUtils.objectMapper.writeValueAsString(publication);
            logger.trace("Attempt to insert json:"+json);
            table.putItem(Item.fromJSON(json));
            logger.trace("Inserted:"+json);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing", e);
            throw new IllegalStateException(e);
        }
    }

    public Publication getPublication(String id,String owner) {

        String json=null;
        try {
            client = AmazonDynamoDBClientBuilder.defaultClient();
            db = new DynamoDB(client);
            table = db.getTable(DynamoTable);
            String keySchema=table.describe().getKeySchema().stream().map(KeySchemaElement::toString)
                .collect(Collectors.joining(","));
            logger.info("keySchema"+keySchema);

            GetItemOutcome body = table.getItemOutcome("id", id,"owner_id",owner);
            json = body.getItem().toJSON();
            Publication publication = JsonUtils.objectMapper.readValue(json, Publication.class);
            return publication;
        } catch (AmazonServiceException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            logger.error("Error while deserialising json string:"+json,e);
            throw new  IllegalStateException(e);
        }
    }

}
