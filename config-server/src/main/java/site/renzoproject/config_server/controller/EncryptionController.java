package site.renzoproject.config_server.controller;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Use this on production
//@RestController
//@RequestMapping("/crypto")
public class EncryptionController {

//    private final TextEncryptor encryptor;
//
//    public EncryptionController(TextEncryptor encryptor) {
//        this.encryptor = encryptor;
//    }
//
//    @PostMapping("/encrypt")
//    public String encrypt(@RequestBody String plainText) {
//        return encryptor.encrypt(plainText);
//    }
//
//    @PostMapping("/decrypt")
//    public String decrypt(@RequestBody String cipherText) {
//        return encryptor.decrypt(cipherText);
//    }
}
