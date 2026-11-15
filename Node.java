public class Node {

    int valor;
    boolean vermelho;

    Node esquerda;
    Node direita;
    Node pai;

    public Node(int valor) {
        this.valor = valor;
        this.vermelho = true;
    }
}
