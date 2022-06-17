/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
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
package com.menergemis.defaultsemes.injection

import com.menergemis.defaultsemes.common.QKApplication
import com.menergemis.defaultsemes.common.QkDialog
import com.menergemis.defaultsemes.common.util.QkChooserTargetService
import com.menergemis.defaultsemes.common.widget.AvatarView
import com.menergemis.defaultsemes.common.widget.PagerTitleView
import com.menergemis.defaultsemes.common.widget.PreferenceView
import com.menergemis.defaultsemes.common.widget.QkEditText
import com.menergemis.defaultsemes.common.widget.QkSwitch
import com.menergemis.defaultsemes.common.widget.QkTextView
import com.menergemis.defaultsemes.common.widget.RadioPreferenceView
import com.menergemis.defaultsemes.feature.backup.BackupController
import com.menergemis.defaultsemes.feature.blocking.BlockingController
import com.menergemis.defaultsemes.feature.blocking.manager.BlockingManagerController
import com.menergemis.defaultsemes.feature.blocking.messages.BlockedMessagesController
import com.menergemis.defaultsemes.feature.blocking.numbers.BlockedNumbersController
import com.menergemis.defaultsemes.feature.compose.editing.DetailedChipView
import com.menergemis.defaultsemes.feature.conversationinfo.injection.ConversationInfoComponent
import com.menergemis.defaultsemes.feature.settings.SettingsController
import com.menergemis.defaultsemes.feature.settings.about.AboutController
import com.menergemis.defaultsemes.feature.settings.swipe.SwipeActionsController
import com.menergemis.defaultsemes.feature.themepicker.injection.ThemePickerComponent
import com.menergemis.defaultsemes.feature.widget.WidgetAdapter
import com.menergemis.defaultsemes.injection.android.ActivityBuilderModule
import com.menergemis.defaultsemes.injection.android.BroadcastReceiverBuilderModule
import com.menergemis.defaultsemes.injection.android.ServiceBuilderModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class,
    BroadcastReceiverBuilderModule::class,
    ServiceBuilderModule::class])
interface AppComponent {

    fun conversationInfoBuilder(): ConversationInfoComponent.Builder
    fun themePickerBuilder(): ThemePickerComponent.Builder

    fun inject(application: QKApplication)

    fun inject(controller: AboutController)
    fun inject(controller: BackupController)
    fun inject(controller: BlockedMessagesController)
    fun inject(controller: BlockedNumbersController)
    fun inject(controller: BlockingController)
    fun inject(controller: BlockingManagerController)
    fun inject(controller: SettingsController)
    fun inject(controller: SwipeActionsController)

    fun inject(dialog: QkDialog)

    fun inject(service: WidgetAdapter)

    /**
     * This can't use AndroidInjection, or else it will crash on pre-marshmallow devices
     */
    fun inject(service: QkChooserTargetService)

    fun inject(view: AvatarView)
    fun inject(view: DetailedChipView)
    fun inject(view: PagerTitleView)
    fun inject(view: PreferenceView)
    fun inject(view: RadioPreferenceView)
    fun inject(view: QkEditText)
    fun inject(view: QkSwitch)
    fun inject(view: QkTextView)

}
