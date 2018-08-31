package tests;

import Suporte.Generator;
import Suporte.Screenshot;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.runner.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

//@RunWith(DataDrivenTestRunner)
@DataLoader(filePaths = "informacoesUsuarioTest.csv")

public class informacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp() {
        //Abrindo o Navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Juliana\\Driver\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Navegando para a página
        navegador.get("http://www.juliodelima.com.br/taskit//");

        //clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //identificando o formulário de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //digitar no campo com name "login" que está dentro do formulário de id "signinbox" o texto "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //digitar no campo com name "password" que está dentro do formulário de id "signinbox" o texto"123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //clicar no link com o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //clicar em um link que possui class "me
        navegador.findElement(By.className("me")).click();

        //clicar em um link que possui o texto "MORE ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    //@Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario() {

        //clicar no botão através do xpath //button[@date-target="addmoredata"]
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //identificar o popup do formulário de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //na combo de name "type" escolha a opção phone
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("Phone");

        //No campo contact digitar 999999999
        popupAddMoreData.findElement(By.name("contact")).sendKeys("+5511968358769");

        //clicar no "save" que está no popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //na mensagem de id "toast-container" validar que o texto é "Your contact has been added"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Your contact has been added!", mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario() {
        //clicar no elemento pelo xpath //span[text()="+5511989891122"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+55123123373876\"]/following-sibling::a")).click();

        //confirmar a janela javascript
        navegador.switchTo().alert().accept();

        //validar que a mensagem apresenta foi rest in peace, dear phone!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "/Users/Juliana/Documents//TestReport/" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirarScreenShot(navegador, screenshotArquivo);

        //aguardar até 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //clicar no link com contexto logout
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() {
        //fechar navegador
        //navegador.quit();
    }
}
