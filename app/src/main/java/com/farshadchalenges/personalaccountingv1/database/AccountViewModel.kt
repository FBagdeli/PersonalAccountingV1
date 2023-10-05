@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.farshadchalenges.personalaccountingv1.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val dao: AccountDao,
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.AMOUNT)
    private val _state = MutableStateFlow(AccountState())
    private val _account = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
//                SortType.FIRST_NAME -> TODO()
//                SortType.LAST_NAME -> TODO()
//                SortType.DATE -> TODO()
                SortType.AMOUNT -> dao.getAccountsOrderedByAmount()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _account) { state, sortType, accounts ->
        state.copy(
            sortType = sortType,
            account = accounts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AccountState())

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.DeleteAccount -> {
                viewModelScope.launch {
                    dao.deleteAccount(event.account)
                }
            }

            AccountEvent.HideAddAccountDialog -> {
                _state.update {
                    it.copy(
                        isAddingAccount = false
                    )
                }
            }

            AccountEvent.SaveAccount -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val date = state.value.date
                val amount = state.value.amount

                if (firstName.isBlank() || lastName.isBlank() || date.isBlank() || amount.isBlank()) {
                    return
                }
                val account = Account(
                    firstName = firstName,
                    lastName = lastName,
                    date = date,
                    amount = amount
                )
                viewModelScope.launch {
                    dao.upsertAccount(account)
                }
                _state.update {
                    it.copy(
                        firstName = "",
                        lastName = "",
                        date = "",
                        amount = "",
                        isAddingAccount = false
                    )
                }

            }

            is AccountEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is AccountEvent.SetDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is AccountEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is AccountEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
            AccountEvent.ShowAddAccountDialog ->{
                _state.update {it.copy(
                    isAddingAccount = true
                )
                }
            }
            is AccountEvent.SortAccount -> {
                _sortType.value = event.sortType
            }
        }
    }

}