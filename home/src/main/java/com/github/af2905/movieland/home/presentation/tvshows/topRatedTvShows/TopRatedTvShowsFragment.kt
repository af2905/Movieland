package com.github.af2905.movieland.home.presentation.tvshows.topRatedTvShows

/*
class TopRatedTvShowsFragment :
    BaseFragment<HomeNavigator, FragmentTopRatedTvShowsBinding, TopRatedTvShowsViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_top_rated_tv_shows
    override fun viewModelClass(): Class<TopRatedTvShowsViewModel> =
        TopRatedTvShowsViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = HomeComponentProvider.getHomeComponent(parentFragment)!!
        val topRatedTvShowsComponent =
            DaggerTopRatedTvShowsComponent.factory().create(appComponent, homeComponent)
        topRatedTvShowsComponent.injectTopRatedTvShowsFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            addItemDecoration(
                VerticalListItemDecorator(
                    marginTop = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginBottom = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = this.context.resources.getDimensionPixelSize(R.dimen.default_margin)
                )
            )
        }
        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->

            }
        }
    }
}*/
