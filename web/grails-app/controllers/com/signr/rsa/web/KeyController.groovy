package com.signr.rsa.web

import java.text.DecimalFormat;

import com.signr.rsa.core.Rsa;

class KeyController {

  public static final DecimalFormat FORMAT = new DecimalFormat("0")

  def keyService

  def index() {

    [page: "RSA Keys"]
  }

  def random() {
    def randomRsa = keyService.makeRandom()

    def publicKeyMap = [n:randomRsa.publicKey.keyN, 
      e: randomRsa.publicKey.keyE]
    def privateKeyMap = [d: FORMAT.format(randomRsa.privateKey.keyD)] << publicKeyMap 

    [privateKeyMap: privateKeyMap, publicKeyMap: publicKeyMap, randomRsa: randomRsa]
  }
}
