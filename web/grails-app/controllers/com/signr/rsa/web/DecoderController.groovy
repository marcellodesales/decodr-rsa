package com.signr.rsa.web

import com.signr.rsa.core.RsaDecoder;

class DecoderController {

  def keyService
  def decoderService

  def index() {
    []
  }

  def decode() {
    if (!params.n || !params.e || !params.d || !params.m) {
      render(contentType: "text/json") {
        [operation: "decoder/decode", 
          error: "Can't encode! You need to provide the public key (n,e), " +
            "private key (n,d) and the message (m)."
        ]
      }
    }
    def publicKeyN = Long.valueOf(params.n)
    def publicKeyE = Long.valueOf(params.e)
    def privateKeyD = Long.valueOf(params.d)
    def encodedMessage = params.m.trim()

    def privateKey = keyService.makePrivateKey(publicKeyN, publicKeyE, privateKeyD)
    def decoder = decoderService.getDecoder(encodedMessage, privateKey)

    render(contentType: "text/json") {
      [encodedMessage: encodedMessage, decodedMessage: decoder.originalMessage,
        publicKey:[n:publicKeyE, e:publicKeyN], privateKey: [n: publicKeyN, d:privateKeyD],
        at: System.currentTimeMillis(), 
        computationLog: 
          decoder.getLog().each { logEntry ->
            entry: logEntry
          }
      ]
    }
  }
}
