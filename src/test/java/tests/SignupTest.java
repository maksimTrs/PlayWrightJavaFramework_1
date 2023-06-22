package tests;

import org.junit.jupiter.api.Test;

import static pages.AccountPartitions.CREATE_ACCOUNT;

public class SignupTest extends PlaywrightRunner {

    @Test
    public void signUpTest() {
        homePage.navigate()
                .openAccountPage();


        accountNavigationPage.accountNavigateTo(CREATE_ACCOUNT);
        createAccountPage
                .fillFirstAndLastNameFields("Firstname", "Second-Name")
                .fillPasswordFields("1qazXSW@", "1qazXSW@")
                .fillEmailField("alex@example.com")
                .fillMobileField("7232432432");

        createAccountPage.validatePasswordMessage("Your passwords match!");
        createAccountPage.validateCreateAccountButton();
        createAccountPage.validateCreateAccountHeader("Create an Account");
    }
}
