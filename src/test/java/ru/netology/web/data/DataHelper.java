package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getFirstUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getSecondUser() {
        return new AuthInfo("petya", "123qwerty");
    }
    public static AuthInfo getIncorrectName() {
        return new AuthInfo("nazar", "qwerty123");
    }
    public static AuthInfo getIncorrectPassword() {
        return new AuthInfo("petya", "789zxcvbn");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        val CodeFromSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        String verificationCode;
        val runSQL = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val code = runSQL.query(conn, CodeFromSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new VerificationCode(verificationCode);
    }

    public static VerificationCode getWrongCode() {return new VerificationCode("00000"); }

    public static void cleanDatabase() {

        val delUsers = "DELETE FROM users;";
        val delCards = "DELETE FROM cards;";
        val delAuthCodes = "DELETE FROM auth_codes;";
        val delCardTransactions = "DELETE FROM card_transactions;";

        val runSQL = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runSQL.update(conn, delAuthCodes);
            runSQL.update(conn, delCardTransactions);
            runSQL.update(conn, delCards);
            runSQL.update(conn, delUsers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
