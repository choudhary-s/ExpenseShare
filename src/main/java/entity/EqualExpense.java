package entity;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(double amount, User paidBy, List<Split> splits) throws Exception{
        super(amount, paidBy, splits);
        boolean isValid = validate();
        if(!isValid){
            throw new Exception("Invalid Equal Expense");
        }
    }
    @Override
    public boolean validate() {
        return true;
    }
}
