public class RedBlackTree {

    private Node raiz;
    private Node nil;

    public RedBlackTree() {
        nil = new Node(0);
        nil.vermelho = false;

        nil.esquerda = nil;
        nil.direita = nil;
        nil.pai = nil;

        raiz = nil;
    }

    public void inserir(int valor) {

        Node novo = new Node(valor);
        novo.esquerda = nil;
        novo.direita = nil;

        Node pai = nil;
        Node atual = raiz;

        while (atual != nil) {
            pai = atual;

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else if (valor > atual.valor) {
                atual = atual.direita;
            } else {
                return;
            }
        }

        novo.pai = pai;

        if (pai == nil) {
            raiz = novo;
        } else if (valor < pai.valor) {
            pai.esquerda = novo;
        } else {
            pai.direita = novo;
        }

        corrigirInsercao(novo);
    }

    private void corrigirInsercao(Node no) {

        while (no.pai.vermelho) {

            if (no.pai == no.pai.pai.esquerda) {

                Node tio = no.pai.pai.direita;

                if (tio.vermelho) {

                    no.pai.vermelho = false;
                    tio.vermelho = false;
                    no.pai.pai.vermelho = true;

                    no = no.pai.pai;

                } else {

                    if (no == no.pai.direita) {
                        no = no.pai;
                        rotacaoEsquerda(no);
                    }

                    no.pai.vermelho = false;
                    no.pai.pai.vermelho = true;

                    rotacaoDireita(no.pai.pai);
                }

            } else {

                Node tio = no.pai.pai.esquerda;

                if (tio.vermelho) {

                    no.pai.vermelho = false;
                    tio.vermelho = false;
                    no.pai.pai.vermelho = true;

                    no = no.pai.pai;

                } else {

                    if (no == no.pai.esquerda) {
                        no = no.pai;
                        rotacaoDireita(no);
                    }

                    no.pai.vermelho = false;
                    no.pai.pai.vermelho = true;

                    rotacaoEsquerda(no.pai.pai);
                }
            }
        }

        raiz.vermelho = false;
    }

    private void rotacaoEsquerda(Node no) {

        Node filho = no.direita;

        no.direita = filho.esquerda;

        if (filho.esquerda != nil) {
            filho.esquerda.pai = no;
        }

        filho.pai = no.pai;

        if (no.pai == nil) {
            raiz = filho;
        } else if (no == no.pai.esquerda) {
            no.pai.esquerda = filho;
        } else {
            no.pai.direita = filho;
        }

        filho.esquerda = no;
        no.pai = filho;
    }

    private void rotacaoDireita(Node no) {

        Node filho = no.esquerda;

        no.esquerda = filho.direita;

        if (filho.direita != nil) {
            filho.direita.pai = no;
        }

        filho.pai = no.pai;

        if (no.pai == nil) {
            raiz = filho;
        } else if (no == no.pai.direita) {
            no.pai.direita = filho;
        } else {
            no.pai.esquerda = filho;
        }

        filho.direita = no;
        no.pai = filho;
    }

    public boolean buscar(int valor) {

        Node atual = raiz;

        while (atual != nil) {

            if (valor == atual.valor) {
                return true;
            }

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        return false;
    }

    private Node menorValor(Node no) {

        while (no.esquerda != nil) {
            no = no.esquerda;
        }

        return no;
    }

    private void transplantar(Node antigo, Node novo) {

        if (antigo.pai == nil) {
            raiz = novo;
        } else if (antigo == antigo.pai.esquerda) {
            antigo.pai.esquerda = novo;
        } else {
            antigo.pai.direita = novo;
        }

        novo.pai = antigo.pai;
    }

    public void remover(int valor) {

        Node no = raiz;

        while (no != nil && no.valor != valor) {

            if (valor < no.valor) {
                no = no.esquerda;
            } else {
                no = no.direita;
            }
        }

        if (no == nil) {
            return;
        }

        Node substituto = no;
        Node filho;
        boolean corOriginal = substituto.vermelho;

        if (no.esquerda == nil) {

            filho = no.direita;
            transplantar(no, no.direita);

        } else if (no.direita == nil) {

            filho = no.esquerda;
            transplantar(no, no.esquerda);

        } else {

            substituto = menorValor(no.direita);
            corOriginal = substituto.vermelho;
            filho = substituto.direita;

            if (substituto.pai == no) {
                filho.pai = substituto;
            } else {
                transplantar(substituto, substituto.direita);

                substituto.direita = no.direita;
                substituto.direita.pai = substituto;
            }

            transplantar(no, substituto);

            substituto.esquerda = no.esquerda;
            substituto.esquerda.pai = substituto;

            substituto.vermelho = no.vermelho;
        }

        if (!corOriginal) {
            corrigirRemocao(filho);
        }

        nil.vermelho = false;
    }

    private void corrigirRemocao(Node no) {

        while (no != raiz && !no.vermelho) {

            if (no == no.pai.esquerda) {

                Node irmao = no.pai.direita;

                if (irmao.vermelho) {

                    irmao.vermelho = false;
                    no.pai.vermelho = true;

                    rotacaoEsquerda(no.pai);
                    irmao = no.pai.direita;
                }

                if (!irmao.esquerda.vermelho &&
                        !irmao.direita.vermelho) {

                    if (irmao != nil) {
                        irmao.vermelho = true;
                    }

                    no = no.pai;

                } else {

                    if (!irmao.direita.vermelho) {

                        irmao.esquerda.vermelho = false;
                        irmao.vermelho = true;

                        rotacaoDireita(irmao);
                        irmao = no.pai.direita;
                    }

                    irmao.vermelho = no.pai.vermelho;
                    no.pai.vermelho = false;
                    irmao.direita.vermelho = false;

                    rotacaoEsquerda(no.pai);

                    no = raiz;
                }

            } else {

                Node irmao = no.pai.esquerda;

                if (irmao.vermelho) {

                    irmao.vermelho = false;
                    no.pai.vermelho = true;

                    rotacaoDireita(no.pai);
                    irmao = no.pai.esquerda;
                }

                if (!irmao.direita.vermelho &&
                        !irmao.esquerda.vermelho) {

                    if (irmao != nil) {
                        irmao.vermelho = true;
                    }

                    no = no.pai;

                } else {

                    if (!irmao.esquerda.vermelho) {

                        irmao.direita.vermelho = false;
                        irmao.vermelho = true;

                        rotacaoEsquerda(irmao);
                        irmao = no.pai.esquerda;
                    }

                    irmao.vermelho = no.pai.vermelho;
                    no.pai.vermelho = false;
                    irmao.esquerda.vermelho = false;

                    rotacaoDireita(no.pai);

                    no = raiz;
                }
            }
        }

        no.vermelho = false;
    }

    public void imprimirEmOrdem() {
        imprimir(raiz);
        System.out.println();
    }

    private void imprimir(Node no) {

        if (no != nil) {

            imprimir(no.esquerda);

            System.out.print(no.valor +
                    (no.vermelho ? "(V) " : "(P) "));

            imprimir(no.direita);
        }
    }

    public void imprimirPreOrdem() {
        imprimirPre(raiz);
        System.out.println();
    }

    private void imprimirPre(Node no) {

        if (no != nil) {

            System.out.print(no.valor + " ");

            imprimirPre(no.esquerda);
            imprimirPre(no.direita);
        }
    }
}
