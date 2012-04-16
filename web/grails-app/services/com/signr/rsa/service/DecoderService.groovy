package com.signr.rsa.service

import com.signr.rsa.core.RsaDecoder;

class DecoderService {

  def getDecoder(encodedMessage, privatekey) {
    return RsaDecoder.newInstance(encodedMessage, privatekey)
  }
}
