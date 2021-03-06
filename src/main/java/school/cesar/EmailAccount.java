package school.cesar;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class EmailAccount {

    private String user;
    private String domain;
    private String password;
    private LocalDate lastPasswordUpdate;

    public EmailAccount(){

    }

    public EmailAccount(String user, String domain, String password, LocalDate lastPasswordUpdate){
        this.user = user;
        this.domain = domain;
        this.password = password;
        this.lastPasswordUpdate = lastPasswordUpdate;
    }

    public void setLastPasswordUpdate(LocalDate lastPasswordUpdate) {
        this.lastPasswordUpdate = lastPasswordUpdate;
    }

    public boolean verifyPasswordExpiration(){
        LocalDate date = LocalDate.now();
        long result =DAYS.between(this.lastPasswordUpdate, date);
        if(result>90){
            return true;
        }else{
            return false;
        }
    }

    public boolean validUser(String user) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\._\\-]{1,15}$");
        Matcher matcher = pattern.matcher(user);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validDomain(String domain) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\._\\-]{1,15}$");
        Matcher matcher = pattern.matcher(domain);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDate getLastPasswordUpdate() {return lastPasswordUpdate;}
}
