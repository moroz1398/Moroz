package ru.moroz.developerslife.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.moroz.developerslife.data.post.Post

/**
 * View commands for "latest post" screen
 */
interface LatestPostView : MvpView {

    @AddToEndSingle
    fun showPost(post: Post)

    @AddToEndSingle
    fun showDisabledBackButton()

    @AddToEndSingle
    fun showEnabledBackButton()

    @AddToEndSingle
    fun showDisabledNextButton()

    @AddToEndSingle
    fun showEnabledNextButton()

    @OneExecution
    fun showError(error: String)

}