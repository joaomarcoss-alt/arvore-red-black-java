public class Main {

    public static void main(String[] args) {

        RedBlackTree arvore = new RedBlackTree();

        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(30);
        arvore.inserir(15);
        arvore.inserir(5);
        arvore.inserir(25);

        System.out.println("Arvore em ordem:");
        arvore.imprimirEmOrdem();

        System.out.println("\nArvore em pre-ordem:");
        arvore.imprimirPreOrdem();

        System.out.println("\nBuscando valores:");

        System.out.println("15 encontrado: " +
                arvore.buscar(15));

        System.out.println("50 encontrado: " +
                arvore.buscar(50));

        System.out.println("\nRemovendo o valor 20:");

        arvore.remover(20);

        System.out.println("Arvore depois da remocao:");
        arvore.imprimirEmOrdem();
    }
}
