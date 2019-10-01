package ceyhun.erturk.ApiTests;

import ceyhun.erturk.InıtTest;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.CurrencyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AccountApi {

    protected ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static final InıtTest inıtTest = new InıtTest();


    @Test
    public void testCreateAccount() throws Exception {
        Account acc = new Account(500, "Ceyhun", "Erturk", CurrencyUtil.CurrencyType.EURO);
        String jsonInString = mapper.writeValueAsString(acc);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut("api/account/create");
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account aAfterCreation = mapper.readValue(jsonString, Account.class);
        assertTrue(aAfterCreation.getAccountUsername().equals("Ceyhun"));
        assertTrue(aAfterCreation.getAccountType() == CurrencyUtil.CurrencyType.EURO);
    }

    @Test
    public void testGetAccount() throws IOException {
        HttpPut request = new HttpPut("api/account/" + 55);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account aAfterCreation = mapper.readValue(jsonString, Account.class);
        assertTrue(aAfterCreation.getAccountType() == CurrencyUtil.CurrencyType.DOLLAR);
    }


}
