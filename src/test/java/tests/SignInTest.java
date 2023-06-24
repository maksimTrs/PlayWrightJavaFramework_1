package tests;

import org.junit.jupiter.api.Test;

import static pages.AccountPartitions.SIGN_IN;
import static services.PropertyReader.getTestDataFromBundle;

public class SignInTest extends PlaywrightRunner {
    @Test
    public void signUpTest() {
        homePage.navigate()
                .openAccountPage();


        accountNavigationPage.accountNavigateTo(SIGN_IN);
        signInPage.doSignIn(getTestDataFromBundle("USER_EMAIL"), getTestDataFromBundle("USER_PASSWORD"));

        signInPage.validateAccountSignInVerifyWindow(getTestDataFromBundle("USER_EMAIL"));
    }
}