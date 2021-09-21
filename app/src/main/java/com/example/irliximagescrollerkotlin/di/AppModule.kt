package com.example.irliximagescrollerkotlin.di

import android.app.Application
import androidx.room.Room
import com.example.irliximagescrollerkotlin.api.PixabayAPI
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(PixabayAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePixabayAPI(retrofit: Retrofit): PixabayAPI = retrofit.create(PixabayAPI::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        ImageBlockDatabase::class.java,
        "image_block_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideImageBlockDao(db: ImageBlockDatabase) = db.imageBlockDao()
}