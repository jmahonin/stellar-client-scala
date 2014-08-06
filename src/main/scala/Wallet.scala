import org.apache.commons.codec.binary.Base64
import org.bouncycastle.util.encoders.Hex

import scala.util.parsing.json.JSON

object Wallet {

  // Given a username and password, return the resulting (id, key) to retreive
  // and open a wallet
  def deriveIdAndKey(username: String, password: String): (String, String) = {
    // "id" is the scrypted username + password, converted to a hex string
    val user_pass = (username.toLowerCase + password).getBytes("UTF-8") 
    val id = new String(Hex.encode(Crypto.scrypt(user_pass)), "UTF-8")

    // "key" is the scrypted id + username + password
    val id_user_pass = (id + username.toLowerCase + password).getBytes("UTF-8")
    val key = new String(Hex.encode(Crypto.scrypt(id_user_pass)), "UTF-8")
    (id, key)
  }

  // Given an encrypted wallet payload and a key, "open" it
  def open(payload: String, key: String): String = {
    JSON.parseFull(new String(Base64.decodeBase64(payload), "UTF-8")) match {
      case Some(o) => {
        val json = o.asInstanceOf[Map[String, String]]

        // Grab the cipher text and IV
        val cipherText = Base64.decodeBase64(json("cipherText"))
        val iv = Base64.decodeBase64(json("IV"))

        var decrypted = ""

        (json("cipherName"), json("mode")) match {
          case ("aes", "gcm") => new String(Crypto.decryptAesGcm(iv, cipherText, Hex.decode(key)))
        }
      }
      case _ => return null
    }
  }
}