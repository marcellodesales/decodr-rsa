package com.signr.rsa.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class RsaTests {

  @Test
  public void testCompleteScenario() {
    Rsa marcellosRsa = Rsa.newInstance();
    assertNotNull("A random RSA object should be constructed.", marcellosRsa);

    RsaPublicKey publicKey = marcellosRsa.getPublicKey();
    assertNotNull("A public key must been constructed.", publicKey);

    String inputMessage = "Marcello de Sales: Passion for Software Engineering";

    RsaEncoder encoder = RsaEncoder.newInstance(inputMessage, publicKey);
    assertNotNull("The encoder for the given RSA must be constructed.", encoder);

    String receivedInputMessage = encoder.getOriginalMessage();
    assertNotNull("The encoder should have received the original message.", receivedInputMessage);
    assertEquals("The received message should be the same as the input.", inputMessage, receivedInputMessage);

    String encodedInputMessage = encoder.getEncryptedMessage();
    assertNotNull("The encoder should have received the original message.", encodedInputMessage);

    RsaPrivateKey privateKey = marcellosRsa.getPrivateKey();
    assertNotNull("A public key must been constructed.", privateKey);

    RsaDecoder decoder = RsaDecoder.newInstance(encodedInputMessage, privateKey);
    assertNotNull("The decoder should be created.", encoder);

    String receivedEncodedMessage = decoder.getEncryptedMessage();
    assertNotNull("The encoder should have received the original message.", receivedEncodedMessage);
    assertEquals("The received message should be the same as the input.", encodedInputMessage, receivedEncodedMessage);

    String decodedMessage = decoder.getOriginalMessage();
    assertNotNull("The encoder should have received the original message.", decodedMessage);
    assertEquals("The received message should be the same as the input.", inputMessage, decodedMessage);
  }

}
