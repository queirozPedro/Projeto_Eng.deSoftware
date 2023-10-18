import java.io.IOException;
import java.util.Scanner;

public class MainProjeto {

    public static void main(String[] args) throws InterruptedException, IOException {
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
                        Aluno aluno = Aluno.loginAluno(email, senha);
                        if (aluno != null) {
                            //Bibliotecario Bibliotecario = Bibliotecario.loginBibliotecario(email, senha);
                            // if (bibliotecario != null) {
                            //     menuBibliotecarioin(bibliotecario, sc);
                            // } else {
                            //     menuAluno(aluno, sc);
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

    public static void MenuCadastro(Scanner sc) throws InterruptedException, IOException {
        try {
            LimpaTela();
            System.out.println(" ===  Tela de Cadastro  ===");
            System.out.println(" 1 -> Registrar-se");
            System.out.println(" 0 -> Voltar");
            System.out.print(" >> ");

            switch (Integer.valueOf(sc.nextLine())) {
                case 1:
                    boolean sair = false;
                    String nome = null, cpf = null, email = null, senha = null, senhaConfirma = null, telefone = null;

                    do {
                        LimpaTela();
                        System.out.println(" < Cadastro >");
                        if (!validarNome(nome)) {
                            System.out.print(" Nome: ");
                            nome = sc.nextLine();
                        } else {
                            System.out.println(" Nome: " + nome);
                            if (!validarCpf(cpf)) {
                                System.out.print(" Cpf (11 digitos): ");
                                cpf = sc.nextLine();
                            } else {
                                System.out.println(" Cpf: " + cpf);
                                if (!validarEmail(email)) {
                                    System.out.print(" Email: ");
                                    email = sc.nextLine();
                                } else {
                                    System.out.println(" Email: " + email);
                                    if (!validarSenha(senha)) {
                                        System.out.print(" Senha (minimo de 6 digitos): ");
                                        senha = sc.nextLine();
                                        System.out.print(" Confirme a Senha: ");
                                        senhaConfirma = sc.nextLine();
                                        if (!senha.equals(senhaConfirma)) {
                                            senha = null;
                                        }
                                    } else {
                                        System.out.println(" Senha: " + senha);
                                        if (!validarTelefone(telefone)) {
                                            System.out.print(" Telefone (11 digitos)): ");
                                            telefone = sc.nextLine();
                                        } else {
                                            System.out.println(" Telefone: " + telefone);
                                            System.out.print(" Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                                //Aluno aluno = new Aluno();
                                                //aluno.cadastrarAluno();
                                            }
                                            System.out.print(" Aperte Enter para Continuar! ");
                                            sc.nextLine();
                                            sair = true;
                                        }
                                    }
                                }
                            }
                        }
                    } while (!sair);
                    break;
                case 0:
                    return;
            }
        } catch (NumberFormatException e) {
        }
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

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-0-9]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        if (!cpf.matches("\\d{11}")) {
            return false;
        }
        return true;
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }
        if (!telefone.matches("\\d{11}")) {
            return false;
        }
        return true;
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }
        return true;

    }

    public static boolean validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty() || senha.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

}