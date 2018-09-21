package school.cesar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.cesar.utils.EmailAccountBuilder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailAccountTest {


    EmailAccountBuilder emailAccountBuilder;


    @BeforeEach
    public void setUpTest(){
        this.emailAccountBuilder = new EmailAccountBuilder();

    }

    @Test
    public void userValidTest_True(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void userInvalidTest_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("A!@#A")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void domainValidTest_True(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("Domain_Val-i.d")
                .setDomain("DomainValid")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void domainInvalidTest_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("Domain!Val@i.d")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void passwordExpirationTest_True(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now().plusDays(100))
                .build();
        assertTrue(emailAccountTest.verifyPasswordExpiration());

    }

    @Test
    public void passwordExpirationTest_False(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now().minusDays(80))
                .build();
        assertFalse(emailAccountTest.verifyPasswordExpiration());

    }

    @Test
    public void setLastPasswordUpdateTest_Sucess(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("Domain!Val@i.d")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        emailAccountTest.setLastPasswordUpdate(LocalDate.now().plusDays(1));
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        assertEquals(emailAccountTest.getLastPasswordUpdate(), tomorrow);
    }
}
