package com.farshadchalenges.personalaccountingv1.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Account(

    val lastName: String,
    val firstName : String,
    val date:String,
    val amount:String,

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0

)
