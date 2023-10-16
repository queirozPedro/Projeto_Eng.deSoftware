import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Emprestimo {

    private int id; // Identificador do empréstimo
    private Aluno aluno; // Aluno que fez o empréstimo
    private int idLivro; // Identificador do livro emprestado
    private Date dataInicial; // Data de início do empréstimo
    private Date dataFinal; // Data de final do empréstimo

    public Emprestimo(Aluno aluno, int idLivro, Date dataInicial, Date dataFinal) {
        this.aluno = aluno;
        this.idLivro = idLivro;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    // Método para confirmar o empréstimo
    public boolean confirmarEmprestimo() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state;

        try {
            // Insere o empréstimo na tabela Emprestimo
            String query = "INSERT INTO emprestimo (id_aluno, id_livro, data_inicial, data_final) VALUES (?, ?, ?, ?)";
            state = connection.prepareStatement(query);
            state.setInt(1, aluno.getMatricula());
            state.setInt(2, idLivro);
            state.setDate(3, new java.sql.Date(dataInicial.getTime()));
            state.setDate(4, new java.sql.Date(dataFinal.getTime()));
            state.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Empréstimo não realizado
    }

    // Método para confirmar a renovação
    public boolean confirmarRenovacao() {
        // Lógica para confirmar ou negar a renovação do empréstimo.
        return true; // Retorna true se a renovação foi confirmada, ou false se negada.
    }

    // Método para calcular penalização por devolução fora do prazo
    public void calculoPenalizacao(Date dataPrazo, Date dataFinal) {
        // Lógica para calcular e aplicar a penalização com base nas datas.
    }

    // Outros métodos e getters/setters
}
