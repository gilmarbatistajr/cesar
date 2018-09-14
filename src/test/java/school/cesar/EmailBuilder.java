package school.cesar;

import java.time.Instant;
import java.util.Collection;

public class EmailBuilder {

    private Instant creationDate;
    private String from;
    private Collection<String> to;
    private Collection<String> cc;
    private Collection<String> bcc;
    private String subject;
    private String message;

    public EmailBuilder setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public EmailBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public EmailBuilder setTo(Collection<String> to) {
        this.to = to;
        return this;
    }

    public EmailBuilder setCc(Collection<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailBuilder setBcc(Collection<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public Email build(){
        Email email = new Email();
        email.setCreationDate(this.creationDate);
        email.setFrom(this.from);
        email.setTo(this.to);
        email.setCc(this.cc);
        email.setBcc(this.bcc);
        email.setSubject(this.subject);
        email.setMessage(this.message);
        return email;
    }


}
