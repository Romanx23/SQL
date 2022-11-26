package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanData() {
        DataHelper.cleanDatabase();
    }

    @Test
    void correctLoginFirstUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getFirstUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCodeFor());
        var dashboardPage = new DashboardPage();
    }

    @Test
    void correctLoginSecondUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getSecondUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCodeFor());
        var dashboardPage = new DashboardPage();
    }

    @Test
    void notLoginIncorrectName() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getIncorrectName();
        loginPage.validLogin(authInfo);
        loginPage.getLoginNotification();
    }

    @Test
    void notLoginIncorrectPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getIncorrectPassword();
        loginPage.validLogin(authInfo);
        loginPage.getLoginNotification();
    }

    @Test
    void notLoginIncorrectVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getSecondUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.invalidVerify(DataHelper.getWrongCode());
        verificationPage.getCodeNotification();
    }

    @Test
    void BlockPageWithTreeIncorrectPasswords() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getIncorrectPassword();
        for (int i = 0; i < 3; i++) {
            loginPage.validLogin(authInfo);
            loginPage.getLoginNotification();
            loginPage.notifButtonClick();
            loginPage.cleanFields();
        }
        loginPage.validLogin(authInfo);
        loginPage.getSystemBlocked();
    }

}
