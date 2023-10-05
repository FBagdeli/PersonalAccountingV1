package com.farshadchalenges.personalaccountingv1.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.farshadchalenges.personalaccountingv1.R
import com.farshadchalenges.personalaccountingv1.database.AccountEvent
import com.farshadchalenges.personalaccountingv1.database.AccountState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransferDialog(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onEvent(AccountEvent.HideAddAccountDialog)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = state.firstName,
                    onValueChange = {
                        onEvent(AccountEvent.SetFirstName(it))
                    }, placeholder = {
                        Text(text = stringResource(id = R.string.first_name))
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(AccountEvent.SetLastName(it))
                    }, placeholder = {
                        Text(text = stringResource(id = R.string.last_name))
                    }
                )
                TextField(
                    value = state.amount,
                    onValueChange = {
                        onEvent(AccountEvent.SetAmount(it))
                    }, placeholder = {
                        Text(text = stringResource(id = R.string.amount))
                    }
                )
                TextField(
                    value = state.date,
                    onValueChange = {
                        onEvent(AccountEvent.SetDate(it))
                    }, placeholder = {
                        Text(text = stringResource(id = R.string.date))
                    }, supportingText = {
                        stringResource(id = R.string.date_support)
                    }
                )

            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    onEvent(AccountEvent.SaveAccount)
                }) {
                    Text(text = stringResource(id = R.string.Add_transaction))
                }
            }
        })
}