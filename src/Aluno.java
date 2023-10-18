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

    /**
     * Método cadastrar: Responsável por criar uma nova conta de Aluno no banco
     * de dados sistema.
     * Obs.: Recebe uma instancia da Classe Aluno com dados já formatriculaados
     * corretamente,a falta
     * dessa formatriculaação pode causar erros.
     */
    public void cadastrarAluno() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        
        if (buscaAluno(getCpf()) == null) {
            try {

                // Insere o Aluno na tabela Aluno
                String query = "INSERT Into Aluno (nome, cpf, email, idade, senha, telefone, bibliotecario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                state = connection.prepareStatement(query);
                state.setString(1, nome);
                state.setString(2, cpf);
                state.setString(3, email);
                state.setInt(4, idade);
                state.setString(5, senha);
                state.setString(6, telefone);
                state.setBoolean(7, false);
                state.executeUpdate();

                System.out.println(" Aluno Cadastrado!");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (state != null) {
                        state.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    public static void excluirAluno(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            // Remove o Aluno da tabela Aluno
            String query = "DELETE From Aluno where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();
            state.close();

            System.out.println(" Aluno Excluido!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método buscaAluno: Responsável por retornar um Aluno do banco de dados de
     * acordo com seu cpf.
     * Obs.: O Método não trata dados, portanto o cpf deve ser recebido no
     * formatriculao
     * correto. Retorna
     * null caso não encontre.
     * 
     * @param cpf
     * @return Aluno
     */
    public static Aluno buscaAluno(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {

            // Seleciona tudo (*) na tabela Aluno onde o cpf foi o igual ao recebido
            String query = "SELECT * From Aluno where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            // Retorna o Aluno
            if (result.next()) {
                return null;
            }
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(result != null){
                    result.close();
                }
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Método loginAluno: Método que recebe um email e uma senha e retorna o
     * Aluno que seja correspondente aos dois. Ele será usado juntamente com o
     * buscaAluno
     * para efetuar o login. Obs.: O Método não trata dados, portanto o email e
     * senha devem
     * ser recebidos no formatriculao correto.
     * 
     * @param email
     * @param senha
     * @return Aluno
     */
    public static Aluno loginAluno(String email, String senha) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {

            // Remove o Aluno da tabela Aluno
            String query = "SELECT cpf From Aluno where email = ? AND senha = ?";
            state = connection.prepareStatement(query);
            state.setString(1, email);
            state.setString(2, senha);
            result = state.executeQuery();

            if (result.next()) {
                return buscaAluno(result.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(result != null){
                    result.close();
                }
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Mexi em editar Aluno pq tava feio demais

    public void editarAluno(String campo, String valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try{
            String query = "UPDATE Aluno SET "+ campo +" = ? WHERE cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, valor);
            state.setString(2, getCpf());
            state.executeUpdate();
            state.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void editarAluno(String campo, int valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            String query = "UPDATE Aluno SET "+ campo +" = ? WHERE cpf = ?";
            state = connection.prepareStatement(query);
            state.setInt(1, valor);
            state.setString(2, getCpf());
            state.executeUpdate();
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void editarAluno(String campo, boolean valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        
        try {
            String query = "UPDATE Aluno SET "+ campo +" = ? WHERE cpf = ?";
            state = connection.prepareStatement(query);
            state.setBoolean(1, valor);
            state.setString(2, getCpf());
            state.executeUpdate();
            state.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método listaAluno: Método que acessa o banco de dados e retorna um
     * ArrayList de Aluno.
     * 
     * @return ArrayList<Aluno>
     */
    public static ArrayList<Aluno> listaAluno() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result;

        // ArrayList do tipo Aluno, que será retornado com todos os Alunos do banco.
        ArrayList<Aluno> Alunos = new ArrayList<Aluno>();

        try {

            // Seleciona todos os Alunos.
            String query = "SELECT * From Aluno";
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
                Alunos.add(user);
            }

            state.close();
            return Alunos;

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
            String query = "INSERT Into Aluno (id_Aluno, id_livro, datainicial, status) VALUES (?, ?, ?, ?)";
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
