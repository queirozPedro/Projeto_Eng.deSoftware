import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bibliotecario extends Aluno {

    public Bibliotecario(int matricula, String nome, String cpf, String email, int idade, String senha, String telefone) {
        super(matricula, nome, cpf, email, idade, senha, telefone);
    }

    public Bibliotecario(String cpf, String nome, String senha, String email, String telefone, int idade) {
        super(cpf, nome, senha, email, telefone, idade);
    }

    public void inserirAluno(Aluno aluno) {
        aluno.cadastrar();
    }

    public void buscarAluno(int idAluno) {
        Aluno aluno = buscaUsuarioPorMatricula(idAluno);
        if (aluno != null) {
            System.out.println(aluno.toString());
        } else {
            System.out.println("Aluno não encontrado");
        }
    }

    public void editarAluno(int idAluno, Aluno aluno) {
        Aluno alunoExistente = buscaUsuarioPorMatricula(idAluno);
        if (alunoExistente != null) {
            alunoExistente.editarUsuario(aluno.getNome(), aluno.getSenha(), aluno.getEmail(), aluno.getIdade(),
                    aluno.getTelefone(), false);
            System.out.println("Aluno editado com sucesso!");
        } else {
            System.out.println("Aluno não encontrado");
        }
    }

    public void removerAluno(int idAluno) {
        Aluno alunoExistente = buscaUsuarioPorMatricula(idAluno);
        if (alunoExistente != null) {
            excluirUsuario(alunoExistente.getCpf());
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

    private Aluno buscaUsuarioPorMatricula(int matricula) {
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            String query = "SELECT * From Usuario where matricula = ?";
            state = PostgreSQLConnection.getInstance().getConnection().prepareStatement(query);
            state.setInt(1, matricula);
            result = state.executeQuery();

            if (result.next()) {
                return new Aluno(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
                        result.getInt(5), result.getString(6), result.getString(7));
            }
            state.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
