package model;

public class PaymentModel {
    int id ;
    String methodPayment ;

    public PaymentModel(int id, String methodPayment) {
        this.id = id;
        this.methodPayment = methodPayment;
    }

    public int getId() {
        return id;
    }

    public String getMethodPayment() {
        return methodPayment;
    }
}
