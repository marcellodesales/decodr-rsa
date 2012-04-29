package decodr

import java.text.DecimalFormat;

import com.signr.rsa.core.RsaPrivateKey;
import com.signr.rsa.core.RsaPublicKey;

class DecodrTagLib {

  public static final DecimalFormat FORMAT = new DecimalFormat("0")

  static namespace = "decodr"

  /**
   * The tag for <decodr:rsaKey> with the following attributes: 
   * <li>key: Instance of {@link RsaPublicKey}.
   * <li>[format]: "link". Optional for printing the URL.
   */
  def rsaKey = { attrs, body ->
    def writer = out

    if (attrs.key == null) {
      throwTagError("Tag [rsaKey] is missing required attribute [key]")
    }

    if (attrs.key instanceof RsaPublicKey) {
      if (attrs.format != null && attrs.format == 'link') {
        out << body() << "n=${attrs.key.keyN}&e=${attrs.key.keyE}"

      } else {
        out << body() << "Publick Key (N,E) = (${attrs.key.keyN}, ${attrs.key.keyE})" 
      }
    }

    if (attrs.key instanceof RsaPrivateKey) {
      if (attrs.format != null && attrs.format == 'link') {
        out << body() << "n=${attrs.key.getRSAPublicKey().keyN}&e=${attrs.key.getRSAPublicKey().keyE}" +
          "&d=${FORMAT.format(attrs.key.keyD)}"

      } else {
        out << body() << "Private Key (N,D) = (${attrs.key.getRSAPublicKey().keyN}, " +
          "${FORMAT.format(attrs.key.keyD)})"
      }
    }
  }
}
