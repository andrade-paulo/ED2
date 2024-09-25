package datastructures;

import java.io.Serializable;
import java.util.Iterator;

public class HashCache<T> implements Serializable, Iterable<T> {
    @SuppressWarnings("hiding")
    private class Node<T> implements Serializable {
        int chave;
        T valor;

        public Node(int chave, T valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    static final int tamanhoMaximo = 20;
    private Node<T>[] tabela;
    private int ocupacao;
     
    @SuppressWarnings("unchecked")
    public HashCache() {
        tabela = new Node[tamanhoMaximo];
        ocupacao = 0;
    }

    private int hash(int chave) {
        return chave % tamanhoMaximo;
    }

    private int hashColisao(int chave, int i) {
        // Função de hash para tratamento de colisões por endereçamento aberto
        // A função somada garante a varredura de toda a tabela
        return (hash(chave) + (i + chave)) % tamanhoMaximo;
    }

    private int aleatorioValido() {
        int posicao = (int) (Math.random() * tamanhoMaximo);
        while (tabela[posicao] == null) {
            posicao = (posicao + 1) % tamanhoMaximo;
        }
        return posicao;
    }

    public void inserir(int chave, T valor) {
        if (ocupacao == tamanhoMaximo) {
            remover(aleatorioValido());
        }

        int posicao = hash(chave);

        if (tabela[posicao] == null) {
            tabela[posicao] = new Node<>(chave, valor);
            ocupacao++;
        } else {
            // Se já existe a chave na posição inicial, atualiza o valor
            if (tabela[posicao].chave == chave) {
                tabela[posicao].valor = valor;
                return;
            }
            
            int i = 1;
            while (tabela[hashColisao(chave, i)] != null) {
                // Se já existe a chave, atualiza o valor
                if (tabela[hashColisao(chave, i)].chave == chave) {
                    tabela[hashColisao(chave, i)].valor = valor;
                    return;
                }
                i++;
            }

            tabela[hashColisao(chave, i)] = new Node<>(chave, valor);
            ocupacao++;
        }
    }

    public T buscar(int chave){
        int posicao = hash(chave);

        if (tabela[posicao] != null && tabela[posicao].chave == chave) {
            return tabela[posicao].valor;
        } else {
            int i = 1;
            while (tabela[hashColisao(chave, i)] != null && tabela[hashColisao(chave, i)].chave != chave) {
                i++;
            }

            if (tabela[hashColisao(chave, i)] != null) {
                return tabela[hashColisao(chave, i)].valor;
            }
        }

        return null;
    }

    public void remover(int chave) {
        int posicao = hash(chave);

        if (tabela[posicao] != null && tabela[posicao].chave == chave) {
            tabela[posicao] = null;
            ocupacao--;
        } else {
            int i = 1;
            while (tabela[hashColisao(chave, i)] != null && tabela[hashColisao(chave, i)].chave != chave) {
                i++;
            }

            if (tabela[hashColisao(chave, i)] != null) {
                tabela[hashColisao(chave, i)] = null;
                ocupacao--;
            }
        }
    }

    public void limpar() {
        // Util para debug
        for (int i = 0; i < tamanhoMaximo; i++) {
            tabela[i] = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < tamanhoMaximo; i++) {
            if (tabela[i] != null) {
                sb.append(tabela[i].chave);
            } else {
                sb.append("null");
            }

            if (i < tamanhoMaximo - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < ocupacao;
            }

            @Override
            public T next() {
                while (tabela[i] == null && i < tamanhoMaximo) {
                    i++;
                }

                if (i >= tamanhoMaximo) {
                    return null;
                }
                
                return tabela[i++].valor;
            }
        };
    }
}
