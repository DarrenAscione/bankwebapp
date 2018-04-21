package sg.edu.sutd.bank.webapp.servlet;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Spy;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;

public class DefaultServletTest {

    private DefaultServlet defaultServlet;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void run() {
        defaultServlet = new DefaultServlet();
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void sendError() {
        String msg = "error";
        defaultServlet.sendError(request, msg);
        verify(request.getSession(), times(1)).getAttribute(msg);

    }

    @Test
    public void getUserId() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user_id")).thenReturn(12);

        // calling function to test
        int result = defaultServlet.getUserId(request);
        assertEquals(result, 12);
    }

}