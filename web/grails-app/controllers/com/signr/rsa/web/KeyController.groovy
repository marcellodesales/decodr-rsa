package com.signr.rsa.web

import com.signr.rsa.core.Rsa;

class KeyController {

  def keyService

  def index() {
    
    [page: "RSA Keys"]
  }

  def random() {
    def randomRsa = keyService.makeRandom()

    [keyGenLog: randomRsa.getKeysLog(), randomRsa: randomRsa]
  }
}
