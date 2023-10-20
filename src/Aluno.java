import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

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

    public Aluno(String nome, String cpf, String email, int idade, String senha, String telefone) {
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
     * @throws SQLException
     */
    public boolean cadastrarAluno() throws SQLException {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        
        if (buscaAluno(getCpf()) == null) {
            try {

                int idAluno;

                // Insere o Aluno na tabela Aluno
                String query = "INSERT Into usuario (nome, cpf, email, idade, senha, telefone, bibliotecario) VALUES (?, ?, ?, ?, ?, ?, ?)";
                state = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                state.setString(1, nome);
                state.setString(2, cpf);
                state.setString(3, email);
                state.setInt(4, idade);
                state.setString(5, senha);
                state.setString(6, telefone);
                state.setBoolean(7, false);
                state.executeUpdate();
                

                ResultSet keyAluno = state.getGeneratedKeys();
                if (keyAluno.next()) {
                    
                    idAluno = keyAluno.getInt(1);
                    setMatricula(idAluno);
                } else{
                    return false;
                }

                System.out.println(" Aluno Cadastrado!");
                return true;

            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (state != null) {
                        state.close();
                    }
                } catch (SQLException e) {
                    throw e;
                }
            }

        } else {
            System.out.println(" ERRO! Cpf já Cadastrado!");
            return false;
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
    public static boolean excluirAluno(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            // Remove o Aluno da tabela Aluno
            String query = "DELETE From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();
            state.close();

            System.out.println(" Aluno Excluido!");
            return true;
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
        return false;
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
     * @throws SQLException
     */
    public static Aluno buscaAluno(String cpf) throws SQLException {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        Aluno aluno = null;

        try {

            // Seleciona tudo (*) na tabela usuario onde o cpf foi o igual ao recebido
            String query = "SELECT * From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            // Retorna o Aluno
            if (result.next()) {
                int matricula = result.getInt(1);
                String nome = result.getString(2);
                String email = result.getString(4);
                int idade = result.getInt(5);
                String senha = result.getString(6);
                String telefone = result.getString(7);

                aluno = new Aluno(matricula, nome, cpf, email, idade, senha, telefone);
                return aluno;
            }

            state.close();

            return aluno;


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
                throw e;
            }
        }
        return aluno;
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

            String query = "SELECT cpf From usuario where email = ? AND senha = ?";
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

    /**
     * Método editarAluno: Método responsável por editar os atributos: nome,
     * senha, email ou telefone de um Aluno 
     * Obs.: O Método não trata dados, portanto os dados
     * devem ser recebidos no formatriculao correto.
     * 
     * @param campo
     * @param valor
     */
    public void editarAluno(String campo, String valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try{
            String query = "UPDATE usuario SET "+ campo +" = ? WHERE cpf = ?";
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
    
    /**
     * Método editarAluno: Método responsável por editar os atributos: idade.
     * Obs.: O Método não trata dados, portanto os dados
     * devem ser recebidos no formatriculao correto.
     * 
     * @param campo
     * @param valor
     */
    public void editarAluno(String campo, int valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            String query = "UPDATE usuario SET "+ campo +" = ? WHERE cpf = ?";
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

    /**
     * Método editarAluno: Método responsável por editar os atributos: bibliotecário.
     * No qual pode ser utilizado para tornar um aluno um bibliotecário. 
     * Obs.: O Método não trata dados, portanto os dados
     * devem ser recebidos no formatriculao correto.
     * 
     * @param campo
     * @param valor
     */
    public void editarAluno(String campo, boolean valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        
        try {
            String query = "UPDATE usuario SET "+ campo +" = ? WHERE cpf = ?";
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
            String query = "SELECT * From usuario WHERE bibliotecario = ?";
            state = connection.prepareStatement(query);
            state.setBoolean(1, false);
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

    /**
     * Método solicitarEmprestimo: Método que o Aluno irá solicitar o empréstimo 
     * de um livro.
     * Esse método irá criar uma tupla na tabela empréstimo com o status ""
     * que representará que esse empréstimo está aguardando ser confirmado
     * por um bibliotecário.
     * 
     * @param livro
     * @param data
     * 
     * @return boolean
     */
    public boolean solicitarEmprestimo(int livro, LocalDate data){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            // O atributo status ficaria com algo em que diria que o empréstimo está pendente
            String query = "INSERT Into emprestimo (matricula, id_livro, data_inicial, status) VALUES (?, ?, ?, ?)";
            state = connection.prepareStatement(query);
            state.setInt(1, this.matricula);
            state.setInt(2, livro);
            state.setDate(3, Date.valueOf(data));
            state.setString(4, "solicitando empréstimo");
            state.executeUpdate();

            return true;
            
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método renovarEmprestimo: Método que o Aluno irá solicitar a renovação
     * de um empréstimo de um livro.
     * Esse método irá atualizar uma tupla na tabela empréstimo com o status ""
     * que representará que essa solicitação está aguardando ser confirmado
     * por um bibliotecário.
     * 
     * @param livro
     * 
     * @return boolean
     */
    public boolean renovarEmprestimo(int livro){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            String query = "UPDATE emprestimo SET status = ? WHERE matricula = ? AND  id_livro = ?";
            state = connection.prepareStatement(query);
            state.setString(1, "solicitando renovação");
            state.setInt(2, this.matricula);
            state.setInt(3, livro);
            state.executeUpdate();

            return true;
            
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método devolverLivro: Método que o Aluno irá devolver um dos livros que ele
     * pegou emprestado.
     * Esse método irá atualizar uma tupla na tabela empréstimo com o status "devolvido"
     * que representará que esse livro foi devolvido.
     * 
     * @param livro
     * 
     * @return boolean
     */
    public boolean devolverLivro(int livro){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {

            String query = "UPDATE emprestimo SET status = ?, data_devolucao = ? WHERE matricula = ? AND  id_livro = ?";
            state = connection.prepareStatement(query);
            state.setString(1, "devolvido");
            state.setDate(2, Date.valueOf(LocalDate.now()));
            state.setInt(3, this.matricula);
            state.setInt(4, livro);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Aluno other = (Aluno) obj;

        return matricula == other.matricula &&
                idade == other.idade &&
                Objects.equals(cpf, other.cpf) &&
                Objects.equals(nome, other.nome) &&
                Objects.equals(senha, other.senha) &&
                Objects.equals(email, other.email) &&
                Objects.equals(telefone, other.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, cpf, nome, senha, email, telefone, idade);
    }

}

