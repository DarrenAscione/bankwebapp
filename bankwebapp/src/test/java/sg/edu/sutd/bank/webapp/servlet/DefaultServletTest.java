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

    final DefaultServlet defaultServlet = new DefaultServlet();

    @Test
    public void getUserId() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user_id")).thenReturn(12);

        // calling function to test
        int result = defaultServlet.getUserId(request);
        assertEquals(result, 12);
    }

}