package school.cesar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import school.cesar.utils.EmailAccountBuilder;
import school.cesar.utils.EmailBuilder;
import school.cesar.utils.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailClientTest{

    EmailBuilder emailBuilder;
    EmailAccountBuilder emailAccountBuilder;

    EmailClient emailClient;

    @Mock EmailService service;


    private Collection<String> to;
    private Collection<String> cc;
    private Collection<String> bcc;
    String emailValid = "test@teste.com";
    String emailInvalid = "test@.c";


    @BeforeEach
    public void setUpTest(){
        this.emailBuilder = new EmailBuilder();
        this.emailAccountBuilder = new EmailAccountBuilder();
        this.emailClient = new EmailClient();
        this.to = new ArrayList<String>();
        this.cc = new ArrayList<String>();
        this.bcc = new ArrayList<String>();
    }

    @Test
    public void isValidEmailTest_withEmailValid_True(){
        this.to.add(emailValid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                                      .setCc(cc)
                                      .setFrom(emailValid)
                                      .setCreationDate(Instant.now())
                                      .setTo(to)
                                      .setMessage("message")
                                      .setSubject("subject")
                                      .build();
        assertTrue(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithoutCreationDate_False(){
        this.to.add(emailInvalid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(null)
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        assertFalse(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithEmailTOInvalid_False(){
        this.to.add(emailInvalid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        assertFalse(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithEmailCCInvalid_False(){
        this.to.add(emailValid);
        this.bcc.add(emailValid);
        this.cc.add(emailInvalid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        assertFalse(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithEmailBCCInvalid_False(){
        this.to.add(emailValid);
        this.bcc.add(emailInvalid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        assertFalse(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithFromInvalid_False(){
        this.to.add(emailValid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailInvalid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        assertFalse(emailClient.isValidEmail(emailTest));
    }

    @Test
    public void isValidEmailTest_WithPasswordValid_Sucess() throws RuntimeException {
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                .setDomain("UserDamain")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();

        Assertions.assertDoesNotThrow( () -> {
            emailClient.emailList(emailAccountTest);
        });
        
    }

    @Test
    public void emailListTest_WithPasswordInvalid_Sucess(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                .setDomain("UserDomain")
                .setPassword("12345")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        this.to.add(emailValid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        Collection<Email> resultMock = new ArrayList<Email>();
        resultMock.add(emailTest);
        when(service.emailList(any(EmailAccount.class))).thenReturn(resultMock);
        emailClient.setEmailService(service);
        assertEquals(resultMock,emailClient.emailList(emailAccountTest));

    }

    @Test
    public void emailListTest_WithPasswordExpiration_Fail() throws RuntimeException{
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                .setDomain("UserDomain")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now().plusDays(100))
                .build();
        assertThrows(RuntimeException.class, () -> {
            emailClient.emailList(emailAccountTest);
        });
    }

    @Test
    public void emailListTest_WithPasswordInvalid_Fail() throws RuntimeException{
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                                                           .setDomain("UserDomain")
                                                           .setPassword("12345")
                                                           .setLastPasswordUpdate(LocalDate.now())
                                                           .build();
        assertThrows(RuntimeException.class, () -> {
            emailClient.emailList(emailAccountTest);
        });
    }

    @Test
    public void sendEmailTest_WithEmailValid_Sucess(){
        this.to.add(emailValid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();

        when(service.sendEmail(any(Email.class))).thenReturn(true);
        emailClient.setEmailService(service);
        Assertions.assertDoesNotThrow( () -> {
            emailClient.sendEmail(emailTest);
        });
    }

    @Test
    public void sendEmailTest_WithEmailInvalid_Fail() throws RuntimeException{
        this.to.add(emailInvalid);
        this.bcc.add(emailValid);
        this.cc.add(emailValid);
        Email emailTest = emailBuilder.setBcc(bcc)
                .setCc(cc)
                .setFrom(emailValid)
                .setCreationDate(Instant.now())
                .setTo(to)
                .setMessage("message")
                .setSubject("subject")
                .build();
        Assertions.assertThrows(RuntimeException.class, () -> {
            emailClient.sendEmail(emailTest);
        });
    }

    @Test
    public void createAccountTest_WithUserDomainPasswordValid_True(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("12")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailClient.createAccount(emailAccountTest));
    }

    @Test
    public void createAccountTest_WithUserInvalid_DomainAndPasswordValid_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("!1!@#2")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailClient.createAccount(emailAccountTest));
    }

    @Test
    public void createAccountTest_WithDomainInvalid_UserAndPasswordValid_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("User")
                .setDomain("UserDamain@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailClient.createAccount(emailAccountTest));
    }

    @Test
    public void createAccountTest_WithPasswordInvalid_UserAndDomainValid_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("User")
                .setDomain("UserDamain")
                .setPassword("12345")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailClient.createAccount(emailAccountTest));
    }


}
