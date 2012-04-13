package com.signr.rsa.key

import com.signr.rsa.core.Rsa;

class KeyController {

  def index() {
    render "This is the for RSA keys..."
  }

  def random() {
    def randomRsa = Rsa.newInstance()
    def logs = new StringBuilder()
    logs.append("Generating a random pair of keys...<BR><BR>")
    randomRsa.getKeysLog().each { logEntry ->
      logs.append(logEntry + "<BR>")
    }
    render logs
  }
}
