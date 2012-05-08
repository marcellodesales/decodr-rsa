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
    def key = ""
    def bgcolor = attrs.bgcolor ?: "#FFFFFF"

    if (attrs.key != null) {

      if (attrs.key instanceof RsaPublicKey) {

        if (attrs.format != null) {
          if (attrs.format == 'link') {
            out << body() << "n=${attrs.key.keyN}&e=${attrs.key.keyE}"
  
          } else {
            key = "Publick Key (N,E) = (${attrs.key.keyN}, ${attrs.key.keyE})"
            if (attrs.format == 'copy') {
              out << body() << makeClippyIcon(key, bgcolor)
            }
          }

        } else {
          key = "Publick Key (N,E) = (${attrs.key.keyN}, ${attrs.key.keyE})"
          out << body() << key
        }

      } else if (attrs.key instanceof RsaPrivateKey) {

        if (attrs.format != null) { 
          if (attrs.format == 'link') {
            key = "n=${attrs.key.getRSAPublicKey().keyN}&e=${attrs.key.getRSAPublicKey().keyE}" +
              "&d=${FORMAT.format(attrs.key.keyD)}"
            out << body() << key

          } else {
            key = "Private Key (N,D) = (${attrs.key.getRSAPublicKey().keyN}, " +
              "${FORMAT.format(attrs.key.keyD)})"
            if (attrs.format == 'copy') {
              out << body() << makeClippyIcon(key, bgcolor)
            }
          }

        } else {
          key = "Private Key (N,D) = (${attrs.key.getRSAPublicKey().keyN}, " +
            "${FORMAT.format(attrs.key.keyD)})"
          out << body() << key
        }

      } else {
        throwTagError("Tag [rsaKey] needs to have an attribute [key] with instance of PublicKey or PrivateKey")
      }

    } else if (attrs.n && attrs.e) {
      key = "Publick Key (N,E) = (${attrs.n}, ${attrs.e})"
      out << body() << "${key}"

    } else if (attrs.n && attrs.d) {
      key = "Private Key (N,D) = (${attrs.n}, ${attrs.d})"
      out << body() << "${key}"

    } else {
      throwTagError("Tag [rsaKey] is missing the required attribute [d and e]")
    }
  }

  /**
   * <p>May 8, 2012 12:52:48 AM</p> 
   * @param key the key text.
   * @param bgcolor the background color used.
   * @return The clippy swf object for copy-and-paste purposes with the value of the key.
   */
  def makeClippyIcon(key, bgcolor) {
    def swfPath = g.createLinkTo(dir:'swf/', file:'clippy.swf')
    return """
      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
              width="110"
              height="14"
              id="clippy" >
      <param name="movie" value="${swfPath}"/>
      <param name="allowScriptAccess" value="always" />
      <param name="quality" value="high" />
      <param name="scale" value="noscale" />
      <param NAME="FlashVars" value="text=${key}">
      <param name="bgcolor" value="${bgcolor}">
      <embed src="${swfPath}"
             width="110"
             height="14"
             name="clippy"
             quality="high"
             allowScriptAccess="always"
             type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer"
             FlashVars="text=${key}"
             bgcolor="${bgcolor}"
      />
      </object>
        """
  }

}
