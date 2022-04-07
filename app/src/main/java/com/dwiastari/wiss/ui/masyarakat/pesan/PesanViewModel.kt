package com.dwiastari.wiss.ui.masyarakat.pesan

import androidx.lifecycle.ViewModel
import com.dwiastari.wiss.repository.MasyarakatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PesanViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
}