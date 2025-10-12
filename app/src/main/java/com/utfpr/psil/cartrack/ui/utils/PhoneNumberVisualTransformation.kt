package com.utfpr.psil.cartrack.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.take(PHONE_NUMBER_LENGTH)

        // Constrói a string visual (formatada)
        val formattedText = buildString {
            for (i in originalText.indices) {
                // Adiciona o "(" antes do primeiro dígito
                if (i == 0) append("(")
                append(originalText[i])
                // Adiciona o ") " após o segundo dígito (DDD)
                if (i == 1) append(") ")
                // Adiciona o "-" após o sétimo dígito (quinto do número)
                if (i == 6) append("-")
            }
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // offset: Posição do cursor no texto original (só dígitos)
                val transformedOffset = when {
                    offset == 0 -> 0
                    offset <= 2 -> offset + 1 // Mapeia considerando o "("
                    offset <= 7 -> offset + 4 // Mapeia considerando o "() "
                    else -> offset + 5        // Mapeia considerando o "() " e o "-"
                }
                // Garante que o offset nunca ultrapasse o comprimento do texto formatado
                return transformedOffset.coerceAtMost(formattedText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                // offset: Posição do cursor no texto visual (formatado)
                val originalOffset = when {
                    offset <= 1 -> offset      // Antes ou logo após o "("
                    offset <= 4 -> offset - 1  // Remove o "(" -> CORRIGIDO
                    offset <= 10 -> offset - 4 // Remove o "() "
                    else -> offset - 5         // Remove o "() " e o "-"
                }
                // Garante que o offset nunca seja menor que zero ou maior que o comprimento do texto original
                return originalOffset.coerceIn(0, originalText.length) // CORRIGIDO
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetTranslator)
    }

    companion object {
        const val PHONE_NUMBER_LENGTH = 11
    }
}