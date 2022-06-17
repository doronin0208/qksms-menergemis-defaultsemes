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

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.menergemis.defaultsemes.blocking.BlockingClient
import com.menergemis.defaultsemes.blocking.BlockingManager
import com.menergemis.defaultsemes.common.ViewModelFactory
import com.menergemis.defaultsemes.common.util.NotificationManagerImpl
import com.menergemis.defaultsemes.common.util.ShortcutManagerImpl
import com.menergemis.defaultsemes.feature.conversationinfo.injection.ConversationInfoComponent
import com.menergemis.defaultsemes.feature.themepicker.injection.ThemePickerComponent
import com.menergemis.defaultsemes.listener.ContactAddedListener
import com.menergemis.defaultsemes.listener.ContactAddedListenerImpl
import com.menergemis.defaultsemes.manager.ActiveConversationManager
import com.menergemis.defaultsemes.manager.ActiveConversationManagerImpl
import com.menergemis.defaultsemes.manager.AlarmManager
import com.menergemis.defaultsemes.manager.AlarmManagerImpl
import com.menergemis.defaultsemes.manager.AnalyticsManager
import com.menergemis.defaultsemes.manager.AnalyticsManagerImpl
import com.menergemis.defaultsemes.manager.ChangelogManager
import com.menergemis.defaultsemes.manager.ChangelogManagerImpl
import com.menergemis.defaultsemes.manager.KeyManager
import com.menergemis.defaultsemes.manager.KeyManagerImpl
import com.menergemis.defaultsemes.manager.NotificationManager
import com.menergemis.defaultsemes.manager.PermissionManager
import com.menergemis.defaultsemes.manager.PermissionManagerImpl
import com.menergemis.defaultsemes.manager.RatingManager
import com.menergemis.defaultsemes.manager.ReferralManager
import com.menergemis.defaultsemes.manager.ReferralManagerImpl
import com.menergemis.defaultsemes.manager.ShortcutManager
import com.menergemis.defaultsemes.manager.WidgetManager
import com.menergemis.defaultsemes.manager.WidgetManagerImpl
import com.menergemis.defaultsemes.mapper.CursorToContact
import com.menergemis.defaultsemes.mapper.CursorToContactGroup
import com.menergemis.defaultsemes.mapper.CursorToContactGroupImpl
import com.menergemis.defaultsemes.mapper.CursorToContactGroupMember
import com.menergemis.defaultsemes.mapper.CursorToContactGroupMemberImpl
import com.menergemis.defaultsemes.mapper.CursorToContactImpl
import com.menergemis.defaultsemes.mapper.CursorToConversation
import com.menergemis.defaultsemes.mapper.CursorToConversationImpl
import com.menergemis.defaultsemes.mapper.CursorToMessage
import com.menergemis.defaultsemes.mapper.CursorToMessageImpl
import com.menergemis.defaultsemes.mapper.CursorToPart
import com.menergemis.defaultsemes.mapper.CursorToPartImpl
import com.menergemis.defaultsemes.mapper.CursorToRecipient
import com.menergemis.defaultsemes.mapper.CursorToRecipientImpl
import com.menergemis.defaultsemes.mapper.RatingManagerImpl
import com.menergemis.defaultsemes.repository.BackupRepository
import com.menergemis.defaultsemes.repository.BackupRepositoryImpl
import com.menergemis.defaultsemes.repository.BlockingRepository
import com.menergemis.defaultsemes.repository.BlockingRepositoryImpl
import com.menergemis.defaultsemes.repository.ContactRepository
import com.menergemis.defaultsemes.repository.ContactRepositoryImpl
import com.menergemis.defaultsemes.repository.ConversationRepository
import com.menergemis.defaultsemes.repository.ConversationRepositoryImpl
import com.menergemis.defaultsemes.repository.MessageRepository
import com.menergemis.defaultsemes.repository.MessageRepositoryImpl
import com.menergemis.defaultsemes.repository.ScheduledMessageRepository
import com.menergemis.defaultsemes.repository.ScheduledMessageRepositoryImpl
import com.menergemis.defaultsemes.repository.SyncRepository
import com.menergemis.defaultsemes.repository.SyncRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    ConversationInfoComponent::class,
    ThemePickerComponent::class])
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRxPreferences(preferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(preferences)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    // Listener

    @Provides
    fun provideContactAddedListener(listener: ContactAddedListenerImpl): ContactAddedListener = listener

    // Manager

    @Provides
    fun provideActiveConversationManager(manager: ActiveConversationManagerImpl): ActiveConversationManager = manager

    @Provides
    fun provideAlarmManager(manager: AlarmManagerImpl): AlarmManager = manager

    @Provides
    fun provideAnalyticsManager(manager: AnalyticsManagerImpl): AnalyticsManager = manager

    @Provides
    fun blockingClient(manager: BlockingManager): BlockingClient = manager

    @Provides
    fun changelogManager(manager: ChangelogManagerImpl): ChangelogManager = manager

    @Provides
    fun provideKeyManager(manager: KeyManagerImpl): KeyManager = manager

    @Provides
    fun provideNotificationsManager(manager: NotificationManagerImpl): NotificationManager = manager

    @Provides
    fun providePermissionsManager(manager: PermissionManagerImpl): PermissionManager = manager

    @Provides
    fun provideRatingManager(manager: RatingManagerImpl): RatingManager = manager

    @Provides
    fun provideShortcutManager(manager: ShortcutManagerImpl): ShortcutManager = manager

    @Provides
    fun provideReferralManager(manager: ReferralManagerImpl): ReferralManager = manager

    @Provides
    fun provideWidgetManager(manager: WidgetManagerImpl): WidgetManager = manager

    // Mapper

    @Provides
    fun provideCursorToContact(mapper: CursorToContactImpl): CursorToContact = mapper

    @Provides
    fun provideCursorToContactGroup(mapper: CursorToContactGroupImpl): CursorToContactGroup = mapper

    @Provides
    fun provideCursorToContactGroupMember(mapper: CursorToContactGroupMemberImpl): CursorToContactGroupMember = mapper

    @Provides
    fun provideCursorToConversation(mapper: CursorToConversationImpl): CursorToConversation = mapper

    @Provides
    fun provideCursorToMessage(mapper: CursorToMessageImpl): CursorToMessage = mapper

    @Provides
    fun provideCursorToPart(mapper: CursorToPartImpl): CursorToPart = mapper

    @Provides
    fun provideCursorToRecipient(mapper: CursorToRecipientImpl): CursorToRecipient = mapper

    // Repository

    @Provides
    fun provideBackupRepository(repository: BackupRepositoryImpl): BackupRepository = repository

    @Provides
    fun provideBlockingRepository(repository: BlockingRepositoryImpl): BlockingRepository = repository

    @Provides
    fun provideContactRepository(repository: ContactRepositoryImpl): ContactRepository = repository

    @Provides
    fun provideConversationRepository(repository: ConversationRepositoryImpl): ConversationRepository = repository

    @Provides
    fun provideMessageRepository(repository: MessageRepositoryImpl): MessageRepository = repository

    @Provides
    fun provideScheduledMessagesRepository(repository: ScheduledMessageRepositoryImpl): ScheduledMessageRepository = repository

    @Provides
    fun provideSyncRepository(repository: SyncRepositoryImpl): SyncRepository = repository

}