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
package com.menergemis.defaultsemes.injection.android

import com.menergemis.defaultsemes.feature.backup.BackupActivity
import com.menergemis.defaultsemes.feature.blocking.BlockingActivity
import com.menergemis.defaultsemes.feature.compose.ComposeActivity
import com.menergemis.defaultsemes.feature.compose.ComposeActivityModule
import com.menergemis.defaultsemes.feature.contacts.ContactsActivity
import com.menergemis.defaultsemes.feature.contacts.ContactsActivityModule
import com.menergemis.defaultsemes.feature.conversationinfo.ConversationInfoActivity
import com.menergemis.defaultsemes.feature.gallery.GalleryActivity
import com.menergemis.defaultsemes.feature.gallery.GalleryActivityModule
import com.menergemis.defaultsemes.feature.main.MainActivity
import com.menergemis.defaultsemes.feature.main.MainActivityModule
import com.menergemis.defaultsemes.feature.notificationprefs.NotificationPrefsActivity
import com.menergemis.defaultsemes.feature.notificationprefs.NotificationPrefsActivityModule
import com.menergemis.defaultsemes.feature.plus.PlusActivity
import com.menergemis.defaultsemes.feature.plus.PlusActivityModule
import com.menergemis.defaultsemes.feature.qkreply.QkReplyActivity
import com.menergemis.defaultsemes.feature.qkreply.QkReplyActivityModule
import com.menergemis.defaultsemes.feature.scheduled.ScheduledActivity
import com.menergemis.defaultsemes.feature.scheduled.ScheduledActivityModule
import com.menergemis.defaultsemes.feature.settings.SettingsActivity
import com.menergemis.defaultsemes.injection.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PlusActivityModule::class])
    abstract fun bindPlusActivity(): PlusActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBackupActivity(): BackupActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ComposeActivityModule::class])
    abstract fun bindComposeActivity(): ComposeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ContactsActivityModule::class])
    abstract fun bindContactsActivity(): ContactsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindConversationInfoActivity(): ConversationInfoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GalleryActivityModule::class])
    abstract fun bindGalleryActivity(): GalleryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NotificationPrefsActivityModule::class])
    abstract fun bindNotificationPrefsActivity(): NotificationPrefsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [QkReplyActivityModule::class])
    abstract fun bindQkReplyActivity(): QkReplyActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ScheduledActivityModule::class])
    abstract fun bindScheduledActivity(): ScheduledActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindSettingsActivity(): SettingsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBlockingActivity(): BlockingActivity

}
