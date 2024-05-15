package lk.ijse.controller.model;



public class Payment {
    private String payId;
    private String amount;
    private String method;
    private String paidDate;
    private String status;



    // No-argument constructor
    public Payment() {
    }

    // Constructor with all fields
    public Payment(String payId, String amount,String method, String paidDate, String status ) {
        this.payId = payId;
        this.amount = amount;
        this.status = status;
        this.paidDate = paidDate;
        this.method = method;
    }

    // Getters and setters for payId
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    // Getters and setters for amount
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    // Getters and setters for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters for paidDate
    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    // Getters and setters for method
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payId='" + payId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", paidDate=" + paidDate +
                ", method='" + method + '\'' +
                '}';
    }


}
