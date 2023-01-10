package com.github.af2905.movieland.home.presentation.people

/*
class PopularPeopleFragment :
    BaseFragment<HomeNavigator, FragmentPopularPeopleBinding, PopularPeopleViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_popular_people
    override fun viewModelClass(): Class<PopularPeopleViewModel> =
        PopularPeopleViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            PersonV2Item.VIEW_TYPE,
            listener = PersonV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

*/
/*    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = HomeComponentProvider.getHomeComponent(parentFragment)!!
        val popularPeopleComponent =
            DaggerPopularPeopleComponent.factory().create(appComponent, homeComponent)
        popularPeopleComponent.injectPopularPeopleFragment(this)
    }*//*


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
                when (effect) {
                    is PopularPeopleContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}*/
