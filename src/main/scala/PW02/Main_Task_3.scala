package PW02

object Main_Task_3 {

  def alphabet = "abcdefghijklmnopqrstuvwxyz"
  def numberOfLetters = 26

  def encode(text : String, key : Int ) : String = {
    var textToEncode = text.toLowerCase()
    var encryptedMessage = ""
    for(letter <- textToEncode.toCharArray){
      var pos = alphabet.indexOf(letter)
      var encryptedPos = (key + pos) % numberOfLetters
      var encryptedLetter = alphabet.charAt(encryptedPos)

      encryptedMessage += encryptedLetter.toString
    }
    return encryptedMessage
  }

  def decode(cipher: String, key: Int): String = {
    var textToDecode = cipher.toLowerCase()
    var decryptedMessage = ""
    for(letter <- textToDecode.toCharArray){
      var pos = alphabet.indexOf(letter)
      var decryptedPos = (pos - key) % numberOfLetters

      if (decryptedPos < 0){
        decryptedPos = alphabet.length + decryptedPos
      }

      var decryptedLetter = alphabet.charAt(decryptedPos)
      decryptedMessage += decryptedLetter.toString
    }
    return decryptedMessage
  }

  def main(args: Array[String]): Unit = {
    println(encode("lorem", 3))
    println(decode("oruhp", 3))
  }
}
