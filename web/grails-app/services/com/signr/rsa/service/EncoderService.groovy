package com.signr.rsa.service

import com.signr.rsa.core.RsaEncoder;

class EncoderService {

    def getEncodedMessage(originalMessage, publicKey) {
      RsaEncoder encoder = RsaEncoder.newInstance(originalMessage, publicKey)
      return encoder.getEncryptedMessage()
    }

    def getEncoder(originalMessage, publicKey) {
      return RsaEncoder.newInstance(originalMessage, publicKey)
    }
}
