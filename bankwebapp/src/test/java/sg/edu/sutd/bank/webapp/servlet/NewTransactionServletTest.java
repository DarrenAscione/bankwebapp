package sg.edu.sutd.bank.webapp.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import sg.edu.sutd.bank.webapp.model.ClientTransaction;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class NewTransactionServletTest extends Mockito {

    @Test
    public void doPost() throws Exception {
        NewTransactionServlet newTransactionServlet = mock(NewTransactionServlet.class);
        ClientTransaction clientTransaction = new ClientTransaction();
        clientTransaction = spy(clientTransaction);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(newTransactionServlet.getUserId(request)).thenReturn(12);
        when(request.getParameter("amount")).thenReturn("10");
        when(request.getParameter("transcode")).thenReturn("234a9f84:162b932fad7:-7f41");



        newTransactionServlet.doPost(request, response);

        //verify(request, atLeast(1)).getParameter("amount");

    }
}