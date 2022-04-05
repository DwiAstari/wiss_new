package com.dwiastari.wiss.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class EmptyTextWatcher(val layout: TextInputLayout, val message: String): TextWatcher {
    
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("Not yet implemented")
    }
    
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s.isNullOrEmpty()){
            layout.error = message
        } else {
            layout.error = null
        }
    }
    
    override fun afterTextChanged(s: Editable?) {
        TODO("Not yet implemented")
    }
}