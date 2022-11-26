package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private SelenideElement heading = $x("//h2[contains(text(),'Личный кабинет')]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

}
