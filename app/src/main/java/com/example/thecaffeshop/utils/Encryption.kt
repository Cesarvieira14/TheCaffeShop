package com.example.thecaffeshop.utils

import java.math.BigInteger
import java.security.MessageDigest

class Encryption() {
    companion object {
        // Reference: https://stackoverflow.com/a/64171625/7687307
        public fun hashString(text: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(text.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}
