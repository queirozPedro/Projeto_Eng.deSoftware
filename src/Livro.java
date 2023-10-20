import java.sql.*;

public class Livro {
    String titulo;
    String editora;
    String autor;
    int numPaginas;
    int quantidade;
    int id;

    
    public Livro(String titulo, String editora, String autor, int numPaginas, int quantidade) {
        this.titulo = titulo;
        this.editora = editora;
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.quantidade = quantidade;
    }
    
    //Método de cadastro do livro no banco de dados.
    public void cadastrar() throws SQLException{
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pst = null;
        int idLivro;
        
            try {

                String query = "INSERT INTO livro (titulo, editora, autor, npaginas, quantidade ) VALUES (?,?,?,?,?)";
                 pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                 pst.setString(1, getTitulo());
                 pst.setString(2, getEditora());
                 pst.setString(3, getAutor());
                 pst.setInt(4, getNumPaginas());
                 pst.setInt(5, getQuantidade());

                 int validacaoCadastro = pst.executeUpdate();

                 ResultSet keyLivro = pst.getGeneratedKeys();
                if (keyLivro.next()) {
                    
                    idLivro = keyLivro.getInt(1);
                    setId(idLivro);
                }

                 if(validacaoCadastro > 0){
     
                     System.out.println("Livro cadastrado com sucesso");
     
                 }else{
     
                     System.out.println("Erro ao cadastrar este livro");
     
                 }


                pst.close();

        } catch (java.sql.SQLException e) {
            throw e;
        }

    }


    /**
     * Esse método procura no banco de dados o livro com o id que é passado como parâmetro, caso não tenha nenhum livro cadastrado com o id do parâmetro, o retorno será nulo.
     * @param id
     * @return
     */
    public static Livro buscar(int id){
        PreparedStatement  pstmt = null; 
        Connection connection = null;
        ResultSet rs = null;

        try {

            connection = PostgreSQLConnection.getInstance().getConnection();
            String query = "SELECT * FROM livro WHERE id_livro = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if(!rs.next()){
            
            pstmt.close();
            rs.close();
            connection.close();

                return null;
            }else{

                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String editora = rs.getString("editora");
                    int numPaginas = rs.getInt("n_paginas");
                    int quantidade = rs.getInt("quantidade");

                    Livro livro = new Livro(titulo, editora, autor, numPaginas, quantidade);
                    livro.setId(id);

                    pstmt.close();
                    rs.close();

                    return livro;


            }

            
        } catch (java.sql.SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    /**
     *  Esse método procura no banco de dados o livro com o titulo que é passado como parâmetro, caso não tenha nenhum livro cadastrado com esse titulo do parâmetro, o retorno será nulo.
     * @param titulo
     * @return
     */
    public static Livro buscar(String titulo){
        PreparedStatement  pstmt = null; 
        Connection connection = null;
        ResultSet rs = null;

        try {

            connection = PostgreSQLConnection.getInstance().getConnection();
            String query = "SELECT * FROM livro WHERE titulo = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, titulo);

            rs = pstmt.executeQuery();

            if(!rs.next()){
            
            pstmt.close();
            rs.close();

                return null;

            }else{

                    
                    String autor = rs.getString("autor");
                    String editora = rs.getString("editora");
                    int numPaginas = rs.getInt("n_paginas");
                    int quantidade = rs.getInt("quantidade");
                    int id = rs.getInt("id_livro");

                    Livro livro = new Livro(titulo, editora, autor, numPaginas, quantidade);
                    livro.setId(id);

                    pstmt.close();
                    rs.close();
                    connection.close();

                    return livro;


            }

            
        } catch (java.sql.SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public void editar(){
        //Preciso saber o que deve ser passado...
    }


    /**
     * Esse método de exclusão na qual é passado como parâmetro um inteiro, diz respeito a exclusão a partir do id do livro.
     * @param id
     */
    public static void excluir(int id){
        PreparedStatement pstm = null;
        Connection connection = null;

        try {
            
            connection = PostgreSQLConnection.getInstance().getConnection();
            String query = "DELETE FROM livro WHERE id_livro = ?";
            pstm = connection.prepareStatement(query);

            pstm.setInt(1, id);

            int validacaoDeExclusa = pstm.executeUpdate();

            if(validacaoDeExclusa > 0){
                System.out.println("O livro foi removido...");
            }else{
                System.out.println("Erro ao remover o livro");
            }

            pstm.close();

        } catch (java.sql.SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Esse método de exclusão que é passado como parâmetros o titulo e autor, vai realizar a exclusão a partir desses parâmetros.
     * @param titulo
     * @param autor
     */
    public static void excluir(String titulo, String autor){
        PreparedStatement pstm = null;
        Connection connection = null;

        try {
            
            connection = PostgreSQLConnection.getInstance().getConnection();
            String query = "DELETE FROM livro WHERE titulo = ? and autor = ?";
            pstm = connection.prepareStatement(query);

            pstm.setString(1, titulo);
            pstm.setString(2, autor);

            int validacaoDeExclusa = pstm.executeUpdate();

            if(validacaoDeExclusa > 0){
                System.out.println("O livro foi removido...");
            }else{
                System.out.println("Erro ao remover o livro");
            }

            pstm.close();
            connection.close();

        } catch (java.sql.SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void listar(){
        PreparedStatement pstm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = PostgreSQLConnection.getInstance().getConnection();

            String query = "SELECT * FROM livro";
            pstm = connection.prepareStatement(query);

            rs = pstm.executeQuery();

            if (!rs.next()) { // verifica se não há resultados
                System.out.println("Não há nenhum livro cadastrado.");
            } else {
                do {

                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String editora = rs.getString("editora");
                    int numPaginas = rs.getInt("n_paginas");
                    int quantidade = rs.getInt("quantidade");
                    int id = rs.getInt("id_livro");

                    Livro livro = new Livro(titulo, editora, autor, numPaginas, quantidade);
                    livro.setId(id);

                    System.out.println(livro);

                } while (rs.next()); 
            }

            pstm.close();


        } catch (java.sql.SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }


    //Getters
    public String getTitulo() {
        return titulo;
    }


    public String getAutor() {
        return autor;
    }


    public String getEditora() {
        return editora;
    }


    public int getNumPaginas() {
        return numPaginas;
    }


    public int getQuantidade() {
        return quantidade;
    }


    public int getId() {
        return id;
    }

    //Setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public void setEditora(String editora) {
        this.editora = editora;
    }


    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }


    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

        public void setId(int id) {
        this.id = id;
    }


    public String toString() {
        return "Livro [titulo=" + titulo + ", editora=" + editora + ", autor=" + autor + ", numPaginas=" + numPaginas
                + ", quantidade=" + quantidade + ", id=" + id + "]";
    }




    
}