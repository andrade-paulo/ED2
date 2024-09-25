package model.DAO;

import java.io.*;

import datastructures.Hash;
import datastructures.HashCache;
import model.entities.OrdemServico;
import model.entities.Usuario;

public class OrdemServicoDAO {
    private Hash<OrdemServico> ordensServico;
    private HashCache<OrdemServico> cache;
    private int ocupacao;
    private final int TAMANHO_INICIAL = 5;
    private final String ARQUIVO = "src/database/database.dat"; 

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public OrdemServicoDAO() {
        ordensServico = new Hash<>(TAMANHO_INICIAL);
        carregarArquivo();
        cache = new HashCache();

        if (ordensServico.isEmpty()) {
            OrdemServico.setContador(0);
            ocupacao = 0;
        } else {
            OrdemServico.setContador(ordensServico.getUltimo().getCodigo());
            ocupacao = ordensServico.getOcupacao();
        }
    }

    public void addOrdemServico(OrdemServico ordemServico) {
        ordensServico.inserir(ordemServico.getCodigo(), ordemServico);
        ocupacao++;

        LogDAO.addLog("[DB INSERT] Ordem de Serviço " + ordemServico.getCodigo() + ", ocupação: " + ocupacao + "/" + ordensServico.getTamanho());
        
        updateArquivo();
    }

    public OrdemServico getOrdemServico(int codigo) throws Exception {
        // Não há necessidade de consultar o arquivo, pois sempre vai estar atualizado
        OrdemServico ordemServico = cache.buscar(codigo);

        if (ordemServico == null) {
            LogDAO.addLog("[CACHE MISS] Ordem de Serviço " + codigo + " não encontrada");

            ordemServico = ordensServico.buscar(codigo);
            
            if (ordemServico == null) {
                LogDAO.addLog("[DB MISS] Ordem de Serviço " + codigo + " não encontrada");
                throw new Exception("Ordem de Serviço não encontrada");
            }

            // Insere na cache
            cache.inserir(ordemServico.getCodigo(), ordemServico);
        } else {
            LogDAO.addLog("[CACHE HIT] Ordem de Serviço " + codigo + " encontrada");
            return ordemServico;
        }
        
        LogDAO.addLog("[DB HIT] Ordem de Serviço " + ordemServico.getCodigo() + " encontrada");
        LogDAO.addLog("[CACHE INSERT] " + cache.toString());
        return ordemServico;
    }

    public void removerOrdemServico(int codigo) throws Exception {
        try {
            ordensServico.remover(codigo);
        } catch (Exception e) {
            LogDAO.addLog("[DB MISS] Ordem de Serviço " + codigo + " não encontrada");
            throw new Exception("Ordem de Serviço não encontrada");
        }
        
        ocupacao--;
        LogDAO.addLog("[DB REMOVE] Ordem de Serviço " + codigo + " removida" + ", ocupação: " + ocupacao + "/" + ordensServico.getTamanho());
        
        if (cache.buscar(codigo) != null) {
            cache.remover(codigo);
            LogDAO.addLog("[CACHE REMOVE] " + cache.toString());
        }
        
        updateArquivo();
    }

    public void updateOrdemServico(OrdemServico ordemServico) {
        ordensServico.inserir(ordemServico.getCodigo(), ordemServico);

        // Atualiza apenas se a Ordem de Serviço já estiver na cache
        if (cache.buscar(ordemServico.getCodigo()) != null) {
            cache.inserir(ordemServico.getCodigo(), ordemServico);
            LogDAO.addLog("[CACHE UPDATE] Chave " + ordemServico.getCodigo() + " atualizada");
        }

        updateArquivo();
    }

    public void clearCache() {
        cache.limpar();
    }

    public void updateArquivo() {
        // Abrir arquivo binário "database.dat" e atualizar com as OSs da Hash
        try {
            FileOutputStream fileOut = new FileOutputStream(ARQUIVO);
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
        // Carregar arquivo binário "database.dat" e preencher Hash com as OSs
        try {
            File file = new File(ARQUIVO);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fileIn = new FileInputStream(ARQUIVO);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                ordensServico = (Hash<OrdemServico>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void listarTodosOS() {
        ordensServico.mostrarValores();
    }

    public void listarOS(Usuario usuario) {
        for (OrdemServico ordemServico : ordensServico) {
            if ((ordemServico != null) && ordemServico.getUsuario().equals(usuario)) {
                System.out.println(ordemServico + "\n---------------------------------");
            }
        }
    }

    public int getOcupacao() {
        return ocupacao;
    }
}
