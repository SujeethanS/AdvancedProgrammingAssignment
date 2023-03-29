package com.automation;

import com.dto.user.request.CreateNewUserReq;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RegisterTest {

    @PostConstruct
    public void initRegisterPage() {
        PageFactory.initElements(webDriver, this);
    }

    @Value("${app.url.register}")
    private String url;

    @FindBy(how = How.NAME, name = "firstName")
    public WebElement txtFirstName;
    @FindBy(how = How.NAME, name = "lastName")
    public WebElement txtLastName;
    @FindBy(how = How.NAME, name = "email")
    public WebElement txtEmail;
    @FindBy(how = How.NAME, name = "nic")
    public WebElement txtNic;
    @FindBy(how = How.NAME, name = "mobileNumber")
    public WebElement txtMobile;
    @FindBy(how = How.NAME, name = "dob")
    public WebElement txtDob;
    @FindBy(how = How.NAME, name = "installPlan")
    public WebElement txtPlan;
    @FindBy(how = How.NAME, using = "register")
    public WebElement lnkRegister;

    @Autowired
    private WebDriver webDriver;

    public void register(CreateNewUserReq createNewUserReq) {
        webDriver.navigate().to(url);

        txtFirstName.sendKeys(createNewUserReq.getFirstName());
        txtLastName.sendKeys(createNewUserReq.getLastName());
        txtEmail.sendKeys(createNewUserReq.getUserEmail());
        txtNic.sendKeys(createNewUserReq.getNic());
        txtMobile.sendKeys(createNewUserReq.getUserMobileNumber());
        txtDob.sendKeys(createNewUserReq.getDob());
        txtPlan.sendKeys(String.valueOf(createNewUserReq.getInstallPlan()));

        clickRegister();
    }

    public void clickRegister() {
        lnkRegister.click();
        System.out.println("Register Success!");
    }
}
