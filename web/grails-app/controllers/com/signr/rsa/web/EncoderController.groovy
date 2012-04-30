package com.signr.rsa.web

import com.signr.rsa.core.RsaEncoder;

class EncoderController {

  def keyService
  def encoderService

  def index() {

    []
  }

  def encode() {
    if (!params.n || !params.e || !params.m) {
      render(contentType: "text/json") {
        [operation: "encoder/encode", 
          error: "Can't encode! You need to provide the public key (n,e) and the message (m)."
        ]
      }
    }
    def publicKeyN = Long.valueOf(params.n)
    def publicKeyE = Long.valueOf(params.e)
    def originalMessage = params.m.trim()

    def publicKey = keyService.makePublicKey(publicKeyN, publicKeyE)
    def encoder = encoderService.getEncoder(originalMessage, publicKey)

    render(contentType: "text/json") {
      [originalMessage: originalMessage, encodedMessage: encoder.encryptedMessage,
        publicKey:[n:publicKeyE, e:publicKeyN], 
        at: System.currentTimeMillis(), 
        computationLog: 
          encoder.getLog().each { logEntry ->
            entry: logEntry
          }
      ]
    }
  }
}
