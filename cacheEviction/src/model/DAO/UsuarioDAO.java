package model.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datastructures.AVL;
import model.entities.Usuario;

public class UsuarioDAO {
    private AVL<Usuario> usuarios;

    public UsuarioDAO() {
        usuarios = new AVL<>();
        carregarArquivo();
    }

    public void addCliente(Usuario usuario) {
        try {
            getCliente(usuario.getCpf());
            throw new Exception("Cliente já cadastrado");
        } catch (Exception e) {
            usuarios.insert(usuario.getCodigo(), usuario);
            updateArquivo();
        }
    }

    public Usuario getCliente(int codigo) throws Exception {
        Usuario usuario = usuarios.search(codigo);
        return usuario;
    }

    public Usuario getCliente(String cpf) throws Exception {
        Usuario usuario = usuarios.search(Integer.parseInt(cpf));
        
        if (usuario == null) {
            throw new Exception("Cliente não encontrado");
        }
        
        return usuario;
    }

    public Usuario removerCliente(int codigo) throws Exception {
        Usuario usuario = usuarios.remove(codigo);

        if (usuario == null) {
            throw new Exception("Cliente não encontrado");
        }

        updateArquivo();
        return usuario;
    }

    public void updateCliente(Usuario usuario) {
        usuarios.insert(usuario.getCodigo(), usuario);
        updateArquivo();
    }

    public void updateArquivo() {
        try {
            FileOutputStream fileOut = new FileOutputStream("usuarios.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(usuarios);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarArquivo() {
        try {
            File file = new File("usuarios.dat");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fileIn = new FileInputStream("usuarios.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                usuarios = (AVL<Usuario>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void listarUsuarios() {
        usuarios.order();
    }
}
