package model.DAO;

import datastructures.AVL;
import model.entities.Cliente;

public class ClienteDAO {
    private AVL<Cliente> clientes;

    public ClienteDAO() {
        clientes = new AVL<>();
    }

    public void addCliente(Cliente cliente) {
        try {
            getCliente(cliente.getCpf());
            throw new Exception("Cliente já cadastrado");
        } catch (Exception e) {
            clientes.insert(cliente.getCodigo(), cliente);
        }
    }

    public Cliente getCliente(int codigo) throws Exception {
        Cliente cliente = clientes.search(codigo);
        return cliente;
    }

    public Cliente getCliente(String cpf) throws Exception {
        Cliente cliente = clientes.search(Integer.parseInt(cpf));

        throw new Exception("Cliente não encontrado");
    }

    public Cliente removerCliente(int codigo) throws Exception {
        Cliente cliente = clientes.remove(codigo);
        return cliente;
    }

    public void updateCliente(Cliente cliente) {
        clientes.insert(cliente.getCodigo(), cliente);
    }
}
