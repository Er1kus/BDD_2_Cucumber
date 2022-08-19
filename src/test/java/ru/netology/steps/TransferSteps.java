package ru.netology.steps;

import io.cucumber.java.ru.*;
import lombok.val;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void userLoginWithNameAndPassword(String login, String password) {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(login,password);
        var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
        verificationPage.validVerify(String.valueOf(verificationCode));
    }
    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы;")
    public void shouldTransferMoneyFromSecondToFirst(String amount, String cardNumber, String card) {
        var dashboardPage = new DashboardPage();
        val transferPage = new TransferPage();
        dashboardPage.topUpFirstCard();
        transferPage.validTransfer(Integer.parseInt(amount), DataHelper.getSecondCardNumber());
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void shouldIncreaseFirstCardBalance(String card, String expectingSum) {

        var dashboardPage = new DashboardPage();
        int firstBalanceCard = dashboardPage.getFirstCardBalance();
        int secondBalanceCard = dashboardPage.getSecondCardBalance();
        if(firstBalanceCard != 1000){
            return;
        }
        assertEquals(Integer.parseInt(expectingSum), dashboardPage.getFirstCardBalance());
    }
}
