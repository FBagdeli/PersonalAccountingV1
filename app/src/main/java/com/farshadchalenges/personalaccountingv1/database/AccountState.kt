package com.farshadchalenges.personalaccountingv1.database

data class AccountState(

    val account: List<Account> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val date: String = "",
    val amount: String = "",
    val sortType: SortType = SortType.AMOUNT,
    val isAddingAccount: Boolean = false,


    )
