package service;

import entity.*;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseService {
    public static Expense createExpense(ExpenseType type, User paidBy, double amount, List<Split> splits) throws Exception{
        int totalPax = splits.size();
        switch(type){
            case EQUAL:
                double splitAmount = roundUpto2(amount/totalPax);
                for(Split s: splits){
                    s.setAmount(splitAmount);
                }
                splits.get(0).setAmount(roundUpto2(splitAmount+(amount-totalPax*splitAmount)));
                return new EqualExpense(amount, paidBy, splits);
            case EXACT:
                return new ExactExpense(amount, paidBy, splits);
        }
        return null;
    }
    public static double roundUpto2(double n){
        DecimalFormat df = new DecimalFormat("##.00");
        return Double.parseDouble(df.format(n));
    }
}
