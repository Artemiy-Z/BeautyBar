package ru.beauty.bar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.beauty.bar.navigation.presenter.AppLauncher
import ru.beauty.bar.ui.fragment.BaseFragment

class MainActivity : FragmentActivity(), MainActivityInterface {

    private val navigator = AppNavigator(this, R.id.container)

    private val appLauncher = AppLauncher(App.INSTANCE.router)

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia())
    { uri ->
        currentCallback(uri)
    }

    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5))
        { uris ->
            currentMultipleCallback(uris)
        }

    var currentCallback: ((Uri?) -> Unit) = {}
    var currentMultipleCallback: ((List<Uri>) -> Unit) = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.INSTANCE.mainInterface = this

        enableEdgeToEdge()
        setContentView(R.layout.layout_container)

        val sharedPrefs = getSharedPreferences("Data", MODE_PRIVATE)
        val firstStart: Boolean = sharedPrefs.getBoolean("FirstStart", true)

        appLauncher.coldStart(firstStart = firstStart)
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        currentFragment?.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.INSTANCE.navigatorHolder.setNavigator(navigator = navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    // Interface stuff
    override fun getSharedPrefs(): SharedPreferences {
        return getSharedPreferences("Data", MODE_PRIVATE)
    }

    override fun showErrorMessage(
        message: String,
        dismissCallback: () -> Unit
    ) {
        currentFragment?.showErrorMessage(message, dismissCallback)
    }

    override fun showChoiceMessage(
        message: String,
        dismissCallback: (() -> Unit)?,
        confirmCallback: (() -> Unit)?
    ) {
        currentFragment?.showChoiceMessage(
            message = message,
            dismissCallback = dismissCallback,
            confirmCallback = confirmCallback
        )
    }

    override fun toggleLoading(showLoading: Boolean) {
        currentFragment?.toggleLoading(showLoading = showLoading)
    }

    override fun pickImage(
        callback: ((Uri?) -> Unit)
    ) {
        currentCallback = callback
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun pickMultipleImage(
        callback: ((List<Uri>) -> Unit)
    ) {
        currentMultipleCallback = callback
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun getContext(): Context {
        return applicationContext
    }
}
