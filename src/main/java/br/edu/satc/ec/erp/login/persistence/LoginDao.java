package br.edu.satc.ec.erp.login.persistence;

import br.edu.satc.ec.erp.persistence.connection.BaseDao;
import br.edu.satc.ec.erp.utils.security.Criptografia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static br.edu.satc.ec.erp.utils.LoggerUtils.error;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class LoginDao extends BaseDao {

    public LoginDao() {
        super("USERS");
    }

    public boolean login(String user, String password) {
        String sql = String.format("SELECT ID, NOME, EMAIL, SITUACAO FROM %s WHERE ID = ? AND CPDWD = ?", tableName);
        String located = null;

        try (PreparedStatement ps = getPreparedStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, Criptografia.criptografar(password));

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    located =
                            rs.getString(1);
                }
            }

        } catch (SQLException ex) {
            error(ex, "Ocorreu um erro ao efetuar login.");
        }

        return Objects.nonNull(located);
    }

}
