package br.edu.satc.ec.erp.categoriasproduto.persistence;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.persistence.connection.BaseDao;

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
public class CategoriaProdutoDao extends BaseDao {

    public CategoriaProdutoDao() {
        super("CATEGORIAS_PRODUTO");
    }

    public CategoriaProduto persist(CategoriaProduto categoriaProduto) {
        String sql = String.format("INSERT INTO %s (NOME, DESCRICAO, SITUACAO) VALUES (?, ?, ?)", tableName);
        return executeStatement(categoriaProduto, sql);
    }

    public CategoriaProduto merge(CategoriaProduto categoriaProduto) {

        if (Objects.isNull(categoriaProduto.getId())) {
            return persist(categoriaProduto);
        }

        String sql = String.format("UPDATE %s SET NOME = ?, DESCRICAO = ?, SITUACAO = ? WHERE ID = ?", tableName);
        return executeStatement(categoriaProduto, sql);
    }

    private CategoriaProduto executeStatement(CategoriaProduto categoriaProduto, String sql) {
        try (PreparedStatement ps = getPreparedStatement(sql)) {
            ps.setString(1, categoriaProduto.getNome());
            ps.setString(2, categoriaProduto.getDescricao());
            ps.setString(3, categoriaProduto.getSituacao().getValue());

            final boolean update = Objects.nonNull(categoriaProduto.getId());

            if (update) {
                ps.setLong(4, categoriaProduto.getId());
                ps.executeUpdate();
            } else {
                ps.execute();
                long id = getProximoCodigo();
                categoriaProduto.setId(id);
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao salvar a categoria.");
            return null;
        }
        return categoriaProduto;
    }

    public CategoriaProduto getById(long id) {

        String sql = String.format("SELECT NOME, DESCRICAO, SITUACAO FROM %s WHERE ID = ? ", tableName);
        CategoriaProduto categoriaProduto = null;

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    categoriaProduto = new CategoriaProduto(id,
                            rs.getString(1),
                            rs.getString(2),
                            Situacao.fromValue(rs.getString(3)));
                }
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao buscar a categoria.");
            return null;
        }

        return categoriaProduto;
    }

    public List<CategoriaProduto> getAll() {

        String sql = String.format("SELECT * FROM %s", tableName);
        List<CategoriaProduto> categoriasProduto = new ArrayList<>(20);

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    CategoriaProduto categoriaProduto = new CategoriaProduto(rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            Situacao.fromValue(rs.getString(4)));

                    categoriasProduto.add(categoriaProduto);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categoriasProduto;
    }

}
