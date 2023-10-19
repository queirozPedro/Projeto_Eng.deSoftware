import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bibliotecario extends Aluno {

    // Construtor da classe Bibliotecario que chama o construtor da classe Aluno
    public Bibliotecario(int matricula, String nome, String cpf, String email, int idade, String senha,
            String telefone) {
        super(matricula, nome, cpf, email, idade, senha, telefone);
    }

    // Método para inserir um novo aluno no sistema, chamando o método
    // cadastrarAluno() do objeto Aluno passado como parâmetro
    public void inserirAluno(Aluno aluno) {
        aluno.cadastrarAluno();
    }

    // Método para buscar um aluno com base na matrícula fornecida, imprimindo suas
    // informações se encontrado
    public void buscarAluno(int idAluno) {
        Aluno aluno = buscaAlunoPorMatricula(idAluno);
        if (aluno != null) {
            System.out.println(aluno.toString());
        } else {
            System.out.println("Aluno não encontrado");
        }
    }

    // Deixei comentado, pq tava dando conflito com Aluno
    // public void editarAluno(int idAluno, Aluno aluno) {
    // Aluno alunoExistente = buscaAlunoPorMatricula(idAluno);
    // if (alunoExistente != null) {
    // alunoExistente.editarAluno(aluno.getNome(), aluno.getSenha(),
    // aluno.getEmail(), aluno.getIdade(),
    // aluno.getTelefone(), false);
    // System.out.println("Aluno editado com sucesso!");
    // } else {
    // System.out.println("Aluno não encontrado");
    // }
    // }

    // Método para remover um aluno com base na matrícula, chamando o método
    // excluirAluno() com o CPF do aluno
    public void removerAluno(int idAluno) {
        Aluno alunoExistente = buscaAlunoPorMatricula(idAluno);
        if (alunoExistente != null) {
            excluirAluno(alunoExistente.getCpf());
            System.out.println("Aluno removido com sucesso!");
        } else {
            System.out.println("Aluno não encontrado");
        }
    }

    // Getters e setters para a classe Aluno
    public int getMatricula() {
        return super.getMatricula();
    }

    public void setMatricula(int matricula) {
        super.setMatricula(matricula);
    }

    public String getCpf() {
        return super.getCpf();
    }

    public void setCpf(String cpf) {
        super.setCpf(cpf);
    }

    public String getNome() {
        return super.getNome();
    }

    public void setNome(String nome) {
        super.setNome(nome);
    }

    public String getSenha() {
        return super.getSenha();
    }

    public void setSenha(String senha) {
        super.setSenha(senha);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getTelefone() {
        return super.getTelefone();
    }

    public void setTelefone(String telefone) {
        super.setTelefone(telefone);
    }

    public int getIdade() {
        return super.getIdade();
    }

    public void setIdade(int idade) {
        super.setIdade(idade);
    }

    @Override
    public String toString() {
        return "Bibliotecario []";
    }

    // Método privado que executa uma consulta SQL para buscar um aluno por
    // matrícula no banco de dados
    private Aluno buscaAlunoPorMatricula(int matricula) {
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            // Consulta SQL para buscar o aluno por matrícula
            String query = "SELECT * From Aluno where matricula = ?";
            state = PostgreSQLConnection.getInstance().getConnection().prepareStatement(query);
            state.setInt(1, matricula);
            result = state.executeQuery();

            // Se o resultado for encontrado, cria e retorna um novo objeto Aluno com os
            // detalhes encontrados
            if (result.next()) {
                return new Aluno(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
                        result.getInt(5), result.getString(6), result.getString(7));
            }
            state.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Em caso de exceção, imprime o rastreamento do erro
        }
        return null; // Retorna nulo se nenhum aluno for encontrado com a matrícula especificada
    }
}
