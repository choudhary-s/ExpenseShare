package manager;

import entity.Expense;
import entity.ExpenseType;
import entity.Split;
import entity.User;
import service.ExpenseService;

import java.util.*;

public class ExpenseManager {
    Map<Integer, User> userMap;
    List<Expense> expenseList;
    Map<Integer, Map<Integer, Double>> balanceSheet;
    Map<Integer, Map<Integer, List<Double>>> settlementTransaction;

    public ExpenseManager() {
        userMap = new HashMap<>();
        expenseList = new ArrayList<>();
        balanceSheet = new HashMap<>();
        settlementTransaction = new HashMap<>();
    }

    public void addUser(User u){
        userMap.put(u.getId(), u);
        balanceSheet.put(u.getId(), new HashMap<>());
    }

    public void addExpense(ExpenseType type, User paidBy, double amount, List<Split> splits) throws Exception {
        Expense expense = ExpenseService.createExpense(type, paidBy, amount, splits);
        expenseList.add(expense);

        int paidByUserId = paidBy.getId();
        for(Split s: splits){
            int owedUsersId = s.getUser().getId();
            Map<Integer, Double> bsOfPaidUser = balanceSheet.get(paidByUserId);
            if(!bsOfPaidUser.containsKey(owedUsersId)){
                bsOfPaidUser.put(owedUsersId, 0.0);
            }
            bsOfPaidUser.put(owedUsersId, ExpenseService.roundUpto2(bsOfPaidUser.get(owedUsersId)+s.getAmount()));

            Map<Integer, Double> bsOfOwedUser = balanceSheet.get(owedUsersId);
            if(!bsOfOwedUser.containsKey(paidByUserId)){
                bsOfOwedUser.put(paidByUserId, 0.0);
            }
            bsOfOwedUser.put(paidByUserId, ExpenseService.roundUpto2(bsOfOwedUser.get(paidByUserId)-s.getAmount()));
        }
        System.out.println("Printing balance sheet after update:\n"+balanceSheet);
    }
    public void settlementExpense(User u1, User u2, double amount){
        //add to settlement transaction store.
        //recompute balance sheet.
        if(!settlementTransaction.containsKey(u1.getId())){
            settlementTransaction.put(u1.getId(), new HashMap<>());
        }
        Map<Integer, List<Double>> stOfU1 = settlementTransaction.get(u1.getId());
        if(!stOfU1.containsKey(u2.getId())){
            stOfU1.put(u2.getId(), new ArrayList<>());
        }
        stOfU1.get(u2.getId()).add(amount);


        Map<Integer, Double> bsOfPaidUser = balanceSheet.get(u1.getId());
        if(!bsOfPaidUser.containsKey(u2.getId())){
            bsOfPaidUser.put(u2.getId(), 0.0);
        }
        bsOfPaidUser.put(u2.getId(), ExpenseService.roundUpto2(bsOfPaidUser.get(u2.getId())+amount));

        Map<Integer, Double> bsOfOwedUser = balanceSheet.get(u2.getId());
        if(!bsOfOwedUser.containsKey(u1.getId())){
            bsOfOwedUser.put(u1.getId(), 0.0);
        }
        bsOfOwedUser.put(u1.getId(), ExpenseService.roundUpto2(bsOfOwedUser.get(u1.getId())-amount));
    }
    public void showExpense(User u){
        Map<Integer, Double> bsOfUser = balanceSheet.get(u.getId());
        for(int userId: bsOfUser.keySet()){
            printBalance(u.getId(), userId, bsOfUser.get(userId));
        }
    }
    public void showExpense(){
        Set<Integer> userIds = userMap.keySet();
        for(int userId: userIds){
            User u = userMap.get(userId);
            System.out.println("Printing expense for user "+u.getName());
            showExpense(u);
            System.out.println("----------------------------------------");
        }
    }
    public void printBalance(int u1, int u2, double amount){
        if(u1==u2){
            return;
        }
        String n1 = userMap.get(u1).getName();
        String n2 = userMap.get(u2).getName();
        if(amount<0){
            System.out.println(n1+" owes to "+n2+": "+(amount*-1));
        }
        else{
            System.out.println(n2+" owes to "+n1+": "+amount);
        }
    }
    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public Map<Integer, Map<Integer, Double>> getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(Map<Integer, Map<Integer, Double>> balanceSheet) {
        this.balanceSheet = balanceSheet;
    }
}
