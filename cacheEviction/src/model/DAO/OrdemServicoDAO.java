package model.DAO;

import datastructures.AVL;
import datastructures.Cache;
import model.entities.OrdemServico;

public class OrdemServicoDAO {
    private AVL<OrdemServico> ordensServico;
    private Cache cache;

    public OrdemServicoDAO() {
        ordensServico = new AVL<>();
        cache = new Cache();
    }

    public void addOrdemServico(OrdemServico ordemServico) {
        ordensServico.insert(ordemServico.getCodigo(), ordemServico);
        cache.inserir(ordemServico);

        // Abrir arquivo binário e inserir a nova OS
    }

    public OrdemServico getOrdemServico(int codigo) throws Exception {
        // Não há necessidade de consultar o arquivo, pois sempre vai estar atualizado
        OrdemServico ordemServico = cache.buscar(codigo);
        if (ordemServico == null) {
            ordemServico = ordensServico.search(codigo);
            if (ordemServico != null) {
                cache.inserir(ordemServico);
            }
        }
        return ordemServico;
    }

    public OrdemServico removerOrdemServico(int codigo) throws Exception {
        OrdemServico ordemServico = ordensServico.remove(codigo);
        if (ordemServico != null) {
            cache.remover(ordemServico);
        }
        return ordemServico;

        // Abrir arquivo binário para remover a OS
    }

    public void updateOrdemServico(OrdemServico ordemServico) {
        ordensServico.insert(ordemServico.getCodigo(), ordemServico);
        cache.inserir(ordemServico);

        // Abrir arquivo binário para atualizar a OS
    }

    public void clearCache() {
        cache.limpar();
    }
}
