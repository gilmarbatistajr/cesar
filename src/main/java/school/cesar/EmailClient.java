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
        }
        return false;
    }

    public boolean isValidCreationDate(Email email){
        if (email.getCreationDate() == null) {
            return false;
        }
        return true;
    }

    public boolean isValidEmail(Email email) {
        List<String> listTo = new ArrayList(email.getTo());
        List<String> listBcc = new ArrayList(email.getBcc());
        List<String> listCC = new ArrayList<>(email.getCc());
        if (isValidCreationDate(email) == false) { //verified Create Date
            return false;
        }

        if (isValidAddress(email.getFrom()) == false) { //verified from email
            return false;
        }

        for (String emailTo : listTo) {//verified all to email
            if (isValidAddress(emailTo.toString()) == false) {
                return false;
            }
        }

        for (String emailBcc : listBcc) {//verified all bcc email
            if (isValidAddress(emailBcc.toString()) == false) {
                return false;
            }
        }

        for (String emailCc : listCC) {
            if (isValidAddress(emailCc.toString()) == false) { //verified all cc email
                return false;
            }
        }

        return true;
    }

    public Collection<Email> emailList(EmailAccount account){
        if(account.getPassword().length()>6 ||account.verifyPasswordExpiration()==false){
            return emailService.emailList(account);
        }
        throw new EmailFailException("Email Fail Exception");

    }

    public void sendEmail(Email email){
        if(isValidEmail(email)==false||emailService.sendEmail(email)==false){
                throw new EmailFailException("Email Fail Exception");
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
        new EmailAccount(account.getUser(),account.getDomain(),account.getPassword(),account.getLastPasswordUpdate());
        return true;
    }

}
