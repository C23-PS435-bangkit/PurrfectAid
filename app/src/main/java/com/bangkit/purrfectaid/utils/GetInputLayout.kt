package com.bangkit.purrfectaid.utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Yosua on 14/06/2023
 */
fun TextInputEditText.getInputLayout() : TextInputLayout {
    return this.parent.parent as TextInputLayout
}