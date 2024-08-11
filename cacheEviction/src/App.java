import controller.Controller;
import model.DAO.LogDAO;

public class App {
    public static void main(String[] args) throws Exception {
        LogDAO logDAO = new LogDAO();
        if (Controller.realizarLogin()) {
            Controller.showMenu();
        } else {
            System.out.println("\nVolte sempre!");
        }
    }
}
