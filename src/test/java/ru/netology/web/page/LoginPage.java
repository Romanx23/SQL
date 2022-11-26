package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement notification = $("[data-test-id=error-notification]");

    private SelenideElement notifButton = $x("//div[@data-test-id=\"error-notification\"]/button[@role=\"button\"]");


    public void validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void cleanFields() {
        loginField.doubleClick();
        loginField.sendKeys(Keys.BACK_SPACE);
        passwordField.doubleClick();
        passwordField.sendKeys(Keys.BACK_SPACE);
    }

    public void getLoginNotification() {
        notification.shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
    public void getSystemBlocked() {
        notification.shouldHave(Condition.text("Доступ заблокирован")).shouldBe(Condition.visible);
    }
    public void notifButtonClick(){
        notifButton.click();
    }
}