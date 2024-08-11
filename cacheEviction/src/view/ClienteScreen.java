package view;

import java.util.Scanner;

import model.DAO.UsuarioDAO;
import model.DAO.OrdemServicoDAO;
import model.entities.Usuario;
import model.entities.OrdemServico;

public class ClienteScreen {
    Usuario usuario;
    OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    UsuarioDAO clienteDAO = new UsuarioDAO();

    public ClienteScreen() {
        // Login do cliente
        System.out.println("Bem-vindo ao sistema de Ordem de Serviço!");
        System.out.println("Digite seu nome: ");
        Scanner scanner = new Scanner(System.in);
        String nome = scanner.nextLine();
        System.out.println("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        
        try {
            usuario = clienteDAO.getCliente(cpf);
        } catch (Exception e) {
            System.out.println("Cliente não encontrado. Deseja se cadastrar? (s/n)");
            String resposta = scanner.nextLine();
    
            if (resposta.equals("s")) {
                usuario = new Usuario(nome, cpf);
                clienteDAO.addCliente(usuario);
                System.out.println("Cliente cadastrado com sucesso!");
                showMenu();
            } else {
                System.out.println("Saindo do programa...");
            }
        }

        scanner.close();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int escolha = 0;
        
        do {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar OS");
            System.out.println("2. Buscar OS");
            System.out.println("3. Listar todos os OS");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            escolha = scanner.nextInt();
            
            switch (escolha) {
                case 1:
                    cadastrarOS();
                    break;
                case 2:
                    buscarOS();
                    break;
                case 3:
                    listarTodosOS();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            
            System.out.println();
        } while (escolha != 0);
        
        scanner.close();
    }

    private void cadastrarOS() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Título do Serviço: ");
        String titulo = scanner.nextLine();

        System.out.println("Descrição do Serviço: ");
        String descricao = scanner.nextLine();

        OrdemServico ordemServico = new OrdemServico(titulo, descricao, usuario);
        ordemServicoDAO.addOrdemServico(ordemServico);

        System.out.println("Ordem de Serviço cadastrada com sucesso!");

        scanner.close();
    }

    private void buscarOS() {
        // Implementação do método de busca de OS
    }

    private void removerOS() {
        // Implementação do método de remoção de OS
    }

    private void listarTodosOS() {
        // Implementação do método de listagem de todas as OS
    }
}
