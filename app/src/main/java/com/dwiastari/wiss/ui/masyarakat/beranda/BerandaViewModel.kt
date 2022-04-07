package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.lifecycle.ViewModel
import com.dwiastari.wiss.repository.MasyarakatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
}