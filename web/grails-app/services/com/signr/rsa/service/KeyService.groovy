package com.signr.rsa.service

import com.signr.rsa.core.Rsa;
import com.signr.rsa.core.RsaPrivateKey;
import com.signr.rsa.core.RsaPublicKey;

class KeyService {

    def makeRandom() {
      return Rsa.newInstance()
    }

    def makePublicKey(keyN, keyE) {
      return RsaPublicKey.newInstance(keyN, keyE)
    }

    def makePrivateKey(keyN, keyE, keyD) {
      RsaPublicKey publicKey = RsaPublicKey.newInstance(keyN, keyE)
      return RsaPrivateKey.newInstance(publicKey, keyD)
    }
}
