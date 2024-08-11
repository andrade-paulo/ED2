import model.entities.Usuario;
import model.entities.OrdemServico;

import model.DAO.OrdemServicoDAO;
import model.DAO.UsuarioDAO;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Teste de DAO\n");

        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
 
        //Criar ordem de serviço
        Usuario cliente = new Usuario("Henrique", "97140535469");
        OrdemServico ordemServico = new OrdemServico("Problema no Fone", "Fone tá uma bosta", cliente);
        usuarioDAO.addCliente(cliente);
        ordemServicoDAO.addOrdemServico(ordemServico);

        ordemServicoDAO.list();
        System.out.println("\n");

        usuarioDAO.listarUsuarios();
    }
}
