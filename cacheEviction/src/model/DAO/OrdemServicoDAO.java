package model.DAO;

import java.io.*;

import datastructures.AVL;
import datastructures.Cache;
import model.entities.OrdemServico;
import model.entities.Usuario;

public class OrdemServicoDAO {
    private AVL<OrdemServico> ordensServico;
    private Cache cache;
    private int tamanho;

    public OrdemServicoDAO() {
        ordensServico = new AVL<>();
        carregarArquivo();
        cache = new Cache();

        if (ordensServico.isEmpty()) {
            OrdemServico.setContador(0);
            tamanho = 0;
        } else {
            OrdemServico.setContador(ordensServico.getLast().getCodigo());
            tamanho = ordensServico.countNodes();
        }
    }

    public void addOrdemServico(OrdemServico ordemServico) {
        ordensServico.insert(ordemServico.getCodigo(), ordemServico);
        LogDAO.addLog("[AVL INSERT] Ordem de Serviço " + ordemServico.getCodigo() + " adicionada na AVL. Altura: " + ordensServico.high());

        cache.inserir(ordemServico);
        LogDAO.addLog("[CACHE INSERT] Ordem de Serviço " + ordemServico.getCodigo() + " adicionada na cache");

        tamanho++;
        updateArquivo();
    }

    public OrdemServico getOrdemServico(int codigo) throws Exception {
        // Não há necessidade de consultar o arquivo, pois sempre vai estar atualizado
        OrdemServico ordemServico = cache.buscar(codigo);

        if (ordemServico == null) {
            LogDAO.addLog("[CACHE MISS] Ordem de Serviço " + codigo + " não encontrada em cache");

            ordemServico = ordensServico.search(codigo);
            
            if (ordemServico == null) {
                LogDAO.addLog("[AVL MISS] Ordem de Serviço " + codigo + " não encontrada na AVL");
                throw new Exception("Ordem de Serviço não encontrada");
            }

            // Insere na cache
            cache.inserir(ordemServico);
            LogDAO.addLog("[CACHE INSERT] Ordem de Serviço " + ordemServico.getCodigo() + " inserida em cache");
        } else {
            LogDAO.addLog("[CACHE HIT] Ordem de Serviço " + codigo + " encontrada em cache");
            return ordemServico;
        }
        
        LogDAO.addLog("[AVL HIT] Ordem de Serviço " + ordemServico.getCodigo() + " encontrada");
        return ordemServico;
    }

    public OrdemServico removerOrdemServico(int codigo) throws Exception {
        OrdemServico ordemServico = ordensServico.remove(codigo);
        LogDAO.addLog("[AVL REMOVE] Ordem de Serviço " + codigo + " removida da AVL. Altura: " + ordensServico.high());

        if (ordemServico == null) {
            LogDAO.addLog("[AVL MISS] Ordem de Serviço " + codigo + " não encontrada na AVL");
            throw new Exception("Ordem de Serviço não encontrada");
        }
        
        cache.remover(ordemServico);
        LogDAO.addLog("[CACHE REMOVE] Ordem de Serviço " + codigo + " removida da cache");
        
        tamanho--;
        updateArquivo();
        
        return ordemServico;
    }

    public void updateOrdemServico(OrdemServico ordemServico) {
        ordensServico.insert(ordemServico.getCodigo(), ordemServico);
        cache.inserir(ordemServico);

        updateArquivo();
        LogDAO.addLog("Ordem de Serviço " + ordemServico.getCodigo() + " atualizada");
    }

    public void clearCache() {
        cache.limpar();
    }

    public void updateArquivo() {
        // Abrir arquivo binário "database.dat" e atualizar com as OSs da AVL
        try {
            FileOutputStream fileOut = new FileOutputStream("cacheEviction/src/database/database.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ordensServico);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarArquivo() {
        // Carregar arquivo binário "database.dat" e preencher AVL com as OSs
        try {
            File file = new File("cacheEviction/src/database/database.dat");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fileIn = new FileInputStream("cacheEviction/src/database/database.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                ordensServico = (AVL<OrdemServico>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void listarTodosOS() {
        ordensServico.order();
    }

    public void listarOS(Usuario usuario) {
        for (OrdemServico ordemServico : ordensServico) {
            if (ordemServico.getUsuario().equals(usuario)) {
                System.out.println(ordemServico + "\n---------------------------------");
            }
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}
