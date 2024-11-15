package com.mordecai.base.tools.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

fun Context.getInstallIntent(file: File, authority : String = "${this.packageName}.provider"): Intent {
    val intent = Intent(Intent.ACTION_VIEW)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val apkUri: Uri = FileProvider.getUriForFile(this, authority, file)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } else {
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}
