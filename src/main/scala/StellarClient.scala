import dispatch._, Defaults._
import scala.util.parsing.json._

object StellarClient {
  def main(args: Array[String]) = {

    if (args.length < 2) {
      println("Expected arguments: <username> <password>")
      System.exit(1)
    }
    
    val (id, key) = Wallet.deriveIdAndKey(args(0), args(1))
    
    val wallet = loadWallet(id)
    val result = JSON.parseFull(wallet()).map {
      _.asInstanceOf[Map[String,Any]]
    }

    val data = result.get("data").asInstanceOf[Map[String,String]]
    val mainData = data.get("mainData").get.asInstanceOf[String]
    val keyChainData = data.get("keychainData").get.asInstanceOf[String]

    val decryptedMainData = Wallet.open(mainData, key)
    println(decryptedMainData)
    
    val decryptedKeyChainData = Wallet.open(keyChainData, key)
    println(decryptedKeyChainData)
  }

  def loadWallet(id: String) = {
    val query = url("https://wallet.stellar.org/wallets/show")
      .setContentType("application/json", "UTF-8")
      .POST
    
    val postedQuery = query << s"""{"id":"$id"}"""

    Http(postedQuery OK as.String)
  }
}