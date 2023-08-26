import entity.ExpenseType;
import entity.Split;
import entity.User;
import manager.ExpenseManager;

import java.util.*;

public class Driver {
    public static void main(String[] args) {
        try {
            ExpenseManager em = new ExpenseManager();

            User u1 = new User(1, "Alice");
            User u2 = new User(2, "Bob");
            User u3 = new User(3, "Chris");
            User u4 = new User(4, "Don");
            em.addUser(u1);
            em.addUser(u2);
            em.addUser(u3);
            em.addUser(u4);


            Split s1 = new Split(u1);
            Split s2 = new Split(u2);
            Split s3 = new Split(u3);

            em.addExpense(ExpenseType.EQUAL, u1, 1000, Arrays.asList(s1,s2,s3));
            em.showExpense();

            Split s4 = new Split(u1, 200);
            Split s5 = new Split(u2, 300);
            Split s6 = new Split(u3, 400);

            em.addExpense(ExpenseType.EXACT, u4, 900, Arrays.asList(s4,s5,s6));
            em.showExpense();

            Split s7 = new Split(u1);
            Split s8 = new Split(u3);

            em.addExpense(ExpenseType.EQUAL, u2, 1000, Arrays.asList(s7,s8));
            em.showExpense();

            em.settlementExpense(u1,u2,1000);
            em.showExpense();

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
