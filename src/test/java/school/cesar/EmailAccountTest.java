package school.cesar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

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
    public void testUserValid(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void testUserInvalid(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("A!@#A")
                .setDomain("UserDamain1")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void testDomainValid(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("Domain_Val-i.d")
                .setDomain("DomainValid")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertTrue(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void testDomainInvalid(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("Domain!Val@i.d")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now())
                .build();
        assertFalse(emailAccountTest.validUser(emailAccountTest.getUser()));
    }

    @Test
    public void testPasswordExpirationTrue(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now().plusDays(100))
                .build();
        assertTrue(emailAccountTest.verifyPasswordExpiration());

    }

    @Test
    public void testPasswordExpirationFalse(){
        EmailAccount emailAccountTest = emailAccountBuilder.setUser("UserValid")
                .setDomain("!@#$")
                .setPassword("123456")
                .setLastPasswordUpdate(LocalDate.now().plusDays(89))
                .build();
        assertFalse(emailAccountTest.verifyPasswordExpiration());

    }

    @Test
    public void testSetLastPasswordUpdate(){
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
