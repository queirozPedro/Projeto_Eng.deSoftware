import java.sql.*;
import java.util.ArrayList;

public class Aluno {

    private int matricula;
    private String cpf;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private int idade;

    public Aluno(int matricula, String nome, String cpf, String email, int idade, String senha, String telefone) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.idade = idade;
    }

    public Aluno(String cpf, String nome, String senha, String email, String telefone, int idade) {
        setCpf(cpf);
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setTelefone(telefone);
        setIdade(idade);
    }

    /**
     * Método cadastrar: Responsável por criar uma nova conta de Usuário no banco
     * de dados sistema.
     * Obs.: Recebe uma instancia da Classe Usuario com dados já formatriculaados
     * corretamente,a falta
     * dessa formatriculaação pode causar erros.
     */
    public void cadastrar() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state;
        if (buscaUsuario(getCpf()) == null) {
            try {

                // Insere o usuário na tabela Usuario
                String query = "INSERT Into usuario (nome, cpf, email, idade, senha, telefone, bibliotecario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                state = connection.prepareStatement(query);
                state.setString(1, nome);
                state.setString(2, cpf);
                state.setString(3, email);
                state.setInt(4, idade);
                state.setString(5, senha);
                state.setString(6, telefone);
                state.setBoolean(7, false);
                state.executeUpdate();

                System.out.println(" Usuário Cadastrado!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(" ERRO! Cpf já Cadastrado!");
        }
    }

    /**
     * Método excluirConta: Método que recebe um cpf de um usário e remove ele do
     * banco de dados do sistema.
     * Obs.: O Método não trata dados, portanto o cpf deve ser recebido no
     * formatriculao
     * correto.
     * 
     * @param cpf
     */
    public static void excluirUsuario(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state;

        try {

            // Remove o usuário da tabela Usuario
            String query = "DELETE From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();
            state.close();

            System.out.println(" Usuário Excluido!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método buscaUsuario: Responsável por retornar um Usuário do banco de dados de
     * acordo com seu cpf.
     * Obs.: O Método não trata dados, portanto o cpf deve ser recebido no
     * formatriculao
     * correto. Retorna
     * null caso não encontre.
     * 
     * @param cpf
     * @return Usuario
     */
    public static Aluno buscaUsuario(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {

            // Seleciona tudo (*) na tabela Usuario onde o cpf foi o igual ao recebido
            String query = "SELECT * From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            // Retorna o usuário
            if (result.next()) {
                return null;
            }
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método loginUsuario: Método que recebe um email e uma senha e retorna o
     * usuário que seja correspondente aos dois. Ele será usado juntamente com o
     * buscaUsuario
     * para efetuar o login. Obs.: O Método não trata dados, portanto o email e
     * senha devem
     * ser recebidos no formatriculao correto.
     * 
     * @param email
     * @param senha
     * @return Usuario
     */
    public static Aluno loginUsuario(String email, String senha) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {

            // Remove o usuário da tabela Usuario
            String query = "SELECT cpf From usuario where email = ? AND senha = ?";
            state = connection.prepareStatement(query);
            state.setString(1, email);
            state.setString(2, senha);
            result = state.executeQuery();

            if (result.next()) {
                return buscaUsuario(result.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Método editarUsuario: Edita o usuário no banco de dados. Obs.: Deve receber
     * nulo todos os valores que NÃO serão
     * editados. Não faz tratamento de dados, deve receber dados já
     * formatriculaados.
     * 
     * @param nome
     * @param senha
     * @param email
     * @param telefone
     */
    public void editarUsuario(String nome, String senha, String email, int idade, String telefone, boolean bibliotecario) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        // Primeiro checa se algum desses dados foi recebido e aplica valores aos que
        // forem null.
        setNome(nome != null ? nome : getNome());
        setSenha(senha != null ? senha : getSenha());
        setEmail(email != null ? email : getEmail());
        setIdade(idade != -1 ? idade : getIdade());
        setTelefone(telefone != null ? telefone : getTelefone());

        try {

            // Atualiza nome, senha e email na tabela usuario na posição do cpf usado.
            String query = "UPDATE Usuario SET nome = ?, senha = ?, email = ?, idade = ?, telefone = ?, bibliotecario = ? WHERE cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, this.nome);
            state.setString(2, this.senha);
            state.setString(3, this.email);
            state.setInt(4, this.idade);
            state.setString(5, this.telefone);
            state.setBoolean(6, bibliotecario);
            state.setString(7, this.cpf);
            state.executeUpdate();
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método listaUsuario: Método que acessa o banco de dados e retorna um
     * ArrayList de Usuario.
     * 
     * @return ArrayList<Usuario>
     */
    public static ArrayList<Aluno> listaUsuario() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result;

        // ArrayList do tipo Usuario, que será retornado com todos os usuarios do banco.
        ArrayList<Aluno> usuarios = new ArrayList<Aluno>();

        try {

            // Seleciona todos os usuarios.
            String query = "SELECT * From Usuario";
            state = connection.prepareStatement(query);
            result = state.executeQuery();

            while (result.next()) {

                // Cria um objeto para cada um e coloca no ArrayList.
                Aluno user = new Aluno(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getInt(5),
                result.getString(6),
                result.getString(7));
                usuarios.add(user);
            }

            state.close();
            return usuarios;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean solicitarEmprestimo(int livro, Date data){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            // O atributo status ficaria com algo em que diria que o empréstimo está pendente
            String query = "INSERT Into usuario (id_usuario, id_livro, datainicial, status) VALUES (?, ?, ?, ?)";
            state = connection.prepareStatement(query);
            state.setInt(1, this.matricula);
            state.setInt(2, livro);
            state.setDate(3, data);
            state.setString(4, "");
            state.executeUpdate();

            return true;
            
        } catch (Exception e) {
            return false;
        }
    }

    public boolean renovarEmprestimo(int livro){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            // O atributo status ficaria com algo em que diria que o empréstimo está com a renovação pendente
            String query = "UPDATE emprestimo SET status = ? WHERE id_cliente = ? AND  id_livro = ?";
            state = connection.prepareStatement(query);
            state.setString(1, "");
            state.setInt(2, this.matricula);
            state.setInt(3, livro);
            state.executeUpdate();

            return true;
            
        } catch (Exception e) {
            return false;
        }
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Aluno [matricula=" + matricula + ", cpf=" + cpf + ", nome=" + nome + ", senha=" + senha + ", email="
                + email + ", telefone=" + telefone + ", idade=" + idade + "]";
    }

}
