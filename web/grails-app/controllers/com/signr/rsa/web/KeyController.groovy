package com.signr.rsa.web

import com.signr.rsa.core.Rsa;

class KeyController {

  def keyService

  def index() {
    render "This is the for RSA keys..."
  }

  def random() {
    def randomRsa = keyService.makeRandom()
    def explanationLog = new StringBuilder()
    explanationLog.append("Generating a random pair of keys...<BR><BR>")
    randomRsa.getKeysLog().each { logEntry ->
      explanationLog.append(logEntry + "<BR>")
    }
    render explanationLog
  }
}
