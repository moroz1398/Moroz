package ru.moroz.developerslife.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.moroz.developerslife.R
import ru.moroz.developerslife.app.App.Companion.appComponent
import ru.moroz.developerslife.data.post.Post
import ru.moroz.developerslife.databinding.FragmentLatestPostsBinding
import ru.moroz.developerslife.mvp.models.LatestPostsModel
import ru.moroz.developerslife.mvp.presenters.LatestPostsPresenter
import ru.moroz.developerslife.mvp.views.LatestPostView
import javax.inject.Inject


/**
 * Fragment for "latest post" screen
 */
class LatestPostsFragment : MvpAppCompatFragment(), LatestPostView {

    private var _binding: FragmentLatestPostsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var model: LatestPostsModel

    @Inject
    @InjectPresenter
    lateinit var presenter: LatestPostsPresenter

    @ProvidePresenter
    fun providePresenter(): LatestPostsPresenter = presenter

    init {
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLatestPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNextButtonListener()
        setBackButtonListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showPost(post: Post) {
        binding.postDescription.text = post.description ?: ""
        binding.postDescription.isVisible = !post.description.isNullOrEmpty()

        Glide.with(this)
            .asGif()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.img_not_found)
            .load(post.url)
            .into(binding.postImage)
    }

    override fun showDisabledBackButton() {
        binding.backButton.isEnabled = false
    }

    override fun showEnabledBackButton() {
        binding.backButton.isEnabled = true
    }

    override fun showDisabledNextButton() {
        binding.nextButton.isEnabled = false
    }

    override fun showEnabledNextButton() {
        binding.nextButton.isEnabled = true
    }

    override fun showError(error: String) {
        val snackbar = view?.let {
            Snackbar.make(it, error, Snackbar.LENGTH_LONG)
        }
        snackbar?.show()
    }

    private fun setBackButtonListener() {
        binding.backButton.setOnClickListener {
            presenter.onBackButtonClicked()

            if (!binding.nextButton.isEnabled) {
                binding.nextButton.isEnabled = true
            }
        }
    }

    private fun setNextButtonListener() {
        binding.nextButton.setOnClickListener {
            presenter.onNextButtonClicked()
        }
    }
}