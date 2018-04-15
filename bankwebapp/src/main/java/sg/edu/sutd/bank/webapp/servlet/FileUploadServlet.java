package sg.edu.sutd.bank.webapp.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sg.edu.sutd.bank.webapp.commons.Helper;
import sg.edu.sutd.bank.webapp.commons.ServiceException;
import sg.edu.sutd.bank.webapp.model.ClientInfo;
import sg.edu.sutd.bank.webapp.model.ClientTransaction;
import sg.edu.sutd.bank.webapp.model.User;
import sg.edu.sutd.bank.webapp.service.ClientTransactionDAO;
import sg.edu.sutd.bank.webapp.service.ClientTransactionDAOImpl;
import sg.edu.sutd.bank.webapp.service.TransactionCodesDAO;
import sg.edu.sutd.bank.webapp.service.TransactionCodesDAOImp;

import javax.servlet.RequestDispatcher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class FileUploadServlet extends DefaultServlet{

    private static final long serialVersionUID = 1 ;
    private ClientTransactionDAO clientTransactionDAO = new ClientTransactionDAOImpl();
    private TransactionCodesDAO transactionCodesDAO = new TransactionCodesDAOImp();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String file_name = null;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            return;
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List < FileItem > fields = upload.parseRequest(request);
            Iterator < FileItem > it = fields.iterator();
            if (!it.hasNext()) {
                return;
            }
            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    if (file_name == null) {
                        if (fileItem.getFieldName().equals("file_name")) {
                            file_name = fileItem.getString();
                        }
                    }
                } else {
                    if (fileItem.getSize() > 0) {

                        String path = "/Users/darrenng/Desktop/project/bankwebapp/apache-tomcat-9.0.7/webapps/data/";
                        fileItem.write(new File(path + fileItem.getName()));

                        try(BufferedReader br = new BufferedReader(new FileReader(path + fileItem.getName()))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] tokens = line.split(" ");
                                for (int i=0; i < tokens.length; i++) {
                                    tokens[i] = Helper.input_normalizer(tokens[i]);
                                    if (Helper.xss_match(tokens[i])) {
                                        throw new ServerException("XSS Attempt!");
                                    }
                                }
                                try {

                                    ClientTransaction clientTransaction = new ClientTransaction();
                                    ClientInfo clientInfo = new ClientInfo();
                                    User user = new User(getUserId(request));
                                    clientTransaction.setUser(user);
                                    clientInfo.setUser(user);
                                    BigDecimal amount = new BigDecimal(tokens[1]);
                                    clientTransaction.setAmount(amount);
                                    clientTransaction.setTransCode(tokens[0]);
                                    clientTransaction.setToAccountNum(tokens[2]);


                                    if (transactionCodesDAO.validCode(tokens[0],clientInfo.getUser().getId())) {
                                        transactionCodesDAO.updateUsage(tokens[0], clientInfo.getUser().getId());
                                        clientTransactionDAO.create(clientTransaction);
                                    }
                                } catch (ServiceException e) {
                                    sendError(request, e.getMessage());
                                    forward(request, response);
                                }
                            }
                            redirect(response, ServletPaths.CLIENT_DASHBOARD_PAGE);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
