package dmzjapiexplore

import java.io.File


object DMZJDecrypter {
    fun run(ciphertext: String, writeTo: String?){
        val decrypted = RSAUtils.decryptWithPrivateKeyBlock(ciphertext)
        writeTo?.let {
            File(it).writeBytes(decrypted)
        }
        println("Decrypted response:\n${String(decrypted)}")
    }

}