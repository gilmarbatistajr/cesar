package school.cesar;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailClient {

    private EmailService emailService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean isValidAddress(String emailAddress) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(Email email) {
        List<String> listTo = new ArrayList(email.getTo());
        List<String> listBcc = new ArrayList(email.getBcc());
        List<String> listCC = new ArrayList<>(email.getCc());
        if (email.getCreationDate() != null) { //verified Create Date
            if (isValidAddress(email.getFrom())==true) { //verified from email
                for (String emailTo : listTo) {
                    if (isValidAddress(emailTo.toString()) == true) { //verified all to email
                        for (String emailBcc : listBcc) {
                            if (isValidAddress(emailBcc.toString()) == true) { //verified all bcc email
                                for (String emailCc : listCC) {
                                    if (isValidAddress(emailCc.toString()) == true) { //verified all cc email
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Collection<Email> emailList(EmailAccount account){
        if(account.getPassword().length()>6 ||account.verifyPasswordExpiration()==false){
            return emailService.emailList(account);
        }else{
            throw new RuntimeException();
        }

    }

    public void sendEmail(Email email){
        if(isValidEmail(email)==false||emailService.sendEmail(email)==false){
                throw new RuntimeException();
        }
            emailService.sendEmail(email);

    }

    public boolean createAccount(EmailAccount account){
        if(account.validUser(account.getUser())==false){
            return false;
        }
        if(account.validDomain(account.getDomain())==false){
            return false;
        }
        if (account.getPassword().length()<6){
            return false;
        }
        account.setLastPasswordUpdate(LocalDate.now());
        EmailAccount newAccount = new EmailAccount(account.getUser(),
                                                   account.getDomain(),
                                                   account.getPassword(),
                                                   account.getLastPasswordUpdate());
        return true;
    }

    public EmailService getService(){
        return this.emailService;
    }
}
