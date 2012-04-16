package com.signr.rsa.web

import com.signr.rsa.core.RsaEncoder;

class EncoderController {

  def keyService
  def encoderService

  def index() {
    render "Encode message...make http call to encode?n=SOMEONES_PUBLIC_N&e=SOMEONE_PUBLIC_E&m=YOUR_MSG_TO_SOMEONE"
  }

  def encode() {
    def publicKeyN = Long.valueOf(request.getParameter("n"))
    def publicKeyE = Long.valueOf(request.getParameter("e"))
    def originalMessage = request.getParameter("m")

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
