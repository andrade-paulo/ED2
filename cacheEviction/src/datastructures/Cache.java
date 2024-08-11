package datastructures;

import model.entities.OrdemServico;

public class Cache {
    static final int tamanhoMaximo = 20;
    private OrdemServico[] itens;
    private byte posicaoInicial = 0;
    
    public Cache() {
        itens = new OrdemServico[tamanhoMaximo];
    }

    public void inserir(OrdemServico ordemServico) {
        if (posicaoInicial == tamanhoMaximo - 1) {
            posicaoInicial = 0;  // Rotação da cache para FIFO
        }
        itens[posicaoInicial++] = ordemServico;
    }

    public OrdemServico buscar(int codigo){
        for (OrdemServico ordemServico : itens) {
            if (ordemServico != null && ordemServico.getCodigo() == codigo) {
                return ordemServico;
            }
        }
        
        return null;
    }

    public OrdemServico remover(OrdemServico ordemServico) {
        for (int i = 0; i < tamanhoMaximo; i++) {
            if (itens[i] != null && itens[i].equals(ordemServico)) {
                OrdemServico removido = itens[i];
                itens[i] = null;
                return removido;
            }
        }
        
        return null;
    }

    public void limpar() {
        // Util para debug
        for (int i = 0; i < tamanhoMaximo; i++) {
            itens[i] = null;
        }
    }
}
