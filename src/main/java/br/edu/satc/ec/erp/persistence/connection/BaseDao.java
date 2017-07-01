package br.edu.satc.ec.erp.persistence.connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.edu.satc.ec.erp.utils.LoggerUtils.error;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class BaseDao {

    protected final String tableName;

    public BaseDao(String tableName) {
        this.tableName = tableName;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return ConnectionUtils.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            error(e, "Ocorreu um erro ao obter o preparedStatement para o SQL " + sql);
        }
        return null;
    }

    public long getProximoCodigo() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int codigo = 0;
        try {
            String sql = String.format("SELECT MAX(id) FROM %s", tableName);
            ps = getPreparedStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            return codigo;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean delete(long id) {

        String sql = String.format("DELETE FROM %s WHERE ID = ?", tableName);

        try (PreparedStatement ps = getPreparedStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() >= 0;
        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao excluir a registro.");
            return false;
        }
    }

}
