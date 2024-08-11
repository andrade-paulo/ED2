package model.DAO;

import java.io.*;

import datastructures.AVL;
import datastructures.Cache;
import model.entities.OrdemServico;

public class OrdemServicoDAO {
    private AVL<OrdemServico> ordensServico;
    private Cache cache;
    private int tamanho;
    
    private LogDAO logDAO = new LogDAO();

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
        cache.inserir(ordemServico);
        
        logDAO.addLog("[CACHE + AVL INSERT] Ordem de Serviço " + ordemServico.getCodigo() + " adicionada");
        updateArquivo();
    }

    public OrdemServico getOrdemServico(int codigo) throws Exception {
        // Não há necessidade de consultar o arquivo, pois sempre vai estar atualizado
        OrdemServico ordemServico = cache.buscar(codigo);

        if (ordemServico == null) {
            logDAO.addLog("[CACHE MISS] Ordem de Serviço " + codigo + " não encontrada em cache");

            ordemServico = ordensServico.search(codigo);
            
            if (ordemServico == null) {
                logDAO.addLog("[AVL MISS] Ordem de Serviço " + codigo + " não encontrada na árvore");
                throw new Exception("Ordem de Serviço não encontrada");
            }

            // Insere na cache
            cache.inserir(ordemServico);
            logDAO.addLog("[CACHE INSERT] Ordem de Serviço " + ordemServico.getCodigo() + " inserida em cache");
        } else {
            logDAO.addLog("[CACHE HIT] Ordem de Serviço " + codigo + " encontrada em cache");
            return ordemServico;
        }
        
        logDAO.addLog("[AVL HIT] Ordem de Serviço " + ordemServico.getCodigo() + " encontrada");
        return ordemServico;
    }

    public OrdemServico removerOrdemServico(int codigo) throws Exception {
        OrdemServico ordemServico = ordensServico.remove(codigo);
        
        if (ordemServico != null) {
            cache.remover(ordemServico);
        }

        updateArquivo();
        
        logDAO.addLog("Ordem de Serviço " + codigo + " removida");
        return ordemServico;
    }

    public void updateOrdemServico(OrdemServico ordemServico) {
        ordensServico.insert(ordemServico.getCodigo(), ordemServico);
        cache.inserir(ordemServico);

        updateArquivo();
        logDAO.addLog("Ordem de Serviço " + ordemServico.getCodigo() + " atualizada");
    }

    public void clearCache() {
        cache.limpar();
    }

    public void updateArquivo() {
        // Abrir arquivo binário "database.dat" e atualizar com as OSs da AVL
        try {
            FileOutputStream fileOut = new FileOutputStream("database.dat");
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
            File file = new File("database.dat");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fileIn = new FileInputStream("database.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                ordensServico = (AVL<OrdemServico>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void list () {
        ordensServico.order();
    }

    public int getTamanho() {
        return tamanho;
    }
}
