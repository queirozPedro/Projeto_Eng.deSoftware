import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainProjeto {

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false;
        while (!sair) {
            try {
                switch (Integer.valueOf(MenuPrincipal(sc))) {
                    case 1: // Usuário
                        MenuLogin(sc);
                        break;
                    case 2: // Cliente
                        MenuCadastro(sc);
                        break;
                    case 0: // Sair
                        sair = true;
                        System.out.println("\n     - Finalizando -\n");
                        return;
                }

            } catch (NumberFormatException e) {
            }
        }
        sc.close();
    }

    public static String MenuPrincipal(Scanner sc) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println(" ===  Menu Principal  ===");
        System.out.println(" 1 -> Login");
        System.out.println(" 2 -> Registrar-se");
        System.out.println(" 0 -> Sair");
        System.out.print(" >> ");
        return sc.nextLine();
    }


    public static void MenuLogin(Scanner sc) throws InterruptedException, IOException {
        int out = 3;
        try {
            boolean sair = false;
            do {
                LimpaTela();
                System.out.println(" ===  Menu Login  ===");
                System.out.println(" 1 -> Realizar Login");
                System.out.println(" 0 -> Voltar");
                System.out.print(" >> ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        LimpaTela();
                        System.out.println(" < Login >");
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();
                        if (Aluno.loginAluno(email, senha) != null) {

                            // if(Bibliotecario.loginBibliotecario(email, senha) != null){
                            //     tela bibliotecario
                            // }
                            // else{
                            //     tela alluno
                            // }

                        } else {
                            LimpaTela();
                            System.out.println(" Email ou Senha incorretos! ");
                            if (--out == 0) {
                                System.out.print(" Aguarde alguns minutos e tente novamente! ");
                                sc.nextLine();
                                return;
                            }
                        }
                        System.out.print("\n Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 0:
                        sair = true;
                        break;
                }
            } while (!sair);

        } catch (NumberFormatException e) {
        }
    }

    public static void MenuCadastro(Scanner sc) throws InterruptedException, IOException, SQLException {
        try {
            LimpaTela();
            System.out.println(" ===  Tela de Cadastro  ===");
            System.out.println(" 1 -> Registrar-se");
            System.out.println(" 0 -> Voltar");
            System.out.print(" >> ");

            switch (Integer.valueOf(sc.nextLine())) {
                case 1:
                        LimpaTela();
                        System.out.println(" < Cadastro de Aluno >");
                        System.out.print(" Nome: ");
                        String nome = sc.nextLine();
                        System.out.print(" Cpf: ");
                        String cpf = sc.nextLine();
                        System.out.print(" Email: ");
                        String email = sc.nextLine();
                        System.out.print(" Senha: ");
                        String senha = sc.nextLine();
                        System.out.print(" Telefone: ");
                        String telefone = sc.nextLine();
                        System.out.print(" Idade: ");
                        int idade = Integer.valueOf(sc.nextLine());
                        
                        Aluno aluno = new Aluno(nome, cpf, email, idade, senha, telefone);
                        aluno.cadastrarAluno();
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
                        
                case 0:
                    return;
            }
        } catch (NumberFormatException e) {
        }
    }


    public static void menuALuno(Aluno aLuno, Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        int idLivro;
        do {
            try {
                LimpaTela();
                System.out.println(" > Bem Vindo " + aLuno.getNome() + " (^O^) < ");
                System.out.println(" 1 -> Exibir Perfil");
                System.out.println(" 2 -> Pesquisar por Livros");
                System.out.println(" 3 -> Pedir Emprestimo");
                System.out.println(" 4 -> Meus Emprestimos");
                System.out.println(" 0 -> Sair da Conta");
                System.out.print(" > ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        //editarPerfil(sc, aluno);
                        break;
                    case 2: // buscarLivro
                    
                        break;
                    case 3: // realizarEmprestimo
                    
                        break;
                    case 4: // Meus Emprestimos
                        
                        break;
                    case 0:
                        sair = true;
                        System.out.println(" Saindo da Conta");
                        return;
                }
            } catch (NumberFormatException e) {
            }
        } while (!sair);
    }

    public static void LimpaTela() throws InterruptedException, IOException {
        // Isso aqui funciona pra identificar qual SO está sendo usado
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
        }
    }

}