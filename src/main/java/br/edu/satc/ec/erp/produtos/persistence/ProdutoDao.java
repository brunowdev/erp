package br.edu.satc.ec.erp.produtos.persistence;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.persistence.connection.BaseDao;
import br.edu.satc.ec.erp.produtos.entity.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.edu.satc.ec.erp.utils.LoggerUtils.error;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class ProdutoDao extends BaseDao {

    public ProdutoDao() {
        super("PRODUTOS");
    }

    public Produto persist(Produto produto) {
        String sql = String.format("INSERT INTO %s (NOME, DESCRICAO, QTD_DISPONIVEL, VL_UNITARIO, I_CATEGORIAS_PRODUTO, SITUACAO) VALUES (?, ?, ?, ?, ?, ?)",
                tableName);
        return executeStatement(produto, sql);
    }

    public Produto merge(Produto produto) {

        if (Objects.isNull(produto.getId())) {
            return persist(produto);
        }

        String sql = String.format("UPDATE %s SET NOME = ?, DESCRICAO = ?, QTD_DISPONIVEL = ?, VL_UNITARIO = ?, I_CATEGORIAS_PRODUTO = ?, SITUACAO  = ? WHERE ID = ?", tableName);
        return executeStatement(produto, sql);
    }

    private Produto executeStatement(Produto produto, String sql) {
        try (PreparedStatement ps = getPreparedStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setBigDecimal(3, produto.getQuantidade());
            ps.setBigDecimal(4, produto.getValorUnitario());
            ps.setLong(5, produto.getCategoria().getId());
            ps.setString(6, produto.getSituacao().getValue());

            final boolean update = Objects.nonNull(produto.getId());

            if (update) {
                ps.setLong(7, produto.getId());
                ps.executeUpdate();
            } else {
                ps.execute();
                long id = getProximoCodigo();
                produto.setId(id);
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao salvar o produto.");
            return null;
        }
        return produto;
    }

    public Produto getById(long id) {

        String sql = String.format("SELECT P.ID, P.NOME, P.DESCRICAO, P.QTD_DISPONIVEL, P.VL_UNITARIO, P.SITUACAO, C.ID, C.NOME FROM %s P INNER JOIN CATEGORIAS_PRODUTO C ON C.ID = P.I_CATEGORIAS_PRODUTO WHERE P.ID = ?", tableName);
        Produto produto = null;

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    CategoriaProduto categoriaProduto = new CategoriaProduto(rs.getLong(7), rs.getString(8), null, null);

                    produto = new Produto(id,
                            rs.getString(2),
                            rs.getString(3),
                            rs.getBigDecimal(4),
                            rs.getBigDecimal(5),
                            categoriaProduto,
                            Situacao.fromValue(rs.getString(6)));
                }
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao buscar o produto.");
            return null;
        }

        return produto;
    }

    public List<Produto> getAll() {

        String sql = String.format("SELECT P.ID, P.NOME, P.DESCRICAO, P.QTD_DISPONIVEL, P.VL_UNITARIO, P.SITUACAO, C.ID, C.NOME FROM %s P INNER JOIN CATEGORIAS_PRODUTO C ON C.ID = P.I_CATEGORIAS_PRODUTO", tableName);
        List<Produto> produtos = new ArrayList<>(20);

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    CategoriaProduto categoriaProduto = new CategoriaProduto(rs.getLong(7), rs.getString(8), null, null);
                    Produto produto = new Produto(rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getBigDecimal(4),
                            rs.getBigDecimal(5),
                            categoriaProduto,
                            Situacao.fromValue(rs.getString(6)));

                    produtos.add(produto);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return produtos;
    }

}
