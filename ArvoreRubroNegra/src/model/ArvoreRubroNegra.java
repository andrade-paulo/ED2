package model;

public class ArvoreRubroNegra {
    No raiz;
    
    public ArvoreRubroNegra() {
        this.raiz = null;
    }

    public ArvoreRubroNegra(No raiz) {
        this.raiz = raiz;
    }

    public void inserir(int chave) {
        No novo = new No();

        novo.pai = novo.esquerda = novo.direita = null;
        novo.chave = chave;
        novo.cor = 1; // Vermelho

        No paiAtual = null;
        No atual = this.raiz;

        // Exame de DNA (descobrir quem é o pai)
        while(atual != null) {
            paiAtual = atual;

            if(novo.chave < atual.chave) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        novo.pai = paiAtual;

        // Descobrir se é filho da esquerda ou da direita
        if (paiAtual == null) {
            this.raiz = novo;
        } else if (novo.chave < paiAtual.chave) {
            paiAtual.esquerda = novo;
        } else {
            paiAtual.direita = novo;
        }

        if (novo.pai == null) {
            novo.cor = 0; // Preto
            return;
        }

        if (novo.pai.pai == null) {
            return;
        }
    }


    public void ajustePosInsercao(No v) {
        No u;

        while(v.pai.cor == 1) {
            // Pai é filho direito do avô
            if (v.pai == v.pai.pai.direita) {
                // Referência do tio
                u = v.pai.pai.esquerda;

                // Se o tio for vermelho
                if (u.cor == 1) {
                    u.cor = 0;
                    v.pai.cor = 0;
                    v.pai.pai.cor = 1;
                    v = v.pai.pai;  // Novo v é o avô
                } else {
                    if (v == v.pai.esquerda) {
                        v = v.pai;
                        rotacaoDireita(v);
                    }

                    v.pai.cor = 0;
                    v.pai.pai.cor = 1;
                    rotacaoEsquerda(v.pai.pai);
                }
            } else {
                u = v.pai.pai.direita;

                if (u.cor == 1) {
                    u.cor = 0;
                    v.pai.cor = 0;
                    v.pai.pai.cor = 1;
                    v = v.pai.pai;
                } else {
                    if (v == v.pai.direita) {
                        v = v.pai;
                        rotacaoEsquerda(v);
                    }

                    v.pai.cor = 0;
                    v.pai.pai.cor = 1;
                    rotacaoDireita(v.pai.pai);
                }
            }

            if (v == this.raiz) {
                break;
            }
        }

        this.raiz.cor = 0;
    }

    private void rotacaoEsquerda(No u) {
        No v = u.direita;
        u.direita = v.esquerda;

        if (v.esquerda != null) {
            v.esquerda.pai = u;
        }

        v.pai = u.pai;

        if (u.pai == null) {
            this.raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }

        v.esquerda = u;
        u.pai = v;
    }

    private void rotacaoDireita(No u) {
        No v = u.esquerda;
        u.esquerda = v.direita;

        if (v.direita != null) {
            v.direita.pai = u;
        }

        v.pai = u.pai;

        if (u.pai == null) {
            this.raiz = v;
        } else if (u == u.pai.direita) {
            u.pai.direita = v;
        } else {
            u.pai.esquerda = v;
        }

        v.direita = u;
        u.pai = v;
    }
}
