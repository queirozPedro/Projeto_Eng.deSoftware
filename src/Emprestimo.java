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
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state;

        try {
            // Atualiza a data_final do empréstimo para uma nova data
            String query = "UPDATE emprestimo SET data_final = ? WHERE id_aluno = ? AND id_livro = ? AND data_final = ?";
            state = connection.prepareStatement(query);
            state.setDate(1, new java.sql.Date(dataFinal.getTime())); // Nova data final
            state.setInt(2, aluno.getMatricula());
            state.setInt(3, idLivro);
            state.setDate(4, new java.sql.Date(dataInicial.getTime())); // Data inicial original
            int updatedRows = state.executeUpdate();

            return updatedRows > 0; // Retorna true se a renovação foi confirmada, ou false se negada.
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Renovação não realizada
    }

    // Método para calcular penalização por devolução fora do prazo
    public void calculoPenalizacao(Date dataPrazo, Date dataDevolucao) {
        long diff = dataDevolucao.getTime() - dataPrazo.getTime();
        long diasDeAtraso = diff / (24 * 60 * 60 * 1000); // Converter milissegundos em dias

        // Lógica para calcular e aplicar a penalização com base nos dias de atraso.
        if (diasDeAtraso > 0) {
            // Aplicar penalização, como multa, de acordo com as regras da biblioteca.
            // Você pode adicionar lógica específica aqui.
        }
    }
}