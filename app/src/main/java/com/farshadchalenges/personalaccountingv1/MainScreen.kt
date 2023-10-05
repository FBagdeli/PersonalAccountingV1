package com.farshadchalenges.personalaccountingv1

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.farshadchalenges.personalaccountingv1.database.AccountEvent
import com.farshadchalenges.personalaccountingv1.database.AccountState
import com.farshadchalenges.personalaccountingv1.database.SortType
import com.farshadchalenges.personalaccountingv1.ui.theme.AddTransferDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.main_screen_title))
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = colorResource(id = R.color.purple_500)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AccountEvent.ShowAddAccountDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_account_button)
                )
            }
        }
    ) { paddingValues ->

        if (state.isAddingAccount){
            AddTransferDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    SortType.values().forEach { sortType ->

                        Row(modifier = Modifier.clickable {
                            onEvent(AccountEvent.SortAccount(sortType))
                        }) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(AccountEvent.SortAccount(sortType))
                                })
                            Text(text = sortType.name)
                        }

                    }
                }
            }
            items(state.account) { account ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "${account.firstName} ${account.lastName}", color = Color.Black, fontSize = 20.sp)
                    Text(text = account.amount, color = Color.Black, fontSize = 20.sp)
                    Text(text = account.date, color = Color.Black, fontSize = 20.sp)
                }
            }

        }

    }

}