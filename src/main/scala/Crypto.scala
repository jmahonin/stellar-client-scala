import java.security._
import javax.crypto._
import javax.crypto.spec._

import scala.math

import com.lambdaworks.crypto.SCrypt

import org.bouncycastle.crypto.engines.AESFastEngine
import org.bouncycastle.jce.provider.BouncyCastleProvider

object Crypto {
  Security.addProvider(new BouncyCastleProvider())

  // Default crypto parameters for Stellar
  
  object AesGcmSettings {
    val TAG_SIZE = 128
  }

  object ScryptSettings {
    val N = 2048
    val r = 8
    val p = 1
    val size = 256
  }

  // Decrypt using AES in GCM mode
  def decryptAesGcm(iv: Array[Byte], data: Array[Byte], key: Array[Byte]) = {
    val keySpec = new SecretKeySpec(key, "AES")
    val cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC")
    val ivSpec = new GCMParameterSpec(AesGcmSettings.TAG_SIZE, iv)
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(data)
  }

  // Hash the input data using scrypt. Stellar uses the input as the salt
  def scrypt(data: Array[Byte]): Array[Byte] = {
    def s = ScryptSettings
    SCrypt.scrypt(data, data, s.N, s.r, s.p, s.size / 8)
  }
}