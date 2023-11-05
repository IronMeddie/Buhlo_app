package com.ironmeddie.donat.utils

import android.app.Activity
import android.content.Context
import androidx.room.Room
import com.ironmeddie.donat.data.firestoreDb.RemoteData
import com.ironmeddie.donat.data.auth.Auth
import com.ironmeddie.donat.data.auth.Authorization
import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import com.ironmeddie.donat.domain.NewCurrentUser
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.domain.getMainScreenData.getCurrentmoney
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

var activity: Activity? = null


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesRemoteDatabase(): RemoteDataBase = RemoteData()
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
    @Singleton
    @Provides
    fun provideAuth() : Authorization = Auth()
}
@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

//    @Provides
//    fun providesCategories(db: AppDatabase) = getCategoriesUseCase(db)
//
    @Provides
    fun providesCurrentMoney(db: RemoteDataBase) = getCurrentmoney(db)
//
//    @Provides
//    fun providesGetTransactions(db: AppDatabase) = getTransaction(db)
//    @Provides
//    fun providesupdateMoneyUseCase(db: RemoteDataBase, auth: Authorization) = updateMoneyValue(db,auth)
//

    @Provides
    fun providesSynch(remoteDb: RemoteDataBase, db: AppDatabase) = SyncDataUseCase(db,remoteDb)
    @Provides
    fun providesLodIn(db: RemoteDataBase, auth: Authorization) = NewCurrentUser()

}


