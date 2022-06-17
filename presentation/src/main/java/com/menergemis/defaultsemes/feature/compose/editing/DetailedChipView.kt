/*
 * Copyright (C) 2019 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.menergemis.defaultsemes.feature.compose.editing

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import com.menergemis.defaultsemes.R
import com.menergemis.defaultsemes.common.util.Colors
import com.menergemis.defaultsemes.common.util.extensions.dpToPx
import com.menergemis.defaultsemes.common.util.extensions.setBackgroundTint
import com.menergemis.defaultsemes.common.util.extensions.setTint
import com.menergemis.defaultsemes.common.util.extensions.viewBinding
import com.menergemis.defaultsemes.databinding.ContactChipDetailedBinding
import com.menergemis.defaultsemes.injection.appComponent
import com.menergemis.defaultsemes.model.Recipient
import javax.inject.Inject

class DetailedChipView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    @Inject lateinit var colors: Colors

    private val binding = viewBinding(ContactChipDetailedBinding::inflate)

    init {
        if (!isInEditMode) {
            appComponent.inject(this)
            visibility = View.GONE
        }

        setOnClickListener { hide() }
        setBackgroundResource(R.drawable.rounded_rectangle_2dp)

        elevation = 8.dpToPx(context) .toFloat()
//        updateLayoutParams<LinearLayout.LayoutParams> { setMargins(8.dpToPx(context) ) }

        isFocusable = true
        isFocusableInTouchMode = true
    }

    fun setRecipient(recipient: Recipient) {
        binding.avatar.setRecipient(recipient)
        binding.name.text = recipient.contact?.name?.takeIf { it.isNotBlank() } ?: recipient.address
        binding.info.text = recipient.address

        colors.theme(recipient).let { theme ->
            setBackgroundTint(theme.theme)
            binding.name.setTextColor(theme.textPrimary)
            binding.info.setTextColor(theme.textTertiary)
            binding.delete.setTint(theme.textPrimary)
        }
    }

    fun show() {
        startAnimation(AlphaAnimation(0f, 1f).apply { duration = 200 })

        visibility = View.VISIBLE
        requestFocus()
        isClickable = true
    }

    fun hide() {
        startAnimation(AlphaAnimation(1f, 0f).apply { duration = 200 })

        visibility = View.GONE
        clearFocus()
        isClickable = false
    }

    fun setOnDeleteListener(listener: (View) -> Unit) {
        binding.delete.setOnClickListener(listener)
    }

}
