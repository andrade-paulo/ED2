package model.DAO;

import datastructures.AVL;
import model.entities.Cliente;

public class ClienteDAO {
    private AVL<Cliente> clientes;

    public ClienteDAO() {
        clientes = new AVL<>();
    }

    public void addCliente(Cliente cliente) {
        clientes.insert(cliente.getCodigo(), cliente);
    }

    public Cliente getCliente(int codigo) throws Exception {
        Cliente cliente = clientes.search(codigo);
        return cliente;
    }

    public Cliente removerCliente(int codigo) throws Exception {
        Cliente cliente = clientes.remove(codigo);
        return cliente;
    }

    public void updateCliente(Cliente cliente) {
        clientes.insert(cliente.getCodigo(), cliente);
    }
}
