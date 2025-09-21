package site.renzoproject.config_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;


// Use this on production
//@Configuration
public class EncryptionConfig {
//    @Bean
//    public TextEncryptor textEncryptor() {
//        // Replace with a secure password and salt in production
//        return Encryptors.text("password123", "deadbeef");
//    }
}
