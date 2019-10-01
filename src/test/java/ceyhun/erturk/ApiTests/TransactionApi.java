package ceyhun.erturk.ApiTests;

import ceyhun.erturk.InıtTest;
import ceyhun.erturk.controller.TransactionController;
import ceyhun.erturk.globals.ControllerFactory;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.model.Transaction;
import ceyhun.erturk.util.CurrencyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class TransactionApi {

    protected ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static final InıtTest inıtTest = new InıtTest();


    @Test
    public void testTransaction() throws IOException {
        Long accountId1 = 19L;
        Double initialAmount1 = 1000D;
        Long accountId2 = 28L;
        Double initialAmount2 = 1000D;
        Double transferAmount = 500D;

        Transaction transaction

        accountService.createAccount(accountId1,initialAmount1);
        accountService.createAccount(accountId2,initialAmount2);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        Assert.assertEquals((initialAmount1 - transferAmount),accountService.getCurrentBalance(accountId1),0);
        Assert.assertEquals((initialAmount2 + transferAmount),accountService.getCurrentBalance(accountId2),0);
    }

}
