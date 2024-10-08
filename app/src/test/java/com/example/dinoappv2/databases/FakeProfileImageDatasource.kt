package com.example.dinoappv2.databases

import com.example.dinoappv2.dataClasses.ProfileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProfileImageDatasource(profile: ProfileImage? = null) : ProfileImageDao {
    private var profileImage: ProfileImage? = profile

    override suspend fun insert(image: ProfileImage) {
        profileImage = image
    }

    override fun getImage(): Flow<ProfileImage?> {
        return flowOf(profileImage)
    }
}