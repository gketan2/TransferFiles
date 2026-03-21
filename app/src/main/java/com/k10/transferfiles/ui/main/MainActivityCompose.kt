package com.k10.transferfiles.ui.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.k10.transferfiles.models.FileListObject
import com.k10.transferfiles.utils.ResultWrapper
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.k10.transferfiles.R
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getIcon
import com.k10.transferfiles.utils.FileType
import com.k10.transferfiles.utils.ResultStatus

@Composable
fun MainActivityView(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel
) {
    Column {
        val context = LocalContext.current
        CurrentPathBreakupView()
        FileListView(
            modifier = modifier,
            fileListFlow = viewModel.fileListFlow,
            onFolderClick = { file ->
                if (file.isAccessible)
                    viewModel.getFilesInPath(file.path)
                else
                    Toast.makeText(context, "Can not access ${file.name}", Toast.LENGTH_SHORT).show()
            },
            onFileClick = {},
        )
    }
}

@Composable
fun CurrentPathBreakupView() {

}

@Composable
fun FileListView(
    modifier: Modifier,
    fileListFlow: StateFlow<ResultWrapper<FileListObject>>,
    onFolderClick: (file: FileObject) -> Unit,
    onFileClick: (file: FileObject) -> Unit
) {
    val fileListState = fileListFlow.collectAsState().value
    when (fileListState.status) {
        ResultStatus.LOADING -> {}
        ResultStatus.SUCCESS -> {
            fileListState.data?.files?.let { files ->
                Text(
                    text = pluralStringResource(R.plurals.items_in_total, files.size)
                )
                if (files.isEmpty()) {
                    NoFileItemView()
                } else {
                    LazyColumn {
                        items(files.size) { index ->
                            FileItemView(
                                modifier = modifier,
                                file = files[index],
                                onFolderClick = onFolderClick,
                                onFileClick = onFileClick
                            )
                        }
                    }
                }
            }
        }

        ResultStatus.FAILED -> {
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FileItemView(
    modifier: Modifier,
    file: FileObject,
    onFolderClick: (file: FileObject) -> Unit,
    onFileClick: (file: FileObject) -> Unit
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp)
            .clickable {
                if (file.fileType == FileType.FOLDER) {
                    onFolderClick(file)
                } else {
                    onFileClick(file)
                }
            }
    ) {
        GlideImage(
            modifier = modifier
                .width(50.dp)
                .height(50.dp),
            model = file.getIcon(LocalContext.current.packageName),
            contentDescription = null
        )
        Column {
            Text(
                maxLines = 2,
                text = file.name,
            )
            Text("${file.formattedSize} | ${file.lastModifiedFormatted}")
        }
    }
}

@Composable
fun NoFileItemView() {

}