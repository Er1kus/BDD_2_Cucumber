package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement sumForTransfer = $("[data-test-id='amount'] input");
    private SelenideElement fromCardNumber = $("[data-test-id='from'] input");
    private SelenideElement toCardNumber = $("[data-test-id='to']");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorPopup = $("[data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void transferAs(int amount, String number) {
        sumForTransfer.setValue(String.valueOf(amount));
        fromCardNumber.setValue(number);
        transferButton.click();
    }

    public DashboardPage validTransfer(int amount, DataHelper.TransferCode code) {
        transferAs(Integer.parseInt(String.valueOf(amount)), code.getNumber());
        return new DashboardPage();
    }

    public void invalidTransfer(int amount, DataHelper.TransferCode code) {
        transferAs(Integer.parseInt(String.valueOf(amount)), code.getNumber());
        errorPopup.shouldHave(text("Ошибка " +
                "Ошибка! Произошла ошибка"));
    }
}
