package sg.edu.sutd.bank.webapp.model;

public class TransactionCode {
    private User user;
    private String[] transCode;

    public String[] getTransCode() {
        return transCode;
    }
    public void setTransCode(String[] transCode) {
        this.transCode = transCode;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
