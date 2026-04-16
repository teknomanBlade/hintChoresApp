package com.example.myapplication.di

import com.example.myapplication.core.data.coroutines.DefaultDispatchersProvider
import com.example.myapplication.core.firstrun.FirstRunManager
import com.example.myapplication.core.messages.MessagesLocalDataSource
import com.example.myapplication.domain.coroutines.IDispatchersProvider
import com.example.myapplication.domain.messages.IMessagesRepository
import com.example.myapplication.domain.messages.MessagesRepository
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.domain.usecase.DeleteMessageUseCase
import com.example.myapplication.domain.usecase.GetFavoriteMessageUseCase
import com.example.myapplication.domain.usecase.GetMessagesUseCase
import com.example.myapplication.domain.usecase.UpdateMessageUseCase
import com.example.myapplication.model.data.provider.NotificationProvider
import com.example.myapplication.model.data.ReminderPreferences
import com.example.myapplication.model.data.ReminderScheduler
import com.example.myapplication.model.data.ReminderWorker
import com.example.myapplication.model.data.repository.ReminderRepository
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.MessagesPickerViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.PermissionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    factory { AddMessageUseCase(get()) }
    factory { CreateReminderUseCase(get()) }
    single<IDispatchersProvider> { DefaultDispatchersProvider() }
    factory { DeleteMessageUseCase(get()) }
    single { FirstRunManager(androidContext()) }
    factory { GetMessagesUseCase(get()) }
    factory { GetFavoriteMessageUseCase(get()) }
    single { MessagesLocalDataSource(androidContext()) }

    single<IMessagesRepository> { MessagesRepository(get()) }
    single { NotificationProvider() }
    // Preferences
    single { ReminderPreferences(androidContext()) }

    // Scheduler
    single { ReminderScheduler(androidContext(),get()) }

    // Repository
    single { ReminderRepository(get(), get()) }
    factory { UpdateMessageUseCase(get()) }
    worker { ReminderWorker(androidContext(), get(), get()) }
    // ViewModel
    viewModel { MainViewModel(get(),get(), get(), get(),get()) }
    viewModel { MessagesViewModel(get(), get(), get(),get()) }
    viewModel { MessagesPickerViewModel(get()) }
    viewModel { PermissionViewModel(get(), get()) }
}