package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Expense {
    String id;
    double amount;
    User paidBy;
    List<Split> splitList;

    public abstract boolean validate();

    public Expense(double amount, User paidBy, List<Split> splitList) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitList = splitList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
    }
}
