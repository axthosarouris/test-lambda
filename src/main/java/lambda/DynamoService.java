package lambda;

import static java.lang.String.format;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nva.commons.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamoService {

    private static final String DynamoTable = "Publications";
    private static final Logger logger = LoggerFactory.getLogger(DynamoService.class);
    private Table table;
    private AmazonDynamoDB client;
    private DynamoDB db;

    public DynamoService(BasicSessionCredentials credentials) {
        try {
            client = AmazonDynamoDBClientBuilder.standard().build();
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
            logger.trace("Attempt to insert json:" + json);
            table.putItem(Item.fromJSON(json));
            logger.trace("Inserted:" + json);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing", e);
            throw new IllegalStateException(e);
        }
    }

    public List<Publication> getPublications(String id) {
        try {
            client = AmazonDynamoDBClientBuilder.defaultClient();
            db = new DynamoDB(client);
            table = db.getTable(DynamoTable);
            ItemCollection<QueryOutcome> result = table.query("id", id);

            List<String> items = new ArrayList<>();
            for (Item item : result) {
                items.add(item.toJSON());
            }
            ;
            ;

            List<Publication> publications = items.stream().map(this::getPublication).collect(Collectors.toList());
            return publications;
        } catch (AmazonServiceException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private Publication getPublication(String json) {
        try {
            return JsonUtils.objectMapper.readValue(json, Publication.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
