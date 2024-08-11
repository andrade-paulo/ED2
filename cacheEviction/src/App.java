import model.entities.Cliente;
import model.entities.OrdemServico;

import java.util.Date;

import model.DAO.OrdemServicoDAO;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Teste de DAO\n");

        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
 
        //Criar ordem de serviço
        /*Cliente cliente = new Cliente("Andrade", "222.000.000-00");
        OrdemServico ordemServico = new OrdemServico("Problema no Tablet", "Tablet tá uma bosta", cliente);
        ordemServicoDAO.addOrdemServico(ordemServico);*/

        //ordemServicoDAO.list();
        //System.out.println("\n");
        
        OrdemServico procura = ordemServicoDAO.getOrdemServico(0);
        System.out.println(procura + "\n");

        procura = ordemServicoDAO.getOrdemServico(0);
        System.out.println(procura);
    }
}
