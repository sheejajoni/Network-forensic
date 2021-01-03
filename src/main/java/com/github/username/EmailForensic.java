package com.github.username;
import java.util.Properties;
 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
 
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
  */
public class EmailForensic {
 
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param keyword
     */
    public void searchEmail(String host, String port, String userName,
            String password, final String keyword) {
        Properties properties = new Properties();
 
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
 
        Session session = Session.getDefaultInstance(properties);
 
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains(keyword)) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };
 
            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchCondition);
 
            for (int i = 0; i < foundMessages.length; i++) {
                Message message = foundMessages[i];
                String subject = message.getSubject();
                System.out.println("Found message #" + i + ": " + subject);
            }
 
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
    }


/*@Override
    public boolean addressMatch(Message message) {
        try {
            Address[] fromAddress = message.getFrom();
            if (fromAddress != null && fromAddress.length > 0) {
                if (fromAddress[0].toString().contains(fromEmail)) {
                    return true;
                }
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
         
        return false;
    }

@Override
    public boolean dateMatch(Message message) {
        try {
            if (message.getSentDate().after(afterDate)) {
                return true;
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

@Override
    public boolean contentMatch(Message message) {
        try {
            String contentType = message.getContentType().toLowerCase();
            if (contentType.contains("text/plain")
                    || contentType.contains("text/html")) {
                String messageContent = message.getContent().toString();
                if (messageContent.contains(content)) {
                    return true;
                }
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }*/
 
    /**
     * Test this program with a Gmail's account
     */
    public static void main(String[] args) {
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "your_email";
        String password = "your_password";
        EmailForensic searcher = new EmailForensic();
        String keyword = "attack";
        searcher.searchEmail(host, port, userName, password, keyword);
    }
 
}