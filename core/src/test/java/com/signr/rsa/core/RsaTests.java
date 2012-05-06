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

    String inputMessage = "Marcello Alves de Sales Junior";

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

  @Test
  public void testWithSingleAndRepeatedCharactersWith0() {
    RsaPublicKey rpk = RsaPublicKey.newInstance(25985731L, 5L);
    assertNotNull("The key must be created", rpk);

    assertEquals("The value of the key must exist", 25985731L, rpk.getKeyN());
    assertEquals("The value of the key must exist", 5L, rpk.getKeyE());

    String msg = "ddsokdos oskdoskdosk oksd";
    RsaEncoder encoder = RsaEncoder.newInstance(msg, rpk);
    assertNotNull(encoder.getEncryptedMessage());

    assertEquals("The value of the key must exist", msg, encoder.getOriginalMessage());
    assertEquals("The value of the key must exist", 5L, rpk.getKeyE());

    RsaPrivateKey rppk = RsaPrivateKey.getInstance(rpk, 10389053L);
    RsaDecoder decoder = RsaDecoder.newInstance(encoder.getEncryptedMessage(), rppk);
    String decoded = decoder.getOriginalMessage();

    assertEquals("The original message was not decoded correctly.", msg, decoded);

  }

}
