package controller;

import java.util.Scanner;

import model.DAO.UsuarioDAO;
import model.DAO.OrdemServicoDAO;
import model.entities.Usuario;
import model.entities.OrdemServico;

public class Controller {
    private static Usuario usuario;
    private static OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static Scanner scanner = new Scanner(System.in);

    public Controller() {}

    public static boolean realizarLogin() {
        limparTela();

        System.out.println(Color.CYAN + "Bem-vindo ao sistema de Ordem de Serviço!" + Color.RESET);

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu CPF (apenas números): ");
        String cpf = scanner.nextLine();

        try {
            usuario = usuarioDAO.getCliente(cpf);
            
            if (!usuario.getNome().equals(nome)) {
                System.out.println(Color.RED + "Oops! As credenciais informadas não conferem." + Color.RESET);
                return realizarLogin();
            }

        } catch (Exception e) {
            System.out.print("Cliente não encontrado. Deseja se cadastrar? (s/n): ");

            String resposta = scanner.nextLine();
            
            if (resposta.equals("s") || resposta.equals("S")) {
                usuario = new Usuario(nome, cpf);
                usuarioDAO.addCliente(usuario);
                
                System.out.println(Color.GREEN + "Cliente cadastrado com sucesso!" + Color.RESET);
            } else {
                System.out.println("Saindo do programa...");
                scanner.close();
                return false;
            }
        }

        return true;
    }

    public static void showMenu() {
        limparTela();
        int escolha = 0;
        
        do {
            System.out.println(Color.CYAN + "Menu principal - " + usuario.getNome() + Color.RESET);
            System.out.println("1. Cadastrar Ordem de Serviço");
            System.out.println("2. Listar minhas Ordens de Serviço");
            System.out.println("3. Listar todas as Ordens de Serviço");
            System.out.println("4. Buscar Ordem de Serviço");
            System.out.println("5. Remover Ordem de Serviço");
            System.out.println("6. Atualizar Ordem de Serviço");
            System.out.println("7. Logout");
            System.out.println("0. Sair do sistema");
            System.out.print("Escolha uma opção: ");
            
            escolha = scanner.nextInt();
            scanner.nextLine();
            
            switch (escolha) {
                case 1:
                    cadastrarOS();
                    break;
                case 2:
                    listarMeusOS();
                    break;
                case 3:
                    listarTodosOS();
                    break;
                case 4:
                    buscarOS();
                    break;
                case 5:
                    removerOS();
                    break;
                case 6:
                    atualizarOS();
                    break;
                case 7:
                    usuario = null;
                    realizarLogin();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println(Color.RED + "Opção inválida. Tente novamente." + Color.RESET);
                    break;
            }
            
            System.out.println();
        } while (escolha != 0);

        scanner.close();
    }

    private static void cadastrarOS() {
        limparTela();

        System.out.print("Título do Serviço: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição do Serviço: ");
        String descricao = scanner.nextLine();

        OrdemServico ordemServico = new OrdemServico(titulo, descricao, usuario);
        ordemServicoDAO.addOrdemServico(ordemServico);

        System.out.println(Color.GREEN + "Ordem de Serviço cadastrada com sucesso!" + Color.RESET);
    }

    private static void buscarOS() {
        limparTela();
        
        System.out.print("Código da Ordem de Serviço: ");
        int codigo = scanner.nextInt();

        try {
            OrdemServico ordemServico = ordemServicoDAO.getOrdemServico(codigo);
            System.out.println("\n" + ordemServico + "\n---------------------------------");
        } catch (Exception e) {
            System.out.println(Color.RED + "Oops! Ordem de Serviço não encontrada." + Color.RESET);
        }
    }

    private static void removerOS() {
        limparTela();
        
        System.out.print("Código da Ordem de Serviço: ");
        int codigo = scanner.nextInt();

        try {
            ordemServicoDAO.removerOrdemServico(codigo);
            System.out.println(Color.GREEN + "Ordem de Serviço " + codigo + " removida com sucesso!" + Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED + "Oops! Ordem de Serviço não encontrada." + Color.RESET);
        }
    }

    private static void atualizarOS() {
        limparTela();
        
        System.out.print("Código da Ordem de Serviço: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        try {
            OrdemServico ordemServico = ordemServicoDAO.getOrdemServico(codigo);
            System.out.println("Deixe em branco para manter o valor atual.");

            System.out.print("Título do Serviço: ");
            String titulo = scanner.nextLine();

            System.out.print("Descrição do Serviço: ");
            String descricao = scanner.nextLine();
            
            if (!titulo.isEmpty()) {
                ordemServico.setTitulo(titulo);
            }

            if (!descricao.isEmpty()) {
                ordemServico.setDescricao(descricao);
            }

            ordemServicoDAO.updateOrdemServico(ordemServico);
            System.out.println(Color.GREEN + "Ordem de Serviço atualizada com sucesso!" + Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED + "Oops! Ordem de Serviço não encontrada." + Color.RESET);
        }
    }

    private static void listarMeusOS() {
        limparTela();
        System.out.println(Color.CYAN + "Minhas Ordens de Serviço:" + Color.RESET);

        ordemServicoDAO.listarOS(usuario);
    }

    public static void listarTodosOS() {
        limparTela();

        if (ordemServicoDAO.getTamanho() == 1) {
            System.out.println(Color.CYAN + 1 + " Ordem de Serviço encontrada:\n" + Color.RESET);
        } else {
            System.out.println(Color.CYAN + ordemServicoDAO.getTamanho() + " Ordens de Serviço encontradas:\n" + Color.RESET);
        }

        ordemServicoDAO.listarTodosOS();
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
    }
}
