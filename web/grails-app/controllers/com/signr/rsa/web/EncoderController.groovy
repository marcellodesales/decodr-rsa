package com.signr.rsa.web

import com.signr.rsa.core.RsaEncoder;

class EncoderController {

  def keyService
  def encoderService

  def index() {

    []
  }

  def encode() {
    def publicKeyN = Long.valueOf(params.n)
    def publicKeyE = Long.valueOf(params.e)
    def originalMessage = params.m

    def publicKey = keyService.makePublicKey(publicKeyN, publicKeyE)
    RsaEncoder encoder = encoderService.getEncoder(originalMessage, publicKey)

    def explanationLog = new StringBuilder()
    explanationLog.append("Here's the generation of the encoded message..."+
      "Send the encoded message to the owner of the public key...<BR><BR>")
    encoder.getLog().each { logEntry ->
      explanationLog.append(logEntry + "<BR>")
    }
    render explanationLog
  }
}
