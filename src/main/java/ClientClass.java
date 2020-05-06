import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ClientClass {

    private HttpClient client;
    private Gson gson;
    private Properties props = new Properties();
    private final String pathToProperties= "src/main/resources/request.properties";
    public static final String RESPONSE_OK_MESSAGE = "TYPE_AAA";
    public static final String RESPONSE_BAD_REQUEST_MESSAGE = "TYPE_XXX";
    public static final String RESPONSE_SERVER_ERROR_MESSAGE = "TYPE_YYY";

    private String active;

    final static Logger logger = Logger.getLogger(ClientClass.class);

    ClientClass() {
        client = HttpClients.createDefault();
        gson = new Gson();
        try {
            props.load(new FileInputStream(pathToProperties));
        } catch (IOException ioEx) {
            logger.info("Something is wrong with file. Message: " + ioEx);
        }
        active = props.getProperty("active");
    }

    public URI buildURI(String requestType) throws URISyntaxException {
        try {
            String apiPath = "";
            switch(requestType.toLowerCase()) {
                case "login": apiPath = props.getProperty("login");
                        break;
                case "register": apiPath = props.getProperty("register");
                        break;
                default: logger.info("Unknown path requested.");
            }
            URI uri = new URIBuilder()
                    .setScheme(props.getProperty("protocol"))
                    .setHost(props.getProperty("host"))
                    .setPath(apiPath)
                    .build();
            return uri;
        } catch (URISyntaxException uriEx) {
            logger.info("Syntax wrong at URI. Rethrowing exception...Message: " + uriEx.getMessage());
            throw uriEx;
        }
    }

    public HttpResponse postRequestRegister(URI uri, RegisterData registerData) throws IOException {
        HttpPost postRequest = new HttpPost(uri);
        String requestBody = gson.toJson(registerData);
        logger.info("This is the request body: " + requestBody);
        HttpEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        postRequest.setEntity(stringEntity);
        return client.execute(postRequest);
    }

    public HttpResponse postRequestLogin(URI uri, String username, String password) throws IOException {
        HttpPost postRequest = new HttpPost(uri);
        postRequest.setHeader("X-Auth-Username", username);
        postRequest.setHeader("X-Auth-Password", password);
        postRequest.setHeader("Push-Token", "null");
        postRequest.setHeader("Phone-Type", "ANDROID");
        return client.execute(postRequest);
    }

    public String getResponseMessage(String response, int statusCode) throws IOException {
        String responseMessage;
        int pos = response.indexOf(":") + 2;
        switch (statusCode) {
            case 200: responseMessage = RESPONSE_OK_MESSAGE;
                    break;
            default: responseMessage = response.substring(pos, pos + RESPONSE_OK_MESSAGE.length());
        }
        return responseMessage;
    }

    public String getExpectedMessageByCode(int statusCode) {
        switch (statusCode) {
            case 200:
                return RESPONSE_OK_MESSAGE;
            case 201:
                return RESPONSE_OK_MESSAGE;
            case 400:
                return RESPONSE_BAD_REQUEST_MESSAGE;
            case 401:
                return RESPONSE_BAD_REQUEST_MESSAGE;
            case 500:
                return RESPONSE_SERVER_ERROR_MESSAGE;
            default:
                return RESPONSE_SERVER_ERROR_MESSAGE;
        }
    }

    public String getActive() {
        return active;
    }
}
