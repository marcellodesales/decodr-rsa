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
    RsaEncoder encoder = encoderService.getEncoder(originalMessage, publicKey)

    render(contentType: "text/json") {
      [originalMessage: params.m, publicKey:[n:params.n, e:params.e], 
        at: System.currentTimeMillis(), 
        computationLog: 
          encoder.getLog().each { logEntry ->
            entry: logEntry
          }
      ]
    }
  }
}
