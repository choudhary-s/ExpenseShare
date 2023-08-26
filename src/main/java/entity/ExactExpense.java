package entity;

import java.util.List;

public class ExactExpense extends Expense{

    public ExactExpense(double amount, User paidBy, List<Split> splits) throws Exception{
        super(amount, paidBy, splits);
        boolean isValid = validate();
        if(!isValid){
            throw new Exception("Invalid Exact Expense");
        }
    }
    @Override
    public boolean validate() {
        double totalAmount = getAmount();
        double splitAmount = getSplitList().stream().mapToDouble(split -> split.getAmount()).sum();
        return splitAmount==totalAmount;
    }
}
