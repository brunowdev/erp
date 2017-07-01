package br.edu.satc.ec.erp.contas.persistence;

import br.edu.satc.ec.erp.contas.entity.Conta;
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
public class ContaDao extends BaseDao {

    public ContaDao() {
        super("CONTAS");
    }

    public Conta persist(Conta conta) {
        String sql = String.format("INSERT INTO %s (CNPJ, RAZAO_SOCIAL, NOME_FANTASIA, SITUACAO) VALUES (?, ?, ?, ?)", tableName);
        return executeStatement(conta, sql);
    }

    public Conta merge(Conta conta) {

        if (Objects.isNull(conta.getId())) {
            return persist(conta);
        }

        String sql = String.format("UPDATE %s SET CNPJ = ?, RAZAO_SOCIAL = ?, NOME_FANTASIA = ?, SITUACAO = ? WHERE ID = ?", tableName);
        return executeStatement(conta, sql);
    }

    private Conta executeStatement(Conta conta, String sql) {
        try (PreparedStatement ps = getPreparedStatement(sql)) {
            ps.setString(1, conta.getCnpj());
            ps.setString(2, conta.getRazaoSocial());
            ps.setString(3, conta.getNomeFantasia());
            ps.setString(4, conta.getSituacao().getValue());

            final boolean update = Objects.nonNull(conta.getId());

            if (update) {
                ps.setLong(5, conta.getId());
                ps.executeUpdate();
            } else {
                ps.execute();
                long id = getProximoCodigo();
                conta.setId(id);
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao salvar a conta.");
            return null;
        }
        return conta;
    }

    public boolean delete(long id) {

        String sql = String.format("DELETE FROM %s WHERE ID = ?", tableName);

        try (PreparedStatement ps = getPreparedStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() >= 0;
        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao excluir a conta.");
            return false;
        }
    }

    public Conta getById(long id) {

        String sql = String.format("SELECT CNPJ, RAZAO_SOCIAL, NOME_FANTASIA, SITUACAO FROM %s WHERE ID = ? ", tableName);
        Conta conta = null;

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    conta = new Conta(id,
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            Situacao.fromValue(rs.getString(4)));
                }
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao buscar a conta.");
            return null;
        }

        return conta;
    }

    public List<Conta> getAll() {

        String sql = String.format("SELECT * FROM %s", tableName);
        List<Conta> contas = new ArrayList<>(20);

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Conta conta = new Conta(rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            Situacao.fromValue(rs.getString(5)));

                    contas.add(conta);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return contas;
    }

}
