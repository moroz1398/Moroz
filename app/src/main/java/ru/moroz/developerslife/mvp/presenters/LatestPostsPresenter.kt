package ru.moroz.developerslife.mvp.presenters

import moxy.MvpPresenter
import ru.moroz.developerslife.data.post.Post
import ru.moroz.developerslife.mvp.models.LatestPostsModel
import ru.moroz.developerslife.mvp.views.LatestPostView
import ru.moroz.developerslife.throwables.NoMoreDataThrowable
import ru.moroz.developerslife.utils.ErrorMessageUtils
import javax.inject.Inject

/**
 * Presenter for "latest posts" screen
 */
class LatestPostsPresenter @Inject constructor(
    private val model: LatestPostsModel,
    private val errorMessageUtils: ErrorMessageUtils
) : MvpPresenter<LatestPostView>() {

    private var currentPostIndex = 0
    private var onPostLoaded: ((post: Post?) -> Unit)? = { onPostReceived(it) }
    private var throwableHandler: ((Throwable) -> Unit)? = { onThrowableReceived(it) }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getPost()
    }

    override fun attachView(view: LatestPostView?) {
        super.attachView(view)
        onPostLoaded = { onPostReceived(it) }
        throwableHandler = { onThrowableReceived(it) }
    }

    override fun detachView(view: LatestPostView?) {
        onPostLoaded = null
        throwableHandler = null
        super.detachView(view)
    }

    /** @SelfDocumented */
    fun onNextButtonClicked() {
        currentPostIndex++
        getPost()
    }

    /** @SelfDocumented */
    fun onBackButtonClicked() {
        safeDecrementCurrentPostIndex()
        getPost()
    }

    private fun getPost() {
        lockButtons()
        model.getLatestPost(onPostLoaded!!, throwableHandler!!, currentPostIndex)
    }

    private fun onPostReceived(post: Post?) {
        unLockButtons()
        if (post != null) {
            viewState.showPost(post)
            val isFirstPost = currentPostIndex == 0

            if (isFirstPost) {
                viewState.showDisabledBackButton()
            } else {
                viewState.showEnabledBackButton()
            }
        }
    }

    private fun onThrowableReceived(throwable: Throwable) {
        unLockButtons()
        safeDecrementCurrentPostIndex()
        val error = errorMessageUtils.getErrorMessage(throwable)
        if (throwable is NoMoreDataThrowable) {
            viewState.showDisabledNextButton()
        }

        viewState.showError(error)
    }

    private fun safeDecrementCurrentPostIndex() {
        currentPostIndex--
        if (currentPostIndex < 0) {
            currentPostIndex = 0
        }
    }

    private fun unLockButtons() {
        viewState.showEnabledBackButton()
        viewState.showEnabledNextButton()
    }

    private fun lockButtons() {
        viewState.showDisabledNextButton()
        viewState.showDisabledBackButton()
    }
}