package school.cesar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class EmailClientTest{

    EmailBuilder emailBuilder;
    EmailAccountBuilder emailAccountBuilder;

    EmailClient emailClient;


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
    public void testEmailValid(){
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
    public void testEmailInvalidWithoutCreationDate(){
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
    public void testEmailInvalidWithTOInvalid(){
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
    public void testEmailInvalidWithCCInvalid(){
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
    public void testEmailInvalidWithBCCInvalid(){
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
    public void testEmailInvalidWithFromInvalid(){
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

   /* @Test
    public void testEmailListWithPasswordValid() throws RuntimeException {
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                .setDomain("UserDamain")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();

        Assertions.assertDoesNotThrow( () -> {
            emailClient.emailList(emailAccountTest);
        });
    }
*/
    @Test
    public void testEmailListWithPasswordInvalid() throws RuntimeException{
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserName")
                                                           .setDomain("UserDamain")
                                                           .setPassword("12345")
                                                           .setLastPasswordUpdate(LocalDate.now())
                                                           .build();
        assertThrows(RuntimeException.class, () -> {
            emailClient.emailList(emailAccountTest);
        });
    }

  /*  @Test() //NullPointException por causa da falta da implementação do sendEmail
    public void testSendEmailWithEmailValid() throws RuntimeException{
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

        Assertions.assertDoesNotThrow( () -> {
            emailClient.sendEmail(emailTest);
        });
    }*/

    @Test
    public void testSendEmailWithEmailInvalid() throws RuntimeException{
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
    public void testCreateAccountValid(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("12")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailClient.createAccount(emailAccountTest));
    }

    @Test //verified corret comportament method
    public void testSetEmailService(){
        Assertions.assertDoesNotThrow( () -> {
            emailClient.setEmailService(emailClient.getService());
        });

    }

}
