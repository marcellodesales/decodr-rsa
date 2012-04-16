package com.signr.rsa.web

import com.signr.rsa.core.RsaDecoder;

class DecoderController {

  def keyService
  def decoderService

  def index() {
    render "Decode message...make http call to decode?n=YOUR_PUBLIC_N&e=YOUR_PUBLIC_E&d=YOUR_PRIVATE_D&m=ENCODED_MSG"
  }

  def decode() {
    def publicKeyN = Long.valueOf(request.getParameter("n"))
    def publicKeyE = Long.valueOf(request.getParameter("e"))
    def privateKeyD = Long.valueOf(request.getParameter("d"))
    def encodedMessage = request.getParameter("m")

    def privateKey = keyService.makePrivateKey(publicKeyN, publicKeyE, privateKeyD)
    RsaDecoder decoder = decoderService.getDecoder(encodedMessage, privateKey)

    def explanationLog = new StringBuilder()
    explanationLog.append("Here's the generation of the oringal message..."+
      "That's only possible with your private key...<BR><BR>")
    decoder.getLog().each { logEntry ->
      explanationLog.append(logEntry + "<BR>")
    }
    render explanationLog
  }
}
