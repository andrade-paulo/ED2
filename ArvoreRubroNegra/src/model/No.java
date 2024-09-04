package model;

public class No {
    int chave;
    int cor;
    No pai;
    No esquerda;
    No direita;

    public No(int chave) {
        this.chave = chave;
        this.cor = 1;
        this.pai = null;
        this.esquerda = null;
        this.direita = null;
    }

    public No() {
        this.chave = 0;
        this.cor = 0;
        this.pai = null;
        this.esquerda = null;
        this.direita = null;
    }
}
