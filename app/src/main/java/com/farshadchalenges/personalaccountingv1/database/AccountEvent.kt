package com.farshadchalenges.personalaccountingv1.database

sealed interface AccountEvent{

    object SaveAccount : AccountEvent
    data class SetFirstName(val firstName:String) : AccountEvent
    data class SetLastName(val lastName:String):AccountEvent
    data class SetDate(val date:String):AccountEvent
    data class SetAmount(val amount:String):AccountEvent
    object ShowAddAccountDialog:AccountEvent
    object HideAddAccountDialog:AccountEvent
    data class DeleteAccount(val account: Account):AccountEvent
    data class SortAccount(val sortType: SortType):AccountEvent
}