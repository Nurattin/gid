package com.travel.gid.ui.tour_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.travel.gid.R

@Composable
fun RangeBtn(
    startPos: Int = 0,
    listSortOptions: List<Int>,
    actionOnClickSortedParams: (index: Int, item: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var current by remember { mutableStateOf(startPos) }

    Box(modifier = Modifier
            .wrapContentSize(Alignment.TopEnd).clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = {

            expanded = true }),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(

                text = stringResource(id = listSortOptions[current])
            )
            Image(
                if (expanded) painterResource(id = R.drawable.ic_round_arrow_drop_up)
                else painterResource(id = R.drawable.ic_round_arrow_drop_down),
                contentDescription = null
            )
        }

        DropdownMenu(
            offset = DpOffset(x = 0.dp, y = 10.dp),
            modifier = Modifier.width(210.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listSortOptions.forEachIndexed { index, i ->
                DropdownMenuItem(
                    enabled = index != current,
                    onClick = {
                        actionOnClickSortedParams(index, i)
                        current = index
                        expanded = false
                    },
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(text = stringResource(id = listSortOptions[index]))
                            if (index == current) {
                                Icon(
                                    Icons.Outlined.Done,
                                    contentDescription = null,
                                    modifier = Modifier
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}